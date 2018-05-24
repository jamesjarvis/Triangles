/*
Author: James Jarvis

This version has a GUI representation. To run each problem, click on the graph
that is shown on screen.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.io.*;


/**
 * This is the Main class, which will hold the main method and the objects
 * which are required to run the program.
 * It will also contain the algorithm for searching for a path within the graph.
 */
public class Main implements MouseListener{

    static final int SIZE = 600;
    private static final int GRAPH_SIZE = 23;
    static int SCALE_FACTOR = SIZE/GRAPH_SIZE;
    /*
    Of course, the graph being created would work from 0-24, with 0,0 being positioned in the bottom left
    But the graphics will seemingly start from the top left, which means all y values have to be inverted
    And the values will have to be scaled up to the size of the window (I will not bother creating a scale,
    but maybe some grid lines)
     */

    static Main triangleProblem;

    private Background background;//This is simply the background with gridlines.

    private LinkedList<Route> routes;//Contains all Routes, which themselves contain Vertex objects for each point.

    private int index;

    private Problems problemsdoc;

    private Renderer renderer;

    /**
     * Constructor for the Main class
     */
    private Main(){
        JFrame jframe = new JFrame();
        this.renderer = new Renderer();

        this.background = new Background(SIZE, GRAPH_SIZE);

        this.routes = new LinkedList<>();

        this.index = 0;

        this.problemsdoc = new Problems();

        readProblems();

        jframe.add(renderer);
        jframe.setTitle("Triangles Visualisation");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setSize(SIZE, SIZE+(SCALE_FACTOR));
        jframe.addMouseListener(this);

        jframe.setVisible(true);
    }

    /**
     * Reads problems in from an xml file
     */
    private void readProblems(){
        problemsdoc.readFile("problem/trianglesProblem42.xml");
    }

    /**
     * runs the depth first algo on the problem at index 'index'
     * @param index The number of the route problem
     */
    private void solveProblem(int index){
        long starttime = System.nanoTime();
        if(index>=0&&index<problemsdoc.getProblems().size()){
            Vertex[] problem = problemsdoc.getProblems().get(index);
            Route solved = depthLimitedIterativeDeepening(problem[0], problem[1]);
            if(solved!=null){
                routes.add(solved);
                System.out.println("Problem "+index+": "+solved.toString());

                writeToFile(index,solved);

                renderer.repaint();
            }else{
                System.out.println("Problem "+index+": no solution found");
            }
        }
        long endtime = System.nanoTime();
        System.out.println(" Took "+(endtime-starttime)/1000000+"ms");
    }

    /**
     * Simply writes the route to a file in the format specified by Andy King
     * @param problemNo The number of the route problem
     * @param route     The final route
     * @return          If the output was successful
     */
    private boolean writeToFile(int problemNo, Route route){
        String filePath = "problem-solutions/";

        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(filePath + problemNo + ".txt"),
                        "utf-8")
                )
        ) {
            writer.write(route.toString());
            System.out.println("Written output to file");
        }catch(IOException e){
            System.out.println("Could not write to file");
            return false;
        }
        return true;
    }


    /**
     * This returns all the next possible Routes which can be reached from the starting route.
     * @param startVertex the vertex to start from
     * @return a list of reachable vertex's
     */
    private LinkedList<Vertex> nextConfigs(Vertex startVertex, Vertex goal){
        LinkedList<Vertex> result = new LinkedList<>();

        Vertex goalCheck = nextConfigsDecision(startVertex, goal, result);//This checks to see if the goal destination can be reached. If so it just returns that.
        if(goalCheck!=null){
            result.add(goalCheck);
            return result;
        }

        for(Triangle triangle: problemsdoc.getTriangles()){//This section adds all the reachable triangle vertex's to the nextConfig
            Vertex[] vertices = triangle.getPoints();
            for(int i = 0;i<3;i++){
                Vertex temp = nextConfigsDecision(startVertex, vertices[i], result);
                if(temp!=null){
                    result.add(temp);
                }
            }
        }

        for(int x = 1; x<GRAPH_SIZE; x++){//This section adds all the reachable Vertices at the edge of the graph
            Vertex[] vertices= new Vertex[4];
            vertices[0] = new Vertex(0, x);
            vertices[1] = new Vertex(GRAPH_SIZE, x);
            vertices[2] = new Vertex(x, 0);
            vertices[3] = new Vertex(x, GRAPH_SIZE);
            for(int y = 0; y<4; y++){
                Vertex temp = nextConfigsDecision(startVertex, vertices[y], result);
                if(temp!=null){
                    result.add(temp);
                }
            }
        }

        return result;
    }
    private Vertex nextConfigsDecision(Vertex startVertex, Vertex vertex, LinkedList<Vertex> otherNewRoutes){
        if(acceptable(startVertex, vertex, otherNewRoutes)){
            if(accessibleVertex(startVertex, vertex)){
                //returns the vertex if it can actually be reached without hitting a triangle and if it not the start vertex or has already been included.
                return vertex;
            }
        }
        return null;
    }
    private boolean acceptable(Vertex startVertex, Vertex newPoint, LinkedList<Vertex> otherNewRoutes){
        return !startVertex.equals(newPoint)&&!otherNewRoutes.contains(newPoint);//Returns true if the new vertex is not the start vertex, and has not already been included.
    }
    private boolean accessibleVertex(Vertex start, Vertex end){
        for(Triangle triangle: problemsdoc.getTriangles()){
            Vertex[] points = triangle.getPoints();
            for(int i = 0; i<=2;i++){
                if(Vertex.linesIntersect(start, end, points[i], points[(i+1)%3])){
                    //if it passes through a triangle
                    return false;
                }
            }
        }
        //Returns true if the 'end' vertex can be reached from the 'start' vertex without hitting a triangle
        return true;
    }

    /**
     * This just runs the depthFirst method, at varying depths, up to a maximum depth of 10.
     * @param start     initial vertex
     * @param end       goal vertex
     * @return          final Route object
     */
    private Route depthLimitedIterativeDeepening(Vertex start, Vertex end){
        for(int depth = 1;depth<10;depth++){
            System.out.print("Depth: "+depth);
            Route route = depthFirst(start, end, depth);
            if(route!=null) {
                return route;
            }
            System.out.println("...no solution");
        }
        return null;
    }

    /**
     * This is the depthFirst method as understood from the lecture slides
     * @param config        starting vertex for each call
     * @param destination   destination vertex
     * @param depth         depth of the call
     * @return              final Route object
     */
    private Route depthFirst(Vertex config, Vertex destination, int depth){
        if(depth==0){
            return null;
        }
        else if(config.equals(destination))
        {
            System.out.println("...destination reached");
            return new Route(config);
        } else
        {
            LinkedList<Vertex> result = nextConfigs(config, destination);
            for(Vertex nextConfig: result){
                Route route= depthFirst(nextConfig, destination, depth-1);
                if(route!=null){
                    route.addFirst(config);
                    return route;
                }
            }
            return null;
        }
    }







    /*
     * Main method, creates a new instantiation of the Main class.
     */
    public static void main(String[] args){
        triangleProblem = new Main();
    }








    /**
     * This method repaints the whole graphical representation when called.
     * @param g - The Graphics object which the painting is applied to
     */
    void repaint(Graphics g){
        background.paint(g);
        for(Triangle triangle:problemsdoc.getTriangles()){
            triangle.paint(g);
        }
        for(Route route: routes){
            route.paint(g);
        }
    }
    /*
     * Will be used to start or stop the algorithm from searching when clicked on the window
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        solveProblem(index);
        index++;
    }
    /*
    The following methods are only there because they need to be.
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}

/*
Author: James Jarvis
Kent Login: jj333
 */
/*
This version has a GUI representation. To run each problem, click on the graph
that is shown on screen.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;


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

    private LinkedList<Triangle> triangles;//Contains the triangle 'obstacles'

    private LinkedList<Route> routes;//Contains all Routes, which themselves contain Vertex objects for each point.

    private LinkedList<Vertex[]> problems;//Contains all the problems to solve

    private int index;

    private Renderer renderer;

    /**
     * Constructor for the Main class
     */
    private Main(){
        JFrame jframe = new JFrame();
        this.renderer = new Renderer();

        this.background = new Background(SIZE, GRAPH_SIZE);
        this.triangles = new LinkedList<>();
        this.routes = new LinkedList<>();
        this.problems = new LinkedList<>();
        this.index = 0;


        initialiseTrianglesProblem42();//Adds all the triangles
        initialiseProblems42();//Adds all the problems


        jframe.add(renderer);
        jframe.setTitle("Triangles Visualisation");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setSize(SIZE, SIZE+(SCALE_FACTOR));
        jframe.addMouseListener(this);

        jframe.setVisible(true);
    }

    /*
     * Simply adds all the triangles which were given in my problem set 42
     */
    private void initialiseTrianglesProblem42(){
        triangles.add(new Triangle(11, 0, 13, 1, 18, 5));
        triangles.add(new Triangle(5, 4, 7, 9, 5, 8));
        triangles.add(new Triangle(2, 3, 4, 11, 2, 6));
        triangles.add(new Triangle(13, 7, 14, 9, 17, 10));
        triangles.add(new Triangle(4, 9, 11, 16, 9, 11));
        triangles.add(new Triangle(11, 3, 19, 7, 18, 10));
        triangles.add(new Triangle(14, 10, 17, 12, 22, 11));
        triangles.add(new Triangle(3, 1, 12, 9, 6, 9));
        triangles.add(new Triangle(9, 11, 14, 12, 14, 19));
        triangles.add(new Triangle(12, 2, 18, 7, 20, 8));
        triangles.add(new Triangle(3, 3, 10, 6, 12, 8));
        triangles.add(new Triangle(9, 15, 16, 19, 14, 15));
        triangles.add(new Triangle(11, 2, 18, 6, 11, 3));
        triangles.add(new Triangle(11, 11, 20, 12, 13, 17));
        triangles.add(new Triangle(1, 13, 2, 13, 5, 20));
        triangles.add(new Triangle(13, 16, 17, 19, 14, 20));
    }

    /*
     * Initialises and runs the problems given in problem 42
     */
    private void initialiseProblems42(){
        problems.add(new Vertex[]{new Vertex(3, 1), new Vertex(17,19)});
        problems.add(new Vertex[]{new Vertex(11,11), new Vertex(18,6)});
        problems.add(new Vertex[]{new Vertex(2,6), new Vertex(19,7)});
        problems.add(new Vertex[]{new Vertex(12,8), new Vertex(14,20)});
        problems.add(new Vertex[]{new Vertex(2,3), new Vertex(19,7)});
        problems.add(new Vertex[]{new Vertex(13,1), new Vertex(5,20)});
        problems.add(new Vertex[]{new Vertex(14,9), new Vertex(19,7)});
        problems.add(new Vertex[]{new Vertex(18,5), new Vertex(5,20)});
        problems.add(new Vertex[]{new Vertex(1,13), new Vertex(18,5)});
        problems.add(new Vertex[]{new Vertex(9,15), new Vertex(18,5)});
        problems.add(new Vertex[]{new Vertex(18,6), new Vertex(5,20)});
        problems.add(new Vertex[]{new Vertex(5,20), new Vertex(18,5)});
    }

    /*
    runs the depth first algo on the problem at index 'index'
     */
    private void solveProblem(int index){
        if(index>=0&&index<problems.size()){
            Vertex[] problem = problems.get(index);
            Route solved = depthLimitedIterativeDeepening(problem[0], problem[1]);
            routes.add(solved);
            System.out.println("Problem "+index+": "+solved.toString());
            renderer.repaint();
        }
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

        for(Triangle triangle: triangles){//This section adds all the reachable triangle vertex's to the nextConfig
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
                return vertex;
            }
        }
        return null;
    }
    private boolean acceptable(Vertex startVertex, Vertex newPoint, LinkedList<Vertex> otherNewRoutes){
        return !startVertex.equals(newPoint)&&!otherNewRoutes.contains(newPoint);//Returns true if the new vertex is not the start vertex, and has not already been included.
    }
    private boolean accessibleVertex(Vertex start, Vertex end){
        for(Triangle triangle: triangles){
            Vertex[] points = triangle.getPoints();
            for(int i = 0; i<=2;i++){
                if(Vertex.linesIntersect(start, end, points[i], points[(i+1)%3])){
                    return false;
                }
            }
        }
        //System.out.println("Can reach vertex "+end.toString()+" from "+start.toString());
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
            System.out.print(depth);
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
        for(Triangle triangle:triangles){
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

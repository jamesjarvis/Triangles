import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;


/**
 * This is the Main class, which will hold the main method and the objects
 * which are required to run the program.
 * It will also contain the algorithm for searching for a path within the graph.
 */
public class Main implements ActionListener, MouseListener{

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


    private Renderer renderer;

    private int ticks;
    private boolean start;

    /**
     * Constructor for the Main class
     */
    private Main(){
        JFrame jframe = new JFrame();
        this.renderer = new Renderer();

        this.background = new Background(SIZE, GRAPH_SIZE);
        this.triangles = new LinkedList<>();
        this.routes = new LinkedList<>();

        this.ticks = 0;
        this.start = false;

        initialiseTrianglesProblem42();//Adds all the triangles


        jframe.add(renderer);
        jframe.setTitle("Triangles Visualisation");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setSize(SIZE, SIZE+(SCALE_FACTOR));

        jframe.setVisible(true);
    }

    /**
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

    /**
     * Initialises and runs the problems given in problem 42
     */
    private void solveProblems42(){
        Vertex start1 = new Vertex(3,1);
        Vertex end1 = new Vertex(17,19);
        Route problem1 = depthLimitedIterativeDeepening(start1, end1);
        routes.add(problem1);

        LinkedList<Vertex[]> problems = new LinkedList<>();
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

        for(Vertex[] problem: problems){
            Route solved = depthLimitedIterativeDeepening(problem[0], problem[1]);
            routes.add(solved);
        }

        int i = 0;
        for(Route route: routes){
            System.out.println("Problem "+i+": "+route.toString());
            i++;
        }
    }


    /**
     * This returns all the next possible Routes which can be reached from the starting route.
     * @param startVertex
     * @return
     */
    private LinkedList<Vertex> nextConfigs(Vertex startVertex, Vertex goal){
        LinkedList<Vertex> result = new LinkedList<>();

        Vertex goalCheck = nextConfigsDecision(startVertex, goal, result);
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
        return !startVertex.equals(newPoint)&&!otherNewRoutes.contains(newPoint);//&&!visitedVertex(newPoint);
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
        System.out.println("Can reach vertex "+end.toString()+" from "+start.toString());
        return true;
    }

    /*private boolean visitedVertex(Vertex vertex){
        for(Route route: routes){
            if(route.getLast()==vertex){
                return true;
            }
        }
        return false;
    }*/


    public Route depthLimitedIterativeDeepening(Vertex start, Vertex end){
        for(int depth = 1;depth<10;depth++){
            System.out.println(depth);
            Route route = depthFirst(start, end, depth);
            if(route!=null) return route;
        }
        return null;
    }


    /**
     * This is the depthFirst method as taken straight from the lecture slides
     * @param config
     * @param destination
     * @param depth
     * @return
     */
    private Route depthFirst(Vertex config, Vertex destination, int depth){
        if(depth==0){
            return null;
        }
        else if(config.equals(destination))
        {
            System.out.println("destination reached");
            Route route = new Route(config);
            return route;
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

    /**
     * Main method, creates a new instantiation of the Main class.
     * @param args
     */
    public static void main(String[] args){
        triangleProblem = new Main();
    }

    /**
     * Simply used as a time interval creator to repaint the graphical space.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;

        if(ticks%5==0&&start){
            //increment();
            renderer.repaint();
        }
    }

    /**
     * Will be used to start or stop the algorithm from searching when clicked on the window
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        start = !start;
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

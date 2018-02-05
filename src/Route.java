import java.util.LinkedList;
import java.awt.*;

/**
 * This is the Route class, which is basically a line,
 * which connects various Vertex's together
 */
public class Route {
    LinkedList<Vertex> route;
    Color color;

    /**
     * Constructor for creating a new Route, with only a start vertex.
     * @param start
     */
    Route(Vertex start){
        this.route = new LinkedList<Vertex>();
        route.add(start);
        this.color = Color.BLUE;
    }

    /**
     * Constructor for creating a new Route, with a start and end vertex
     * (Used for creating Grid lines)
     * @param start
     * @param end
     */
    Route(Vertex start, Vertex end){
        this.route = new LinkedList<Vertex>();
        route.add(start);
        route.add(end);
        this.color = Color.BLACK;
    }

    /**
     * Method for adding a new vertex to the Route
     * @param vertex
     */
    public void addVertex(Vertex vertex){
        route.add(vertex);
    }

    /**
     * Paints the route, as a line to the Graphics object
     * @param g
     */
    public void paint(Graphics g){
        g.setColor(color);
        if(route.size()>=2){
            for(int i = 0; i<route.size()-1;i++){
                g.drawLine(route.get(i).get_x()*Main.SCALE_FACTOR, Main.SIZE-route.get(i).get_y()*Main.SCALE_FACTOR, route.get(i+1).get_x()*Main.SCALE_FACTOR, Main.SIZE-route.get(i+1).get_y()*Main.SCALE_FACTOR);
            }
        }
    }

}

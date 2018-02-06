import java.util.LinkedList;
import java.util.Random;
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
        Color[] colors= new Color[]{
                Color.BLUE,
                Color.CYAN,
                Color.YELLOW,
                Color.GREEN,
                Color.RED,
                Color.MAGENTA,
                Color.PINK,
                Color.BLACK,
        };
        Random random = new Random();
        this.color = colors[random.nextInt(colors.length)];
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
        this.color = Color.GRAY;
    }

    /**
     * Method for adding a new vertex to the Route
     * @param vertex
     */
    public void addVertex(Vertex vertex){
        route.add(vertex);
    }

    /**
     * Returns the length of the route
     * @return
     */
    public int length(){
        return route.size();
    }

    /**
     * Returns true if the route contains that Vertex
     * @param v
     * @return
     */
    public boolean contains(Vertex v){
        return route.contains(v);
    }

    public Vertex getLast(){
        return route.getLast();
    }

    public Vertex getFirst(){
        return route.getFirst();
    }

    public void addFirst(Vertex first){
        route.addFirst(first);
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


    /**
     * Returns the string representation of the Route
     * @return
     */
    public String toString(){
        String str = "";
        str+=route.get(0).toString();
        for(int i = 1;i<route.size();i++){
            str+=" "+route.get(i).toString();
        }
        return str;
    }

}

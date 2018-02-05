import java.awt.*;

/**
 * Triangle class, for generating a Triangle object
 */
public class Triangle {

    //The array of Vertex's is used for comparison with other Vertex objects, for example with Collision.
    Vertex points[];
    Polygon triangle;
    int npoints;

    /**
     * Constructor for creating a new Triangle with just the array of Vertex's
     * @param points
     */
    public Triangle(Vertex[] points){
        this.points = points;
        int[] pointsx = { points[0].get_x()*Main.SCALE_FACTOR, points[1].get_x()*Main.SCALE_FACTOR,points[2].get_x()*Main.SCALE_FACTOR};
        int[] pointsy = {Main.SIZE-points[0].get_y()*Main.SCALE_FACTOR, Main.SIZE-points[1].get_y()*Main.SCALE_FACTOR,Main.SIZE-points[2].get_y()*Main.SCALE_FACTOR};
        this.triangle = new Polygon(pointsx,pointsy, 3);
        this.npoints = 3;
    }

    /**
     * Constructor for creating a new Triangle object with arrays of x and y coordinates
     * @param pointsx
     * @param pointsy
     */
    public Triangle(int[] pointsx, int[] pointsy){
        this.points = new Vertex[3];
        for(int i = 0; i<3; i++){
            points[i] = new Vertex(pointsx[i], pointsy[i]);
        }
        //Modify the Polygon so it matched the graph size
        this.triangle = new Polygon(pointsx,pointsy, 3);
        this.npoints = 3;
    }

    /**
     * Constructor for creating a new Triangle object with individual parameters for each x and y coordinate
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     */
    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3){
        this.points = new Vertex[3];
        points[0] = new Vertex(x1, y1);
        points[1] = new Vertex(x2, y2);
        points[2] = new Vertex(x3, y3);
        int[] pointsx = {points[0].get_x()*Main.SCALE_FACTOR, points[1].get_x()*Main.SCALE_FACTOR,points[2].get_x()*Main.SCALE_FACTOR};
        int[] pointsy = {Main.SIZE-(points[0].get_y()*Main.SCALE_FACTOR), Main.SIZE-(points[1].get_y()*Main.SCALE_FACTOR),Main.SIZE-(points[2].get_y()*Main.SCALE_FACTOR)};
        this.triangle = new Polygon(pointsx,pointsy, 3);
        this.npoints = 3;
    }

    /**
     * Returns an array of size 3, with Vertex objects for each corner
     * @return
     */
    Vertex[] getPoints(){
        return points;
    }

    /**
     * Paints the Triangle object
     * @param g
     */
    void paint(Graphics g){
        g.setColor(Color.ORANGE);

        g.fillPolygon(triangle);

        g.setColor(Color.BLACK);
        g.drawPolygon(triangle);
    }
}

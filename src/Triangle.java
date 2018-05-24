/*
Author: James Jarvis

This Class is only used to store representations of the Triangles, and for GUI purposes
 */

import java.awt.*;

/**
 * Triangle class, for generating a Triangle object
 */
class Triangle {

    //The array of Vertex's is used for comparison with other Vertex objects, for example with Collision.
    private Vertex points[];
    private Polygon triangle;
    private int npoints;

    /**
     * Constructor for creating a new Triangle object with individual parameters for each x and y coordinate
     * @param x1    first x value
     * @param y1    first y value
     * @param x2    second x value
     * @param y2    second y value
     * @param x3    third x value
     * @param y3    third y value
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

    public Triangle(){
        this.points = new Vertex[3];
        this.triangle = null;
        this.npoints = 0;
    }

    public void addVertex(Vertex v){
        points[npoints] = v;
        npoints++;
    }

    public void drawTriangle(){
        int[] pointsx = {points[0].get_x()*Main.SCALE_FACTOR, points[1].get_x()*Main.SCALE_FACTOR,points[2].get_x()*Main.SCALE_FACTOR};
        int[] pointsy = {Main.SIZE-(points[0].get_y()*Main.SCALE_FACTOR), Main.SIZE-(points[1].get_y()*Main.SCALE_FACTOR),Main.SIZE-(points[2].get_y()*Main.SCALE_FACTOR)};
        this.triangle = new Polygon(pointsx,pointsy, npoints);
    }

    /**
     * Returns an array of size 3, with Vertex objects for each corner
     * @return  vertex array of the triangles points
     */
    public Vertex[] getPoints(){
        return points;
    }

    /**
     * Paints the Triangle object
     * @param g graphics object
     */
    public void paint(Graphics g){
        g.setColor(Color.ORANGE);

        g.fillPolygon(triangle);

        g.setColor(Color.BLACK);
        g.drawPolygon(triangle);
    }
}

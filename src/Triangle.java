import java.awt.*;

public class Triangle {

    Vertex points[];
    Polygon triangle;
    int npoints;

    public Triangle(Vertex[] points){
        this.points = points;
        int[] pointsx = { points[0].get_x()*Main.SCALE_FACTOR, points[1].get_x()*Main.SCALE_FACTOR,points[2].get_x()*Main.SCALE_FACTOR};
        int[] pointsy = {Main.SIZE-points[0].get_y()*Main.SCALE_FACTOR, Main.SIZE-points[1].get_y()*Main.SCALE_FACTOR,Main.SIZE-points[2].get_y()*Main.SCALE_FACTOR};
        this.triangle = new Polygon(pointsx,pointsy, 3);
        this.npoints = 3;
    }
    public Triangle(int[] pointsx, int[] pointsy){
        this.points = new Vertex[3];
        for(int i = 0; i<3; i++){
            points[i] = new Vertex(pointsx[i], pointsy[i]);
        }
        this.triangle = new Polygon(pointsx,pointsy, 3);
        this.npoints = 3;
    }
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

    boolean checkCollision(Vertex v){
        return false;
    }

    void paint(Graphics g){
        g.setColor(Color.ORANGE);

        g.fillPolygon(triangle);

        g.setColor(Color.BLACK);
        g.drawPolygon(triangle);
    }
}

import java.awt.*;

public class Triangle {

    Vertex points[];
    Polygon triangle;
    int npoints;

    public Triangle(Vertex[] points){
        this.points = points;
        int[] pointsx = {points[0].get_x(), points[1].get_x(),points[2].get_x()};
        int[] pointsy = {points[0].get_y(), points[1].get_y(),points[2].get_y()};
        this.triangle = new Polygon(pointsx,pointsy, 3);
        this.npoints = 3;
    }

    void paint(Graphics g){
        g.setColor(Color.ORANGE);

        g.fillPolygon(triangle);
    }
}

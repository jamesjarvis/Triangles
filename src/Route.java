import java.util.LinkedList;
import java.awt.*;

public class Route {
    LinkedList<Vertex> route;
    Color color;

    Route(Vertex start){
        this.route = new LinkedList<Vertex>();
        route.add(start);
        this.color = Color.BLUE;
    }

    public void addVertex(Vertex vertex){
        route.add(vertex);
    }

    public void paint(Graphics g){
        g.setColor(color);
        if(route.size()>=2){
            for(int i = 0; i<route.size()-1;i++){
                g.drawLine(route.get(i).get_x(), route.get(i).get_y(), route.get(i+1).get_x(), route.get(i+1).get_y());
            }
        }
    }

}

import java.awt.*;
import java.util.ArrayList;

public class Background {

    private Rectangle background;
    private Color backgroundColor;
    private final int GRAPH_SIZE;
    private ArrayList<Route> gridlines;

    Background(int SIZE, int GRAPH_SIZE){
        this.background = new Rectangle(0,0,SIZE, SIZE);
        this.backgroundColor = Color.WHITE;
        this.GRAPH_SIZE = GRAPH_SIZE;

        gridlines = new ArrayList<>();
        for(int i = 0; i<GRAPH_SIZE;i=i+2){
            gridlines.add(new Route(new Vertex(i, 0), new Vertex(i, GRAPH_SIZE)));
            gridlines.add(new Route(new Vertex(0, i), new Vertex(GRAPH_SIZE, i)));
        }
    }

    void paint(Graphics g){
        for(Route line: gridlines){
            line.paint(g);
        }
        //g.setColor(backgroundColor);
        //g.fillRect(background.x, background.y, background.width, background.height);
    }
}

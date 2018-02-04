import java.awt.*;

public class Background {

    Rectangle background;
    Color backgroundColor;
    int GRAPH_SIZE;

    Background(int SIZE, int GRAPH_SIZE){
        this.background = new Rectangle(0,0,SIZE, SIZE);
        this.backgroundColor = Color.WHITE;
        this.GRAPH_SIZE = GRAPH_SIZE;
    }

    void paint(Graphics g){
        g.setColor(backgroundColor);
        g.fillRect(background.x, background.y, background.width, background.height);
    }
}

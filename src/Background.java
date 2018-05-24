/*
Author: James Jarvis

This class is only used for GUI purposes
 */

import java.awt.*;
import java.util.ArrayList;

/**
 * Simply a class for setting the background colour and the gridlines.
 */
class Background {

    private Rectangle background;
    private Color backgroundColor;
    private ArrayList<Route> gridlines;

    /**
     * Constructor of Class Background.
     * @param SIZE the size of the GUI window
     * @param GRAPH_SIZE the size of the graph axis
     */
    Background(int SIZE, int GRAPH_SIZE){
        this.background = new Rectangle(0,0,SIZE, SIZE);
        this.backgroundColor = Color.WHITE;

        gridlines = new ArrayList<>();
        for(int i = 0; i<GRAPH_SIZE;i=i+2){
            gridlines.add(new Route(new Vertex(i, 0), new Vertex(i, GRAPH_SIZE)));
            gridlines.add(new Route(new Vertex(0, i), new Vertex(GRAPH_SIZE, i)));
        }
    }

    /**
     * paints the background to the Graphics object
     * @param g the graphics object
     */
    void paint(Graphics g){
        for(Route line: gridlines){
            line.paint(g);
        }
    }
}

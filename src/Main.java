import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
public class Main implements ActionListener, MouseListener{

    public int SIZE = 600;
    public int GRAPH_SIZE = 24;
    /*
    Of course, the graph being created would work from 0-24, with 0,0 being positioned in the bottom left
    But the graphics will seemingly start from the top left, which means all y values have to be inverted
    And the values will have to be scaled up to the size of the window (I will not bother creating a scale,
    but maybe some grid lines)
     */

    public static Main triangleProblem;

    public Background background;

    public LinkedList<Triangle> triangles;

    public LinkedList<Route> routes;


    public Renderer renderer;

    private int ticks;
    private boolean start;

    public Main(){
        JFrame jframe = new JFrame();
        this.renderer = new Renderer();

        this.background = new Background(SIZE, GRAPH_SIZE);
        this.triangles = new LinkedList<>();
        this.routes = new LinkedList<>();

        this.ticks = 0;
        this.start = false;


        jframe.add(renderer);
        jframe.setTitle("Triangles Visualisation");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setSize(SIZE, SIZE);

        jframe.setVisible(true);

    }


    public void repaint(Graphics g){
        background.paint(g);
        for(Triangle triangle:triangles){
            triangle.paint(g);
        }
        for(Route route: routes){
            route.paint(g);
        }
    }

    public static void main(String[] args){
        triangleProblem = new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;

        if(ticks%5==0){
            //increment();
            renderer.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        start = !start;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

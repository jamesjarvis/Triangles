import javax.swing.*;
import java.awt.Graphics;

/**
 * Renderer class just for re-rendering the object
 */
public class Renderer extends JPanel {

    /**
     * Calls the repaint method in the Main class.
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Main.triangleProblem.repaint(g);
    }
}


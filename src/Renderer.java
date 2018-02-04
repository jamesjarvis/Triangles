import javax.swing.*;
import java.awt.Graphics;

public class Renderer extends JPanel {

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Main.triangleProblem.repaint(g);
    }
}


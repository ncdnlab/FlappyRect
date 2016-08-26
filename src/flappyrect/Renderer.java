package flappyrect;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Md Selim Miah
 * @author Kawsar Ahmed
 * @author Md Mahadi Hasan Nahid
 *
 * North East University Bangladesh (NEUB) nahid@neub.edu.bd
 * @version 1.0
 *
 */
public class Renderer extends JPanel {

    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        try {
            FlappyRect.flappyRect.repaint(g);
        } catch (InterruptedException ex) {
            Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

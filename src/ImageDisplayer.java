import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;

/**
 * Class ImageDisplayer - This class can display images and is used to display a map so the user can easier navigate in the game.
 * Code inspired by https://www.dummies.com/programming/java/how-to-write-java-code-to-show-an-image-on-the-screen/
 * @author Barry Burd, modified by Valentin Magis, K20076746
 * @version 25.11.2020
 */
public class ImageDisplayer {

    /**
     * Constructs a frame and displays the requested image in it.
     */
        public ImageDisplayer(String filename) {
            JFrame frame = new JFrame();
            ImageIcon icon = new ImageIcon(filename);
            JLabel label = new JLabel(icon);
            frame.add(label);

            frame.setDefaultCloseOperation
                    (JFrame.HIDE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
}

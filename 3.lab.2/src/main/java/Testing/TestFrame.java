package Testing;

import javax.swing.*;
import java.awt.*;

/**
 * @author Antonio Bukovac
 */
public class TestFrame extends JFrame {

    public TestFrame() {
        setTitle("Testing drawing");
        setSize(1000, 800);
        setLocation(250, 250);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TestFrame().setVisible(true));
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(new TestComponent(this), BorderLayout.CENTER);
    }

}

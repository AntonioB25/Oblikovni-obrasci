package Testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Antonio Bukovac
 */
public class TestComponent extends JComponent {

    public TestComponent(JFrame frame) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    frame.dispose();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setFont(new Font("Courier", Font.PLAIN, 16));
        int width = getBounds().width;
        int height = getBounds().height;
        drawLines(graphics2D, height, width);
        drawText(graphics2D, width, height);
    }

    private void drawLines(Graphics2D graphics2D, int height, int width) {
        graphics2D.setColor(Color.RED);
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.drawLine(50, 50, 200, 50);
        graphics2D.drawLine(50, 55, 50, 200);
    }

    private void drawText(Graphics2D graphics2D, int width, int height) {
        int fontHeight = graphics2D.getFontMetrics().getHeight();
        int x = 150;
        int yFirst = 200;
        int ySecond = 200 + fontHeight;
        graphics2D.drawString("Neki tekst je ovo", 150, yFirst);
        graphics2D.drawString("Neki drugi tekst je ovo", 150, ySecond);
    }

}

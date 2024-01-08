package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

// SnakeComponent class extends JComponent to draw game elements
class SnakeComponent extends JComponent {
    // List to store apple positions
    public CopyOnWriteArrayList<Point2D> apple;
    // Setter for apple list
    public void setapple(CopyOnWriteArrayList<Point2D> x) { apple = x; }
    // Random generator for various purposes
    public Random ran = new Random();
    // Getting the screen size to set component size
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // Default width and height based on screen size
    private int DEFAULT_WIDTH = screenSize.width / 4;
    private int DEFAULT_HEIGHT = screenSize.height / 4;
    // Methods to get component width and height
    public int ComponentWidth() { return getWidth(); }
    public int ComponentHeight() { return getHeight(); }
    // Size for game objects like snake parts and apples
    int GOS = 30;
    // List to store snake objects
    public final CopyOnWriteArrayList<Snake> Snakes = new CopyOnWriteArrayList<>();
    // Methods to add and remove snake objects
    public void add(Snake b) { Snakes.add(b); }
    public void remove(Snake b) { Snakes.remove(b); }
    // Constructor takes a list of apples
    public SnakeComponent(CopyOnWriteArrayList<Point2D> AL) { this.apple = AL; }

    // Override paintComponent to draw the game elements
    @Override public synchronized void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Setting color for drawing grid lines
        g2.setColor(Color.DARK_GRAY);
        // Drawing grid lines for reference
        for (int i = 0; i < screenSize.width / 25; i++) {
            g2.drawLine(i * GOS, 0, i * GOS, screenSize.height);
            g2.drawLine(0, i * GOS, screenSize.width, i * GOS);
        }
        // Setting color for apples
        g2.setColor(Color.RED);
        // Drawing apples
        for (Point2D a : apple) {
            g2.fill(new Ellipse2D.Double(a.getX() - 27, a.getY() - 27, 24, 24));
        }
        // Drawing each snake in the list
        for (Snake b : Snakes) {
            for (Point2D s : b.Parts) {
                // Setting color and stroke for snake parts
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(9));
                g2.draw(new Rectangle2D.Double(s.getX() - GOS, s.getY() - GOS, GOS, GOS));
                g2.setStroke(new BasicStroke(0));
                g2.setColor(b.GETSC());
                g2.fill(new Rectangle2D.Double(s.getX() - GOS, s.getY() - GOS, GOS, GOS));
            }
            // Drawing the head of the snake with different style
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(9));
            g2.draw(new Rectangle2D.Double(b.GetXSn() - GOS, b.GetYSb() - GOS, GOS, GOS));
            g2.setColor(b.GETSC());
            g2.setStroke(new BasicStroke(0));
            g2.fill(new Rectangle2D.Double(b.GetXSn() - GOS, b.GetYSb() - GOS, GOS, GOS));
            // Drawing the eyes of the snake
            g2.setColor(Color.WHITE);
            g2.fill(new Ellipse2D.Double(b.GetXSn() - GOS / 2 - 7, b.GetYSb() - GOS / 2 - 7, GOS / 2, GOS / 2));
        }
    }

    // Override to set preferred size of the component
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}

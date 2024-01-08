// Package declaration
package SnakeGame;

// Importing necessary Java Swing and AWT classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

// SnakeFrame class extending JFrame to create the game window
class SnakeFrame extends JFrame {

    // List to store positions of apples
    CopyOnWriteArrayList<Point2D> Apple = new CopyOnWriteArrayList<>();
    // Component where the game is drawn
    SnakeComponent comp = new SnakeComponent(Apple);
    // Randomizer for apple spawning
    Random randomizer = new Random();

    // Constructor for SnakeFrame
    SnakeFrame() {
        // Adding the game component to the center of the frame
        add(comp, BorderLayout.CENTER);
        // Panel for the buttons
        JPanel buttonPanel = new JPanel();
        // Adding a button to create new snakes
        addButton(buttonPanel, "snake", new ActionListener() {
            @Override public void actionPerformed(ActionEvent event) {
                addSnake();
            }});
        // Adding the button panel to the top of the frame
        add(buttonPanel, BorderLayout.NORTH);
        // Method to add apples in the game
        addApples();
        // Adjusts the frame size to fit the contents
        pack();
    }

    // Method to add a button to the container
    public void addButton(Container c, String title, ActionListener listener) {
        JButton button = new JButton(title);
        c.add(button);
        button.addActionListener(listener);
    }

    // Method to add apples in random positions
    public void addApples() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        boolean running = true;
        Thread Apples = new Thread(new Runnable() {
            @Override public void run() {
                try {
                    while (running) {
                        // Adding a new apple in a random position
                        Apple.add(new Point2D.Double((1 + randomizer.nextInt((int) (dim.getWidth() / 30))) * 30,
                                (1 + randomizer.nextInt((int) (dim.getHeight() / 30))) * 30));
                        // Updating the apples in the component
                        comp.setapple(Apple);
                        // Repainting the frame to reflect changes
                        repaint();
                        // Thread sleeps for a second
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {}
            }
        });
        Apples.start();
    }

    // Method to add a new snake
    public void addSnake() {
        int x = comp.ran.nextInt((comp.getBounds().width / comp.GOS)) * comp.GOS;
        int y = comp.ran.nextInt((comp.getBounds().height / comp.GOS)) * comp.GOS;
        Snake snake = new Snake(x, y, (CopyOnWriteArrayList<Snake>) comp.Snakes, comp.apple);
        comp.add(snake);
        boolean running = true;

        Thread t = new Thread(new Runnable() {
            @Override public void run() {
                boolean running = true;
                try {
                    while (running) {
                        synchronized (this) {
                            // Check if the snake eats an apple
                            Apple = snake.checkeat(comp.bounds());
                            // Update apples in the component
                            comp.setapple(Apple);
                            // Move the snake
                            snake.move(comp.getBounds());
                            // Repaint to reflect changes
                            comp.repaint();
                            // Check if the snake is alive
                            if (snake.GetBP() == 0) {
                                // If the snake is dead, move it off-screen and stop the thread
                                snake.setCOR(-300, -300, 0, 0);
                                running = false;
                                comp.repaint();
                            }
                        }
                        // Thread sleeps for 200 milliseconds
                        Thread.sleep(200);
                    }
                } catch (InterruptedException e) {}
            }
        });
        t.start();
    }
}

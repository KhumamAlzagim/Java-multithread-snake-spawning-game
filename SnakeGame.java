// Package declaration
package SnakeGame;

// Importing necessary Java Swing and AWT classes
import javax.swing.*;
import java.awt.*;

// Main class for the Snake Game
public class SnakeGame {

    // Entry point of the Java application
    public static void main(String args[]) {
        // Invoking the GUI in the Event Dispatch Thread for thread safety
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Creating the main game window (JFrame)
                JFrame frame = new SnakeFrame();
                frame.setTitle("Snake The Game by Khumam Alzagim"); // Setting the title of the frame
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensuring the application exits when the frame is closed
                frame.setLocationRelativeTo(null); // Centering the frame on the screen
                frame.setVisible(true); // Making the frame visible
            }
        });
    }
}

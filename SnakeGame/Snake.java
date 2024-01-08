package SnakeGame;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

class Snake {

    private Random ran = new Random();
    private int RAN = ran.nextInt(2); // Randomly decides the initial direction of the snake
    private int GOS = 30; // Size of each game object (e.g., snake part)
    private int NBParts; // Number of parts in the snake
    private Color col; // Color of the snake
    private double x, y, dx, dy; // Snake's position and direction

    // Set the snake's current position and direction
    public void setCOR(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    // Add body parts to the snake
    public void ADBP(int x) {
        NBParts += x;
    }

    // Getters for the snake's position and color
    public Double GetXSn() { return x; }
    public Double GetYSb() { return y; }
    public int GetBP() { return NBParts; }
    public Color GETSC() { return col; }

    // Lists to hold apples and other snakes
    private CopyOnWriteArrayList<Point2D> Apple;
    private CopyOnWriteArrayList<Snake> Snakes;
    CopyOnWriteArrayList<Point2D> Parts = new CopyOnWriteArrayList<>();

    // Constructor for Snake
    Snake(int x, int y, CopyOnWriteArrayList<Snake> Reco, CopyOnWriteArrayList<Point2D> apple) {
        this.Snakes = Reco;
        this.Apple = apple;
        this.NBParts = 3 + ran.nextInt(6); // Random initial size of the snake
        this.col = getRandomColor(); // Random color for the snake

        // Random initial direction
        int FS = ran.nextInt(4);
        switch (FS) {
            case 0 -> { dy = -GOS; dx = 0; }
            case 1 -> { dy = 0; dx = -GOS; }
            case 2 -> { dy = GOS; dx = 0; }
            case 3 -> { dy = 0; dx = GOS; }
        }
        this.x = x + 30;
        this.y = y + 30;
    }

    // Method to return a random color
    private Color getRandomColor() {
        return switch (1 + ran.nextInt(10)) {
            case 1 -> Color.RED;
            case 2 -> Color.BLUE;
            case 3 -> Color.GREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.ORANGE;
            case 6 -> Color.PINK;
            case 7 -> Color.MAGENTA;
            case 8 -> Color.CYAN;
            case 9 -> Color.LIGHT_GRAY;
            default -> Color.BLACK;
        };
    }

    // Method to move the snake
    public synchronized void move(Rectangle2D bounds) {
        Point2D z = new Point2D.Double(x, y);
        Parts.add(z);
        boolean Did = false;
        for (Snake s : Snakes) {
            if (s != this) {
                if (x + dx == s.x && y == s.y) {
                    dx = 0;
                    if (y - 30 < 30) {
                        dy = GOS;
                    } else if (y + 2 * 30 > bounds.getMaxY()) {
                        dy = -GOS;
                    } else {
                        dy = GOS;
                    }
                } else if (y + dy == s.y && x == s.x) {
                    dy = 0;
                    if (x + 30 > bounds.getMaxX()) {
                        dx = -GOS;
                    } else if (x - 40 < bounds.getMinX()) {
                        dx = GOS;
                    } else {
                        dx = GOS;
                    }

                }

                for (Point2D p : s.Parts) {
                    if (x + dx == p.getX() && y == p.getY()) {
                        Did = true;
                        if (dy + 2*30 > bounds.getMaxY())
                        {
                            dy = -GOS;
                        }
                        else if (dy - 30 < 30)
                        {
                            dy = GOS;
                        } else if(dx==-GOS)
                        {
                            dy = -GOS;
                        }
                        else {dy =GOS;}
                        dx = 0;
                    }

                    if (x == p.getX() && y + dy == p.getY()) {
                        Did = true;
                        if (RAN == 0) {
                            dy = 0;
                            dx = GOS;
                        }
                        if (RAN == 1) {
                            dy = 0;
                            dx = -GOS;
                        }
                    }
                    if (x + dx == s.x && y + dy == s.y) {
                        Did = true;
                        if (x + GOS > bounds.getMaxX()) {
                            dx = 0;
                            dy = -GOS;
                        }
                        if (x - GOS <= 30) {
                            dx = 0;
                            dy = -GOS;
                        }
                        if (y + 2 * GOS > bounds.getMaxY()) {
                            dy = 0;
                            dx = GOS;
                        }
                        if (y - GOS < bounds.getMinY()) {
                            dy = 0;
                            dx = -GOS;
                        }
                    }
                }
            }

        }
        if (Did) {
            x += dx;
            y += dy;
            Did = false;
            Parts.add(new Point2D.Double(x, y));
            NBParts--;
        }
        while (Parts.size() > NBParts) {
            Parts.remove(0);
        }

        if (x <= 30) {
            if (dx == -GOS) {
                if (y <= bounds.getMinY()) {
                    x = 30;
                    dx = 0;
                    dy = GOS;
                } else if (y + 2 * GOS > bounds.getMaxY()) {
                    x = 30;
                    dx = 0;
                    dy = -GOS;
                } else {
                    if (RAN == 0) {
                        x = 30;
                        dx = 0;
                        dy = GOS;
                    }
                    if (RAN == 1) {
                        x = 30;
                        dx = 0;
                        dy = -GOS;
                    }
                }
            }
        }
        if (x + GOS > bounds.getMaxX()) {
            if (dx == GOS) {
                if (y <= bounds.getMinY()) {
                    x = bounds.getMaxX();
                    dx = 0;
                    dy = GOS;
                } else if (y + 2 * GOS > bounds.getMaxY()) {
                    x = bounds.getMaxX();
                    dx = 0;
                    dy = -GOS;
                } else {
                    if (RAN == 0) {
                        x = bounds.getMaxX();
                        dx = 0;
                        dy = -GOS;
                    }
                    if (RAN == 1) {
                        x = bounds.getMaxX();
                        dx = 0;
                        dy = GOS;
                    }
                }

            }
        }
        if (y < bounds.getMinY()) {
            if (dy == -GOS) {
                if (x <= 30) {
                    dy = 0;
                    dx = GOS;
                } else if (x + GOS > bounds.getMaxX()) {
                    dy = 0;
                    dx = -GOS;
                } else {
                    if (RAN == 0) {
                        dy = 0;
                        dx = -GOS;
                    }
                    if (RAN == 1) {
                        dy = 0;
                        dx = GOS;
                    }
                }
            }


        }

        if (y + 2 * GOS >= bounds.getMaxY()) {
            if (dy == GOS) {
                if (x <= 30) {
                    y = bounds.getMaxY() - GOS - ((bounds.getMinY() - GOS) % 30);
                    dy = 0;
                    dx = GOS;
                } else if (x + GOS > bounds.getMaxX()) {
                    y = bounds.getMaxY() - GOS - ((bounds.getMinY() - GOS) % 30);
                    dy = 0;
                    dx = -GOS;
                } else {
                    if (RAN == 0) {
                        y = bounds.getMaxY() - GOS - ((bounds.getMinY() - GOS) % 30);
                        dy = 0;
                        dx = GOS;
                    }
                    if (RAN == 1) {
                        y = bounds.getMaxY() - GOS - ((bounds.getMinY() - GOS) % 30);
                        dy = 0;
                        dx = -GOS;
                    }
                }

            }
        }
        //Drawing corrections
        if (x % GOS != 0) {
            x = x - x % GOS;
        }
        if (y % GOS != 30) {
            y = y - y % GOS;
        }
        if (y < 30) {
            y = 30;
        }
        if(x > bounds.getMaxX())
        {
            x=bounds.getMaxX();
        }
        if(y > bounds.getMaxY())
        {
            y = bounds.getMaxY() - GOS - ((bounds.getMinY() - GOS) % 30);
        }
        x += dx;
        y += dy;
        System.out.println(NBParts + " x=" + x + " y=" + y);
    }
    // Check for head collision between two snakes
    public boolean Head_Collision(Snake one, Snake Two) {
        if (one.x == Two.x && one.y == Two.y) {
            return true;
        }
        return false;
    }

    // Method to Check if the snake eats an apple and grow accordingly
    public synchronized CopyOnWriteArrayList<Point2D> checkeat(Rectangle2D bounds)
    {
        for(Point2D A: Apple) {
            if (x == A.getX() && y == A.getY()) {
                Parts.add(A);
                NBParts++;
                Apple.add(new Point2D.Double((1 + ran.nextInt((int) (bounds.getWidth() / 30))) * 30, (1 + ran.nextInt((int) (bounds.getHeight() / 30))) * 30));
            }
        }
        return Apple;
    }
}

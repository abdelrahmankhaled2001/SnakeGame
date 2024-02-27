import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeGame extends JFrame implements KeyListener {
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final int UNIT_SIZE = 20;
    private final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private final int DELAY = 100;
    private int[] x = new int[GAME_UNITS];
    private int[] y = new int[GAME_UNITS];
    private int snakeSize = 3;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private Food food;
    private int score = 0;
    private Image bufferImage;
    private Graphics bufferGraphics;

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        food = new Food(UNIT_SIZE, WIDTH, HEIGHT);
        startGame();
    }

    public void startGame() {
        running = true;
        initSnake();
        generateApple();
        timer = new Timer(DELAY, e -> {
            if (running) {
                move();
                checkApple();
                checkCollisions();
                repaint();
            }
        });
        timer.start();
    }

    public void restartGame() {
        running = true;
        score = 0;
        snakeSize = 3;
        direction = 'R';
        initSnake();
        generateApple();
    }

    public void initSnake() {
        for (int i = 0; i < snakeSize; i++) {
            x[i] = WIDTH / 2 - i * UNIT_SIZE;
            y[i] = HEIGHT / 2;
        }
    }

    public void paint(Graphics g) {
        if (bufferImage == null) {
            bufferImage = createImage(getWidth(), getHeight());
            bufferGraphics = bufferImage.getGraphics();
        }

        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw snake
        for (int i = 0; i < snakeSize; i++) {
            if (i == 0)
                bufferGraphics.setColor(Color.GREEN);
            else
                bufferGraphics.setColor(Color.GREEN.darker());

            bufferGraphics.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }

        // Draw apple
        food.draw(bufferGraphics);

        // Display score
        bufferGraphics.setColor(Color.WHITE);
        bufferGraphics.setFont(new Font("Arial", Font.BOLD, 20));
        bufferGraphics.drawString("Score: " + score, 10, 50); // Adjusted position

        // Display game over message
        if (!running) {
            bufferGraphics.setColor(Color.RED);
            bufferGraphics.setFont(new Font("Arial", Font.BOLD, 50));
            bufferGraphics.drawString("Game Over", WIDTH / 6, HEIGHT / 2);
            bufferGraphics.setFont(new Font("Arial", Font.BOLD, 20));
            bufferGraphics.drawString("Press Enter to Restart", WIDTH / 6 + 40, HEIGHT / 2 + 50);
        }

        g.drawImage(bufferImage, 0, 0, this);
    }

    public void generateApple() {
        appleX = food.getX();
        appleY = food.getY();
    }

    public void move() {
        for (int i = snakeSize; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] -= UNIT_SIZE;
                break;
            case 'D':
                y[0] += UNIT_SIZE;
                break;
            case 'L':
                x[0] -= UNIT_SIZE;
                break;
            case 'R':
                x[0] += UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            snakeSize++;
            score++;
            food.generateFoodPosition(WIDTH, HEIGHT);
            generateApple();
        }
    }

    public void checkCollisions() {
        // Check if snake hits itself
        for (int i = snakeSize; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }
        // Check if snake hits game boundaries
        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            running = false;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !running) {
            restartGame();
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (direction != 'D')
                    direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U')
                    direction = 'D';
                break;
            case KeyEvent.VK_LEFT:
                if (direction != 'R')
                    direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L')
                    direction = 'R';
                break;
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new SnakeGame().setVisible(true);
    }
}

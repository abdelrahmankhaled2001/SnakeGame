import java.awt.*;
import java.util.Random;

public class Food {
    private int x;
    private int y;
    private final int UNIT_SIZE;

    public Food(int unitSize, int boardWidth, int boardHeight) {
        UNIT_SIZE = unitSize;
        generateFoodPosition(boardWidth, boardHeight);
    }

    public void generateFoodPosition(int boardWidth, int boardHeight) {
        Random random = new Random();
        x = random.nextInt(boardWidth / UNIT_SIZE) * UNIT_SIZE;
        y = random.nextInt(boardHeight / UNIT_SIZE) * UNIT_SIZE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE);
    }
}

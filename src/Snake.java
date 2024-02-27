import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private int length;
    private char direction;
    private ArrayList<Point> bodySegments;

    public Snake(int initialLength, char initialDirection, Point initialPosition) {
        this.length = initialLength;
        this.direction = initialDirection;
        this.bodySegments = new ArrayList<>();
        for (int i = 0; i < initialLength; i++) {
            bodySegments.add(new Point(initialPosition.x - i, initialPosition.y));
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public ArrayList<Point> getBodySegments() {
        return bodySegments;
    }

    public void move() {
        for (int i = bodySegments.size() - 1; i > 0; i--) {
            bodySegments.get(i).setLocation(bodySegments.get(i - 1));
        }

        Point head = bodySegments.get(0);
        switch (direction) {
            case 'U':
                head.y--;
                break;
            case 'D':
                head.y++;
                break;
            case 'L':
                head.x--;
                break;
            case 'R':
                head.x++;
                break;
        }
    }

    public boolean checkCollisions(Point applePosition) {
        Point head = bodySegments.get(0);
        // Check if snake collides with itself
        for (int i = 1; i < bodySegments.size(); i++) {
            if (head.equals(bodySegments.get(i))) {
                return true;
            }
        }
        // Check if snake eats the apple
        return head.equals(applePosition);
    }

    public void grow() {
        length++;
        bodySegments.add(new Point(bodySegments.get(bodySegments.size() - 1)));
    }

    public void draw(Graphics g, int unitSize) {
        for (Point segment : bodySegments) {
            g.setColor(Color.GREEN);
            g.fillRect(segment.x * unitSize, segment.y * unitSize, unitSize, unitSize);
        }
    }
}

package src;

/**
 * src.Move contains the coordinates where the piece should be set
 * x: 0..n
 * y: 0..n
 */

public class Move {
    private int x;
    private int y;


    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

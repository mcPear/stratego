package game.board;

import java.awt.*;

public class Point {
    private Color color;
    private boolean put;

    private Point() {
    }

    private Point(Color color, boolean put) {
        this.color = color;
        this.put = put;
    }

    public boolean put(Color color) {
        if (!put) {
            put = true;
            this.color = color;
            return true;
        }
        return false;
    }

    public static Point getInstance() {
        return new Point();
    }

    public Color getColor() {
        return color;
    }

    public boolean isPut() {
        return put;
    }

    public static Point copy(Point other) {
        return new Point(other.color, other.put);
    }
}

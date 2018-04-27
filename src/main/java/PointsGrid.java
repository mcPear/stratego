import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PointsGrid {

    private List<List<Point>> points;
    private final int n;

    public PointsGrid(int n, Point point) {
        this.n = n;
        points = new ArrayList<List<Point>>();
        for (int i = 0; i < n; i++) {
            List<Point> row = new ArrayList<Point>();
            for (int j = 0; j < n; j++) {
                row.add(Point.copy(point));
            }
            points.add(row);
        }
    }

    public void putPoint(int row, int column, Color color) {
        if (row >= n || column >= n)
            throw new IllegalArgumentException("Row (" + row + ") or column (" + column + ") is greater or equal n (" + n + ")");
        points.get(row).get(column).put(color);
    }

    public int getN() {
        return n;
    }

    public Point getPoint(int row, int column) {
        return points.get(row).get(column);
    }

    public static PointsGrid getInstance(){
        return new PointsGrid(Dimension.N, Point.getInstance());
    }
}

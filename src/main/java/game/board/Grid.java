package game.board;

import game.Dimension;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Grid {

    private List<List<Point>> points;
    private final int n;

    public Grid(int n, Point point) {
        this.n = n;
        points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Point> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(Point.copy(point));
            }
            points.add(row);
        }
    }

    public boolean putPoint(Coordinates coordinates, Color color) {
        int row = coordinates.row;
        int column = coordinates.column;

        if (row >= n || column >= n)
            throw new IllegalArgumentException("Row (" + row + ") or column (" + column + ") is greater or equal n (" + n + ")");
        return points.get(row).get(column).put(color);
    }

    public int getN() {
        return n;
    }

    public Point getPoint(Coordinates coordinates) {
        return points.get(coordinates.row).get(coordinates.column);
    }

    public static Grid getInstance() {
        return new Grid(Dimension.N, Point.getInstance());
    }

    public boolean isLineFilled(List<Point> line) {
        return line.stream().allMatch(Point::isPut);
    }

    private List<Point> getRow(int row) {
        return points.get(row);
    }

    private List<Point> getColumn(int column) {
        List<Point> columnPoints = new ArrayList<>();
        points.forEach(row -> columnPoints.add(row.get(column)));
        return columnPoints;
    }

    private List<Point> getLeftChord(Coordinates coordinates) {
        int lesserCoordinate = coordinates.getLesser();
        Coordinates i = new Coordinates(coordinates.row - lesserCoordinate, coordinates.column - lesserCoordinate);
        List<Point> chord = new ArrayList<>();
        while (i.row < points.size() && i.column < points.size()) {
            chord.add(points.get(i.row).get(i.column));
            i.row++;
            i.column++;
        }

        return chord;
    }

    private List<Point> getRightChord(Coordinates coordinates) {
        List<Point> chord = new ArrayList<>();
        chord.add(points.get(coordinates.row).get(coordinates.column));
        Coordinates i = new Coordinates(coordinates.row + 1, coordinates.column - 1);

        while (i.row < points.size() && i.column >= 0) {
            chord.add(points.get(i.row).get(i.column));
            i.row++;
            i.column--;
        }

        i = new Coordinates(coordinates.row - 1, coordinates.column + 1);

        while (i.row < points.size() && i.column >= 0) {
            chord.add(points.get(i.row).get(i.column));
            i.row--;
            i.column++;
        }

        return chord;
    }


}

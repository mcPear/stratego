package game.model;

import game.Dimensions;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void unPutPoint(Coordinates coordinates) {
        int row = coordinates.row;
        int column = coordinates.column;

        if (row >= n || column >= n)
            throw new IllegalArgumentException("Row (" + row + ") or column (" + column + ") is greater or equal n (" + n + ")");
        points.get(row).get(column).unPut();
    }

    public int getN() {
        return n;
    }

    public Point getPoint(Coordinates coordinates) {
        return points.get(coordinates.row).get(coordinates.column);
    }

    public static Grid getInstance() {
        return new Grid(Dimensions.N, Point.getInstance());
    }

    private boolean isLineFilled(List<Point> line) {
        return line.stream().allMatch(Point::isPut);
    }

    private boolean isLineMissingPoints(List<Point> line, int count) {
        return line.stream().filter(Point::isPut).count() == line.size() - count;
    }

    public List<List<Point>> getAllFilledLines() {
        return getAllLines().stream().filter(line -> isLineFilled(line)).collect(Collectors.toList());
    }

    private List<List<Point>> getAllLinesMissingPoints(int count) {
        return getAllLines().stream().filter(line -> isLineMissingPoints(line, count)).collect(Collectors.toList());
    }

    public SignificantLines getSignificantLines() {
        return new SignificantLines(
                getAllFilledLines(),
                getAllLinesMissingPoints(1),
                getAllLinesMissingPoints(2));
    }

    private List<List<Point>> getAllLines() {
        List<List<Point>> lines = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            lines.add(getRow(i));
            lines.add(getColumn(i));
            lines.add(getLeftChord(new Coordinates(points.size() - 1 - i, i)));
            lines.add(getRightChord(new Coordinates(i, i)));
        }
        System.out.println("All lines count: " + lines.size());
        return lines;
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

        while (i.column < points.size() && i.row >= 0) {
            chord.add(points.get(i.row).get(i.column));
            i.row--;
            i.column++;
        }

        return chord;
    }

    public boolean isFull() {
        return points.stream().allMatch(row -> row.stream().allMatch(Point::isPut));
    }

    public List<Coordinates> getEmptyCells() {
        List<Coordinates> emptyCells = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!points.get(i).get(j).isPut()) {
                    emptyCells.add(new Coordinates(i, j));
                }
            }
        }
        return emptyCells;
    }

}

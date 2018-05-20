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

    public List<List<Point>> getAllFilledLines(Coordinates coordinates) {
        return getAllCrossedLines(coordinates).stream().filter(line -> isLineFilled(line)).collect(Collectors.toList());
    }

    private List<List<Point>> getAllLinesMissingPoints(Coordinates coordinates, int count) {
        return getAllCrossedLines(coordinates).stream().filter(line -> isLineMissingPoints(line, count)).collect(Collectors.toList());
    }

    public SignificantLines getSignificantLines(Coordinates coordinates) {
        return new SignificantLines(
                getAllFilledLines(coordinates),
                getAllLinesMissingPoints(coordinates, 1),
                getAllLinesMissingPoints(coordinates, 2));
    }

    private List<List<Point>> getAllCrossedLines(Coordinates coordinates) {
        List<List<Point>> lines = new ArrayList<>();
        lines.add(getRow(coordinates.row));
        lines.add(getColumn(coordinates.column));
        lines.add(getLeftChord(coordinates));
        lines.add(getRightChord(coordinates));
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

    private List<Coordinates> getBusyCells() {
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

    public Coordinates getTheMiddleOfPutPoints() {
        int row = 0;
        int column = 0;
        List<Coordinates> busyCells = getBusyCells();
        for (int i = 0; i < busyCells.size(); i++) {
            row += busyCells.get(i).row;
            column += busyCells.get(i).column;
        }
        row = row / busyCells.size();
        column = column / busyCells.size();
//        System.out.println("The middle: "+row+","+column);
        return new Coordinates(row, column);
    }

}

package game.model;

import game.Dimensions;

public class Coordinates {
    int row;
    int column;

    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getLesser() {
        return row > column ? column : row;
    }

    public Coordinates(Coordinates other) {
        row = other.row;
        column = other.column;
    }

    public double getDistanceFromTheMiddle() {
        return Math.sqrt(Math.pow(row - Dimensions.THE_MIDDLE, 2) + Math.pow(column - Dimensions.THE_MIDDLE, 2));
    }

    public double getDistanceFrom(Coordinates cell) {
        return Math.sqrt(Math.pow(row - cell.row, 2) + Math.pow(column - cell.column, 2));
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}

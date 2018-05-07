package game.model;

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
}

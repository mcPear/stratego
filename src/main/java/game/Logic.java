package game;

import algorithm.MinMax;
import algorithm.MinMaxAlphaBeta;
import game.model.Coordinates;
import game.model.Point;
import game.model.Store;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

public class Logic {

    private final Store store;

    public Logic(Store store) {
        this.store = store;
    }

    public boolean handleHumanPut(MouseEvent e) {
        Coordinates coordinates = getGridCoordinates(e);
        boolean isPut = putPoint(coordinates);
        if (isPut) {
            updateScore(coordinates);
            store.changeTurn();
        }
        return isPut;
    }


    private boolean putPoint(Coordinates coordinates) {
        Color currentColor = store.getCurrentTurnPlayer().getColor();
        return store.grid.putPoint(coordinates, currentColor);
    }

    private Coordinates getGridCoordinates(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        x -= Dimensions.WINDOW_MARGIN;
        y -= Dimensions.WINDOW_MARGIN;
        int xMod = x % Dimensions.CELL_LENGTH;
        int yMod = y % Dimensions.CELL_LENGTH;
        int row = (x - xMod) / Dimensions.CELL_LENGTH;
        int column = (y - yMod) / Dimensions.CELL_LENGTH;
        return new Coordinates(row, column);
    }

    private void updateScore(Coordinates coordinates) {
        int score = getScore(coordinates);
        store.getCurrentTurnPlayer().incrementScore(score);
    }

    private int getScore(Coordinates coordinates) {
        int sum = 0;
        List<List<Point>> filledLines = store.grid.getAllFilledLines(coordinates);
        for (List<Point> line : filledLines) {
            sum += line.size();
        }
        return sum;
    }

    public void moveAsComputerA() {
        List<Coordinates> emptyCells = store.grid.getEmptyCells();
        Coordinates coordinates = emptyCells.get(new Random(System.currentTimeMillis()).nextInt(emptyCells.size()));
//        Coordinates coordinates = new MinMax(store).getNextCoordinatesAB();
        if (putPoint(coordinates)) {
            updateScore(coordinates);
            store.changeTurn();
        }
    }

    public void moveAsComputerB() {
        Coordinates coordinates = new MinMaxAlphaBeta(store).getNextCoordinates();
        if (putPoint(coordinates)) {
            updateScore(coordinates);
            store.changeTurn();
        }
    }

    public boolean isGameOver() {
        return store.grid.isFull();
    }

}

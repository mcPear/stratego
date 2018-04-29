package game;

import game.board.Coordinates;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Logic {

    private boolean colorFlag;
    private final Store store;

    public Logic(Store store) {
        this.store = store;
    }

    public void handleMouseClicked(MouseEvent e) {
        Coordinates coordinates = getGridCoordinates(e);
        //assignPointsToUser
        //calculateWinner
        putPoint(coordinates);
        updateScore(coordinates);
    }

    private void putPoint(Coordinates coordinates) {
        Color currentColor = colorFlag ? store.player1.getColor() : store.player2.getColor();
        if (store.grid.putPoint(coordinates, currentColor)) {
            colorFlag = !colorFlag;
        }

    }

    private Coordinates getGridCoordinates(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        x -= Dimension.WINDOW_MARGIN;
        y -= Dimension.WINDOW_MARGIN;
        int xMod = x % Dimension.CELL_LENGTH;
        int yMod = y % Dimension.CELL_LENGTH;
        int row = (x - xMod) / Dimension.CELL_LENGTH;
        int column = (y - yMod) / Dimension.CELL_LENGTH;
        return new Coordinates(row, column);
    }

    private void updateScore(Coordinates coordinates) {

    }

}

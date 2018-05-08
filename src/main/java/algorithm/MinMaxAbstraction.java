package algorithm;

import game.model.Coordinates;
import game.model.Player;
import game.model.Point;
import game.model.Store;

import java.awt.*;
import java.util.List;

public abstract class MinMaxAbstraction {

    private final Store store;
    private final Color simulationColor = Color.BLACK;
    private int depth = 0;
    private boolean useMax = true;
    private Player player;
    private Player opponent;
    protected int minimum = 0;
    private final int MAX_DEPTH = 2;

    public MinMaxAbstraction(Store store) {
        this.store = store;
    }

    private boolean isLeaf() {
        return store.grid.isFull() || depth >= MAX_DEPTH;
    }

    private void beginMinMax() {
        depth++;
        useMax = !useMax;
        store.changeTurn();
    }

    private void endMinMax() {
        store.changeTurn();
        useMax = !useMax;
        depth--;
    }

    private void beginChildMinMax(Coordinates cell) {
        store.grid.putPoint(cell, simulationColor);
        updateScore(cell);
    }

    private void endChildMinMax(Coordinates cell) {
        reduceScore(cell);
        store.grid.unPutPoint(cell);
    }

    private int evaluate() { //stan gry to nie są moje ostatnie przecięte linie, ale moje wszytskie przecięte linie
        //dla gracza wykonującego ruch
        //dodać punkty za linie, które domnkął
        //odjąc punkty za te które są wystawione bez 1, w czasie ??
//        SignificantLines significantLines = store.grid.getSignificantLines(coordinates);
//        int value = 0;
//        for (List<Point> line : significantLines.getFilled()) {
//            value += 2 * line.size();
//        }
//        for (List<Point> line : significantLines.getMissingOnePoint()) {
//            value -= 2 * line.size();
//        }
//        return value;
//        System.out.println(coordinates + " | " + (player.getScore() - opponent.getScore()));
        return player.getScore() - opponent.getScore();
    }

    private void reduceScore(Coordinates coordinates) {
        int score = getScore(coordinates);
        store.getCurrentTurnPlayer().decrementScore(score);
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

    protected class Result {
        private Integer value;

        public void set(int value) {
            this.value = value;
        }

        public void setMax(int value) {
            this.value = this.value == null ? value : Math.max(this.value, value);
        }

        public void setMin(int value) {
            this.value = this.value == null ? value : Math.min(this.value, value);
        }

        public Integer get() {
            return value;
        }
    }

}

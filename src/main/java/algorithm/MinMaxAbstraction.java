package algorithm;

import game.model.*;
import game.model.Point;

import java.awt.*;
import java.util.List;
import java.util.function.Function;

public abstract class MinMaxAbstraction {

    protected final Store store;
    private final Color simulationColor = Color.BLACK;
    private int depth = 0;
    protected boolean useMax = true;
    protected Player player;
    protected Player opponent;
    protected int minimum = 0;
    private final int maxDepth;
    private final Function<HeuristicParameters, Integer> evaluateFunction;
    protected HeuristicParameters heuristicParameters;
    private int moveTimeSeconds;
    protected long startTime;

    public MinMaxAbstraction(Store store, int maxDepth, Function<HeuristicParameters, Integer> evaluateFunction, int moveTimeSeconds) {
        this.store = store;
        this.maxDepth = maxDepth;
        this.evaluateFunction = evaluateFunction;
        this.moveTimeSeconds = moveTimeSeconds;
    }

    protected boolean isLeaf() {
        return store.grid.isFull() || depth >= maxDepth;
    }

    protected void beginMinMax() {
        depth++;
        useMax = !useMax;
        store.changeTurn();
    }

    protected void endMinMax() {
        store.changeTurn();
        useMax = !useMax;
        depth--;
    }

    protected void beginChildMinMax(Coordinates cell) {
        store.grid.putPoint(cell, simulationColor);
        updateScore(cell);
    }

    protected void endChildMinMax(Coordinates cell) {
        reduceScore(cell);
        store.grid.unPutPoint(cell);
    }

    protected int evaluate(Coordinates theLastPutPoint) {
        heuristicParameters.theLastPutPoint = theLastPutPoint;
        heuristicParameters.isPlayerTurn = store.getCurrentTurnPlayer().equals(player);
        return evaluateFunction.apply(heuristicParameters);
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

    protected boolean isTimeOut() {
        return System.currentTimeMillis() - startTime >= moveTimeSeconds * 1_000;
    }

}

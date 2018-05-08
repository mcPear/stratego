package algorithm;

import game.model.Coordinates;
import game.model.Player;
import game.model.Point;
import game.model.Store;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MinMax {

    private final Store store;
    private final Color simulationColor = Color.BLACK;
    private int depth = 0;
    private boolean useMax = true;
    private Player player;
    private Player opponent;
    private int minimum = 0;
    private final int MAX_DEPTH = 2;

    public MinMax(Store store) {
        this.store = store;
    }

    public Coordinates getNextCoordinates() {//as second player here
        player = store.getCurrentTurnPlayer();
        opponent = store.getCurrentTurnOpponent();
        useMax = true;

        List<Pair<Coordinates, Integer>> coordinatesValues = new ArrayList<>();

        store.grid.getEmptyCells().forEach(cell -> {
            store.grid.putPoint(cell, simulationColor);
            updateScore(cell);

            coordinatesValues.add(new Pair<>(cell, minMax()));

            reduceScore(cell);
            store.grid.unPutPoint(cell);
        });

//        store.grid.getEmptyCells().stream().forEach(cell -> coordinatesValues.add(new Pair(cell, minMax(cell))));
        Pair<Coordinates, Integer> result = coordinatesValues.stream().max(Comparator.comparingInt(Pair::getValue)).get();
        //logs
        coordinatesValues.forEach(pair -> System.out.print(pair.getValue() + ","));
        System.out.println("result: " + result.getValue() + '\n');
        //
        return result.getKey();
//        return store.grid.getEmptyCells().stream().max(Comparator.comparingInt(cell -> minMax(cell))).get(); //FIXME load all to map and compare ints only
    }

//    private int minMax() {//tu już masz jakby coordy nałożone
//        store.grid.putPoint(coordinates, simulationColor);
//        updateScore(coordinates);
//        int result;
////        System.out.println(depth);
//        if (store.grid.isFull() || depth >= MAX_DEPTH) {
//            result = evaluate();
//        } else {
//            depth++;
//            useMax = !useMax;
//            store.changeTurn();
//            Stream<Integer> values = store.grid.getEmptyCells().stream().map(cell -> minMax(cell));
//            result = useMax ? values.max(Integer::compareTo).get() : values.min(Integer::compareTo).get();
//            store.changeTurn();
//            useMax = !useMax;
//            depth--;
//        }
//        reduceScore(coordinates);
//        store.grid.unPutPoint(coordinates);
//        return result;
//    }

    private int minMax() {//tu już masz jakby coordy nałożone
        depth++;
        useMax = !useMax;
        store.changeTurn();

        ResultContainer resultContainer = new ResultContainer();

        if (store.grid.isFull() || depth >= MAX_DEPTH) {
            resultContainer.value = evaluate();
        } else {
            store.grid.getEmptyCells().forEach(cell -> {
                store.grid.putPoint(cell, simulationColor);
                updateScore(cell);

                if (useMax) {
                    resultContainer.value = resultContainer.value == null ? minMax() : Math.max(resultContainer.value, minMax());
                } else {
                    resultContainer.value = resultContainer.value == null ? minMax() : Math.min(resultContainer.value, minMax());
                }

                reduceScore(cell);
                store.grid.unPutPoint(cell);
            });
        }

        store.changeTurn();
        useMax = !useMax;
        depth--;
        return resultContainer.value;
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

    private class ResultContainer {
        public Integer value;
    }

}



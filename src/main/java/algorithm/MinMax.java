package algorithm;

import game.model.Coordinates;
import game.model.Player;
import game.model.Point;
import game.model.Store;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class MinMax {

    private final Store store;
    private final Color simulationColor = Color.BLACK;
    private int depth = 0;
    private boolean useMax = true;
    private Player player;
    private Player opponent;
    private int minimum = 0;

    public MinMax(Store store) {
        this.store = store;
    }

    public Coordinates getNextCoordinates() {//as second player here
        player = store.getCurrentTurnPlayer();
        opponent = store.getCurrentTurnOpponent();
        useMax = true;
        return store.grid.getEmptyCells().stream().max(Comparator.comparingInt(cell -> minMax(cell))).get(); //FIXME load all to map and compare ints only
    }

    private int minMax(Coordinates coordinates) {//FIXME dont pass coordinates, put and remove in for
        store.grid.putPoint(coordinates, simulationColor);
        updateScore(coordinates);
        int result;
        //System.out.println(depth);
        if (store.grid.isFull() || depth >= 3) {
            result = evaluate(coordinates);
//            if(result<minimum){
//                minimum = result;
//                System.out.println(result);
//            }
        } else {
            depth++;
            useMax = !useMax;
            store.changeTurn();
            Stream<Integer> values = store.grid.getEmptyCells().stream().map(cell -> minMax(cell));
            result = useMax ? values.max(Integer::compareTo).get() : values.min(Integer::compareTo).get();
            store.changeTurn();
            useMax = !useMax;
            depth--;
        }
        reduceScore(coordinates);
        store.grid.unPutPoint(coordinates);
        return result;
    }

    private int evaluate(Coordinates coordinates) { //stan gry to nie są moje ostatnie przecięte linie, ale moje wszytskie przecięte linie
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
        return player.getScore() - opponent.getScore(); //one dont't change players here
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

}

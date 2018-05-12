package algorithm;

import game.model.Coordinates;
import game.model.Player;
import game.model.Point;
import game.model.Store;
import javafx.util.Pair;

import java.awt.*;
import java.util.List;

//TODO connect abstraction
public class MinMaxAlphaBeta {

    private final Store store;
    private final Color simulationColor = Color.BLACK;
    private int depth = 0;
    private boolean useMax = true;
    private boolean isCutOff = false;
    private Player player;
    private Player opponent;
    private int minimum = 0;
    private final int MAX_DEPTH = 1;

    //-3,-2,-2,-2,-2,-3,-1,-3,-2,-2,-2,-2,-2,-2,-3,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-3,-2,-2,-2,-2,-2,-2,-3,-1,-3,-2,-2,-2,-2,-3,-1,result: -1
    public MinMaxAlphaBeta(Store store) {
        this.store = store;
    }

    //TODO launch with cutoff
    public Coordinates getNextCoordinates() {//as second player here
        player = store.getCurrentTurnPlayer();
        opponent = store.getCurrentTurnOpponent();
        useMax = true;
        int beta = 4 * store.grid.getN() * store.grid.getN();
        int alpha = -beta;
        List<Coordinates> emptyCells = store.grid.getEmptyCells();
        Coordinates alphaCoordinates = emptyCells.get(0);

        for (int i = 0; i < emptyCells.size(); i++) {
            Pair<Coordinates, Integer> result = new Pair(emptyCells.get(i), minMaxAB(emptyCells.get(i), alpha, beta));
            System.out.print(result.getValue() + ",");
            if (result.getValue() > alpha) {
                alpha = result.getValue();
                alphaCoordinates = result.getKey();
            }
        }

        System.out.println("result: " + alpha + '\n');
        return alphaCoordinates;
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
        //System.out.println(coordinates + " | " + (player.getScore() - opponent.getScore()));
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
        java.util.List<java.util.List<game.model.Point>> filledLines = store.grid.getAllFilledLines(coordinates);
        for (java.util.List<Point> line : filledLines) {
            sum += line.size();
        }
        return sum;
    }

    private int minMaxAB(Coordinates coordinates, int alpha, int beta) {//FIXME dont pass coordinates, put and remove in for
        store.grid.putPoint(coordinates, simulationColor);
        updateScore(coordinates);
        Integer result = null;
//        System.out.println("ab" + depth);
        if (store.grid.isFull() || depth >= MAX_DEPTH) {
            result = evaluate(coordinates);
//            if(result<minimum){
//                minimum = result;
//                System.out.println(result);
//            }
        } else {
            isCutOff = false;
            depth++;
            useMax = !useMax;
            store.changeTurn();
//            Stream<Integer> values = store.grid.getEmptyCells().stream().map(cell -> minMax(cell));
            List<Coordinates> emptyCells = store.grid.getEmptyCells();
            if (useMax) {
                for (int i = 0; i < emptyCells.size() && !isCutOff; i++) {
                    result = minMaxAB(emptyCells.get(i), alpha, beta);
                    alpha = Math.max(alpha, result);
                    if (alpha >= beta) {
                        isCutOff = true;
                    }
                }
                result = alpha;
            } else {
                for (int i = 0; i < emptyCells.size() && !isCutOff; i++) {
                    result = minMaxAB(emptyCells.get(i), alpha, beta);
                    beta = Math.min(beta, result);
                    if (beta <= alpha) {
                        isCutOff = true;
                    }
                }
                result = beta;
            }
//            result = useMax ? values.max(Integer::compareTo).get() : values.min(Integer::compareTo).get();
            isCutOff = false;
            store.changeTurn();
            useMax = !useMax;
            depth--;
        }
        reduceScore(coordinates);
        store.grid.unPutPoint(coordinates);
        return result;
    }


}



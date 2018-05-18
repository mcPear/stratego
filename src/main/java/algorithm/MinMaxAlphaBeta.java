package algorithm;

import game.model.Coordinates;
import game.model.HeuristicParameters;
import game.model.Store;
import javafx.util.Pair;

import java.util.List;
import java.util.function.Function;

public class MinMaxAlphaBeta extends MinMaxAbstraction {

    private boolean isCutOff = false;
    private long startTime;

    public MinMaxAlphaBeta(Store store, int maxDepth, Function<HeuristicParameters, Integer> evaluateFunction) {
        super(store, maxDepth, evaluateFunction);
    }

    public Coordinates getNextCoordinates() {//as second player here
        startTime = System.currentTimeMillis();
        player = store.getCurrentTurnPlayer();
        opponent = store.getCurrentTurnOpponent();
        heuristicParameters = new HeuristicParameters(player, opponent, store.grid);
        useMax = true;
        int beta = 4 * store.grid.getN() * store.grid.getN();
        int alpha = -beta;

        List<Coordinates> emptyCells = store.grid.getEmptyCells();
        Coordinates alphaCoordinates = emptyCells.get(0);

        for (int i = 0; i < emptyCells.size(); i++) {
            Coordinates cell = emptyCells.get(i);
            beginChildMinMax(cell);
            Pair<Coordinates, Integer> result = new Pair(cell, minMax(alpha, beta, cell));
            System.out.print(result.getValue() + ",");
            if (result.getValue() > alpha) {
                alpha = result.getValue();
                alphaCoordinates = result.getKey();
            }
            endChildMinMax(cell);
        }

        System.out.println("result: " + alpha + '\n');
        return alphaCoordinates;
    }

    private int minMax(int alpha, int beta, Coordinates theLastPutPoint) {
        beginMinMax();
        Result result = new Result();
        if (isLeaf() || isTimeOut()) {
            result.set(evaluate(theLastPutPoint));
        } else {
            childrenMinMax(result, alpha, beta);
        }
        endMinMax();
        return result.get();
    }

    private void childrenMinMax(Result result, int alpha, int beta) {
        isCutOff = false;
        List<Coordinates> emptyCells = store.grid.getEmptyCells();
        for (int i = 0; i < emptyCells.size() && !isCutOff; i++) {
            Coordinates cell = emptyCells.get(i);
            beginChildMinMax(cell);
            if (useMax) {
                alpha = Math.max(alpha, minMax(alpha, beta, cell));
                if (alpha >= beta) {
                    isCutOff = true;
                }
                result.set(alpha);
            } else {
                beta = Math.min(beta, minMax(alpha, beta, cell));
                if (beta <= alpha) {
                    isCutOff = true;
                }
                result.set(beta);
            }
            endChildMinMax(cell);
        }
        isCutOff = false;
    }

    private boolean isTimeOut() {
        return System.currentTimeMillis() - startTime >= 15_000;
    }

}




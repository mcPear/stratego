package algorithm;

import game.model.Coordinates;
import game.model.HeuristicParameters;
import game.model.Store;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MinMax extends MinMaxAbstraction {

    public MinMax(Store store, int maxDepth, Function<HeuristicParameters, Integer> evaluateFunction, int moveTimeSeconds) {
        super(store, maxDepth, evaluateFunction, moveTimeSeconds);
    }

    public Coordinates getNextCoordinates() {//as second player here
        startTime = System.currentTimeMillis();
        player = store.getCurrentTurnPlayer();
        opponent = store.getCurrentTurnOpponent();
        heuristicParameters = new HeuristicParameters(player, opponent, store.grid);
        useMax = true;

        List<Pair<Coordinates, Integer>> coordinatesValues = new ArrayList<>();

        store.grid.getEmptyCells().forEach(cell -> {
            beginChildMinMax(cell);
            coordinatesValues.add(new Pair<>(cell, minMax(cell)));
            endChildMinMax(cell);
        });

        Pair<Coordinates, Integer> result = coordinatesValues.stream().max(Comparator.comparingInt(Pair::getValue)).get();
        //logs
        coordinatesValues.forEach(pair -> System.out.print(pair.getValue() + ","));
        System.out.println("result: " + result.getValue() + '\n');
        //
        return result.getKey();
    }

    private int minMax(Coordinates theLastPutPoint) {
        beginMinMax();
        Result result = new Result();
        if (isLeaf() || isTimeOut()) {
            result.set(evaluate(theLastPutPoint));
        } else {
            childrenMinMax(result);
        }
        endMinMax();
        return result.get();
    }

    private void childrenMinMax(Result result) {
        store.grid.getEmptyCells().forEach(cell -> {
            beginChildMinMax(cell);

            if (useMax) {
                result.setMax(minMax(cell));
            } else {
                result.setMin(minMax(cell));
            }

            endChildMinMax(cell);
        });
    }

}



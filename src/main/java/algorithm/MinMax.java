package algorithm;

import game.model.Coordinates;
import game.model.Store;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MinMax extends MinMaxAbstraction {

    public MinMax(Store store) {
        super(store);
    }

    public Coordinates getNextCoordinates() {//as second player here
        player = store.getCurrentTurnPlayer();
        opponent = store.getCurrentTurnOpponent();
        useMax = true;

        List<Pair<Coordinates, Integer>> coordinatesValues = new ArrayList<>();

        store.grid.getEmptyCells().forEach(cell -> {
            beginChildMinMax(cell);
            coordinatesValues.add(new Pair<>(cell, minMax()));
            endChildMinMax(cell);
        });

        Pair<Coordinates, Integer> result = coordinatesValues.stream().max(Comparator.comparingInt(Pair::getValue)).get();
        //logs
        coordinatesValues.forEach(pair -> System.out.print(pair.getValue() + ","));
        System.out.println("result: " + result.getValue() + '\n');
        //
        return result.getKey();
    }

    private int minMax() {
        beginMinMax();
        Result result = new Result();
        if (isLeaf()) {
            result.set(evaluate());
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
                result.setMax(minMax());
            } else {
                result.setMin(minMax());
            }

            endChildMinMax(cell);
        });
    }

}



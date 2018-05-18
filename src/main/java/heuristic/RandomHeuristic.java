package heuristic;

import game.model.HeuristicParameters;

import java.util.Random;
import java.util.function.Function;

public class RandomHeuristic implements Function<HeuristicParameters, Integer> {
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    @Override
    public Integer apply(HeuristicParameters heuristicParameters) {
        return RANDOM.nextInt(10);
    }

    public static Function<HeuristicParameters, Integer> get() {
        return new RandomHeuristic();
    }

}

package heuristic;

import game.model.HeuristicParameters;

import java.util.function.Function;

public class MinOpponentScoreHeuristic implements Function<HeuristicParameters, Integer> {

    @Override
    public Integer apply(HeuristicParameters heuristicParameters) {
        return -heuristicParameters.opponent.getScore();
    }

    public static Function<HeuristicParameters, Integer> get() {
        return new MinOpponentScoreHeuristic();
    }

}

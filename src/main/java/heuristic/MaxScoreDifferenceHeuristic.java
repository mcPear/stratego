package heuristic;

import game.model.HeuristicParameters;

import java.util.function.Function;

public class MaxScoreDifferenceHeuristic implements Function<HeuristicParameters, Integer> {

    @Override
    public Integer apply(HeuristicParameters heuristicParameters) {
        return heuristicParameters.player.getScore() - heuristicParameters.opponent.getScore();
    }

    public static Function<HeuristicParameters, Integer> get() {
        return new MaxScoreDifferenceHeuristic();
    }

}

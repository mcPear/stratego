package heuristic;

import game.model.HeuristicParameters;
import game.model.Point;
import game.model.SignificantLines;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class DoNotTankHeuristic implements Function<HeuristicParameters, Integer> {

    @Override
    public Integer apply(HeuristicParameters heuristicParameters) {
        SignificantLines significantLines = heuristicParameters.grid.getSignificantLines(heuristicParameters.theLastPutPoint);
        List<List<Point>> tankedLines = significantLines.getMissingOnePoint();
        int result = heuristicParameters.player.getScore() - heuristicParameters.opponent.getScore();
        if (!tankedLines.isEmpty()) {
            if (heuristicParameters.isPlayerTurn) {
                result += tankedLines.stream().max(Comparator.comparingInt(List::size)).get().size();
            } else {
                result -= tankedLines.stream().max(Comparator.comparingInt(List::size)).get().size();
            }
        }
        return result;
    }

    public static Function<HeuristicParameters, Integer> get() {
        return new DoNotTankHeuristic();
    }

}

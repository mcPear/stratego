package heuristic;

import game.model.HeuristicParameters;
import game.model.Point;
import game.model.SignificantLines;

import java.util.List;
import java.util.function.Function;

public class DoNotTankHeuristic implements Function<HeuristicParameters, Integer> {

    @Override
    public Integer apply(HeuristicParameters heuristicParameters) {
        SignificantLines significantLines = heuristicParameters.grid.getSignificantLines();
        int tankedLinesValue = 0;
        for (List<Point> line : significantLines.getMissingOnePoint()) {
            tankedLinesValue += line.size();
        }
        if(heuristicParameters.playerPutTheLastPoint) //TODO find out how to get it
        return heuristicParameters.player.getScore() - heuristicParameters.opponent.getScore();
    }

    public static Function<HeuristicParameters, Integer> get() {
        return new DoNotTankHeuristic();
    }

}

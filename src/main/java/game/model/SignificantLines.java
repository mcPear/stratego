package game.model;

import java.util.List;

public class SignificantLines {
    private final List<List<Point>> filled;
    private final List<List<Point>> missingOnePoint;
    private final List<List<Point>> missingTwoPoints;

    public SignificantLines(List<List<Point>> filled, List<List<Point>> missingOnePoint, List<List<Point>> missingTwoPoints) {
        this.filled = filled;
        this.missingOnePoint = missingOnePoint;
        this.missingTwoPoints = missingTwoPoints;
    }

    public List<List<Point>> getFilled() {
        return filled;
    }

    public List<List<Point>> getMissingOnePoint() {
        return missingOnePoint;
    }

    public List<List<Point>> getMissingTwoPoints() {
        return missingTwoPoints;
    }

}

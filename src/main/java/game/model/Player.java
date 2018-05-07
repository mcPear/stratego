package game.model;

import java.awt.*;

public class Player {

    private final Color color;
    private int score;

    public Player(Color color) {
        this.color = color;
        this.score = 0;
    }

    public Color getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }


    public void incrementScore(int sum) {
        score += sum;
    }

    public void decrementScore(int sum) {
        score -= sum;
    }


}

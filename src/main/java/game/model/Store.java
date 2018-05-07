package game.model;

import java.awt.*;

public class Store {

    public final Grid grid;
    public final Player player1;
    public final Player player2;
    public boolean isFirstPlayerTurn = true;

    public Store(Color color1, Color color2) {
        this.grid = Grid.getInstance();
        this.player1 = new Player(color1);
        this.player2 = new Player(color2);
    }

    public void changeTurn() {
        isFirstPlayerTurn = !isFirstPlayerTurn;
    }

    public Player getCurrentTurnPlayer() {
        return isFirstPlayerTurn ? player1 : player2;
    }

    public Player getCurrentTurnOpponent() {
        return isFirstPlayerTurn ? player2 : player1;
    }
}

package game;

import game.board.Grid;

import java.awt.*;

public class Store {

    public final Grid grid;
    public final Player player1;
    public final Player player2;

    public Store(Color color1, Color color2) {
        this.grid = Grid.getInstance();
        this.player1 = new Player(color1);
        this.player2 = new Player(color2);
    }
}

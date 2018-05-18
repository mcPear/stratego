package game.model;

public class HeuristicParameters {

    public HeuristicParameters(Player player, Player opponent, Grid grid) {
        this.player = player;
        this.opponent = opponent;
        this.grid = grid;
    }

    public Player player;
    public Player opponent;
    public Grid grid;
    public Coordinates theLastPutPoint;
    public boolean isPlayerTurn;
}

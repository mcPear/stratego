import java.awt.*;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            PointsGrid pointsGrid = PointsGrid.getInstance();
            GameWindow gameWindow = new GameWindow(pointsGrid);
            GameLogic gameLogic = new GameLogic(pointsGrid);
            gameWindow.addMouseListener(new GameMouseListener(gameLogic, gameWindow));
            gameWindow.setVisible(true);
        });

    }


}

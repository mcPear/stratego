import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(PointsGrid pointsGrid) {

        initUI(pointsGrid);
    }

    private void initUI(PointsGrid pointsGrid) {
        add(new GameCanvas(pointsGrid));
        setTitle("Stratego");
        setSize(Dimension.WINDOW_SIZE, Dimension.WINDOW_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
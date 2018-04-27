import java.awt.*;
import java.awt.event.MouseEvent;

public class GameLogic {

    private final PointsGrid pointsGrid;
    private boolean colorFlag;

    public GameLogic(PointsGrid pointsGrid) {
        this.pointsGrid = pointsGrid;
    }

    public void handleMouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        x -= Dimension.WINDOW_MARGIN;
        y -= Dimension.WINDOW_MARGIN;
        int xMod = x % Dimension.CELL_LENGTH;
        int yMod = y % Dimension.CELL_LENGTH;
        int xN = (x - xMod) / Dimension.CELL_LENGTH;
        int yN = (y - yMod) / Dimension.CELL_LENGTH;

        if (colorFlag) {
            pointsGrid.putPoint(xN, yN, Color.BLUE);
        } else{
            pointsGrid.putPoint(xN, yN, Color.RED);
        }
        colorFlag = !colorFlag;
    }

}

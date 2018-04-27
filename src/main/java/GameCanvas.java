import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {
    private Color tempColor;
    private final PointsGrid pointsGrid;

    public GameCanvas(PointsGrid pointsGrid) {
        this.pointsGrid = pointsGrid;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing((Graphics2D) g);
    }

    private void doDrawing(Graphics2D graphics2D) {
        drawGrid(graphics2D);
        drawPoints(graphics2D, pointsGrid);
    }

    private void drawGrid(Graphics2D graphics2D) {

        for (int i = 0; i <= Dimension.N; i++) {
            graphics2D.drawLine(
                    Dimension.WINDOW_MARGIN,
                    Dimension.WINDOW_MARGIN + (i * Dimension.CELL_LENGTH),
                    Dimension.WINDOW_MARGIN + Dimension.BORDER_LENGTH,
                    Dimension.WINDOW_MARGIN + (i * Dimension.CELL_LENGTH));
        }
        for (int i = 0; i <= Dimension.N; i++) {
            graphics2D.drawLine(
                    Dimension.WINDOW_MARGIN + (i * Dimension.CELL_LENGTH),
                    Dimension.WINDOW_MARGIN,
                    Dimension.WINDOW_MARGIN + (i * Dimension.CELL_LENGTH),
                    Dimension.WINDOW_MARGIN + Dimension.BORDER_LENGTH);
        }

    }

    private void drawPoints(Graphics2D graphics2D, PointsGrid pointsGrid) {
        int n = pointsGrid.getN();
        Point point;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                point = pointsGrid.getPoint(i, j);
                if (point.isPut()) {
                    drawPoint(graphics2D, i, j, point.getColor());
                }
            }
        }
    }

    private void drawPoint(Graphics2D graphics2D, int row, int column, Color color) {
        saveCurrentColor(graphics2D);
        graphics2D.setColor(color);
        int x = Dimension.WINDOW_MARGIN + Dimension.POINT_MARGIN + row * Dimension.CELL_LENGTH;
        int y = Dimension.WINDOW_MARGIN + Dimension.POINT_MARGIN + column * Dimension.CELL_LENGTH;
        graphics2D.fillOval(x, y, Dimension.POINT_RADIUS, Dimension.POINT_RADIUS);
        restoreCurrentColor(graphics2D);
    }

    private void saveCurrentColor(Graphics2D graphics2D) {
        tempColor = graphics2D.getColor();
    }

    private void restoreCurrentColor(Graphics2D graphics2D) {
        graphics2D.setColor(tempColor);
    }

}
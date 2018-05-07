package game;

import game.model.Coordinates;
import game.model.Point;
import game.model.Store;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    private Color tempColor;
    private final Store store;

    public Canvas(Store store) {
        this.store = store;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing((Graphics2D) g);
    }

    private void doDrawing(Graphics2D graphics2D) {
        drawGrid(graphics2D);
        drawPoints(graphics2D);
        drawScore(graphics2D);
    }

    private void drawGrid(Graphics2D graphics2D) {

        for (int i = 0; i <= Dimensions.N; i++) {
            graphics2D.drawLine(
                    Dimensions.WINDOW_MARGIN,
                    Dimensions.WINDOW_MARGIN + (i * Dimensions.CELL_LENGTH),
                    Dimensions.WINDOW_MARGIN + Dimensions.BORDER_LENGTH,
                    Dimensions.WINDOW_MARGIN + (i * Dimensions.CELL_LENGTH));
        }
        for (int i = 0; i <= Dimensions.N; i++) {
            graphics2D.drawLine(
                    Dimensions.WINDOW_MARGIN + (i * Dimensions.CELL_LENGTH),
                    Dimensions.WINDOW_MARGIN,
                    Dimensions.WINDOW_MARGIN + (i * Dimensions.CELL_LENGTH),
                    Dimensions.WINDOW_MARGIN + Dimensions.BORDER_LENGTH);
        }

    }

    private void drawPoints(Graphics2D graphics2D) {
        int n = store.grid.getN();
        Point point;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                point = store.grid.getPoint(new Coordinates(i, j));
                if (point.isPut()) {
                    drawPoint(graphics2D, i, j, point.getColor());
                }
            }
        }
    }

    private void drawPoint(Graphics2D graphics2D, int row, int column, Color color) {
        saveCurrentColor(graphics2D);
        graphics2D.setColor(color);
        int x = Dimensions.WINDOW_MARGIN + Dimensions.POINT_MARGIN + row * Dimensions.CELL_LENGTH;
        int y = Dimensions.WINDOW_MARGIN + Dimensions.POINT_MARGIN + column * Dimensions.CELL_LENGTH;
        graphics2D.fillOval(x, y, Dimensions.POINT_RADIUS, Dimensions.POINT_RADIUS);
        restoreCurrentColor(graphics2D);
    }

    private void drawScore(Graphics2D graphics2D) {
        saveCurrentColor(graphics2D);
        graphics2D.setFont(new Font("Verdana", Font.BOLD, Dimensions.FONT_SIZE));
        graphics2D.setColor(store.player1.getColor());
        graphics2D.drawString("" + store.player1.getScore(), Dimensions.WINDOW_MARGIN, Dimensions.WINDOW_HEIGHT - Dimensions.WINDOW_MARGIN);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString("vs", Dimensions.WINDOW_WIDTH / 4, Dimensions.WINDOW_HEIGHT - Dimensions.WINDOW_MARGIN);
        graphics2D.setColor(store.player2.getColor());
        graphics2D.drawString("" + store.player2.getScore(), Dimensions.WINDOW_WIDTH / 2, Dimensions.WINDOW_HEIGHT - Dimensions.WINDOW_MARGIN);
        restoreCurrentColor(graphics2D);
    }

    private void saveCurrentColor(Graphics2D graphics2D) {
        tempColor = graphics2D.getColor();
    }

    private void restoreCurrentColor(Graphics2D graphics2D) {
        graphics2D.setColor(tempColor);
    }

}
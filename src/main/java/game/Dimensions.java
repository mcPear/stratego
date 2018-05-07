package game;

public class Dimensions {

    public static int WINDOW_MARGIN = 20;
    public static int N = 5;
    public static int CELL_LENGTH = 65;
    public static int FONT_SIZE = 40;
    public static int BORDER_LENGTH = CELL_LENGTH * N;
    public static int WINDOW_WIDTH = 2 * WINDOW_MARGIN + N * CELL_LENGTH;
    public static int WINDOW_HEIGHT = WINDOW_WIDTH + WINDOW_MARGIN + FONT_SIZE;
    public static int POINT_RADIUS = CELL_LENGTH / 5 * 4;
    public static int POINT_MARGIN = (CELL_LENGTH - POINT_RADIUS)/2;

}

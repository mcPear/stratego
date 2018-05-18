package game;

import game.model.Store;

import javax.swing.*;

public class Window extends JFrame {

    private Canvas canvas;

    public Window(Store store) {
        initUI(store);
    }

    private void initUI(Store store) {
        canvas = new Canvas(store);
        add(canvas);
        setTitle("Stratego");
        setSize(Dimensions.WINDOW_WIDTH, Dimensions.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paintImmediately(){
        canvas.paintImmediately(canvas.getBounds());
    }

}
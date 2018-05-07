package game;

import game.model.Store;

import javax.swing.*;

public class Window extends JFrame {

    public Window(Store store) {

        initUI(store);
    }

    private void initUI(Store store) {
        add(new Canvas(store));
        setTitle("Stratego");
        setSize(Dimensions.WINDOW_WIDTH, Dimensions.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
package game;

import javax.swing.*;

public class Window extends JFrame {

    public Window(Store store) {

        initUI(store);
    }

    private void initUI(Store store) {
        add(new Canvas(store));
        setTitle("Stratego");
        setSize(Dimension.WINDOW_SIZE, Dimension.WINDOW_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
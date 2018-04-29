import game.*;
import game.Window;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Store store = new Store(Color.RED, Color.BLUE);
            Window window = new Window(store);
            Logic logic = new Logic(store);
            window.addMouseListener(new MouseListener(logic, window));
            window.setVisible(true);
        });

    }


}

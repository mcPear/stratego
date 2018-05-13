import game.Logic;
import game.MouseListener;
import game.Window;
import game.model.Store;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Store store = new Store(Color.RED, Color.BLUE);
        Window window = new Window(store);
        Logic logic = new Logic(store);
        window.addMouseListener(new MouseListener(logic, window));
        window.setVisible(true);

        while (!logic.isGameOver()) {
                logic.moveAsComputerA();
                window.repaint();
                logic.moveAsComputerB();
                window.repaint();
        }

    }


}

import game.Logic;
import game.MouseListener;
import game.Window;
import game.model.Store;

import java.awt.*;

public class Main {

    private static boolean PLAYER_VS_MACHINE = false;

    public static void main(String[] args) {

        Store store = new Store(Color.RED, Color.BLUE);
        Window window = new Window(store);
        Logic logic = new Logic(store);
        window.addMouseListener(new MouseListener(logic, window, PLAYER_VS_MACHINE));
        window.setVisible(true);

        if (!PLAYER_VS_MACHINE) {
            while (!logic.isGameOver()) {
                logic.moveAsComputerA();
                window.repaint();
                logic.moveAsComputerB();
                window.repaint();
            }
        }
    }


}

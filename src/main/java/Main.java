import game.Logic;
import game.MouseListener;
import game.Window;
import game.model.Store;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    private static boolean PLAYER_VS_MACHINE = false;

    public static void main(String[] args) {

        Store store = new Store(Color.RED, Color.BLUE);
        Window window = new Window(store);
        Logic logic = new Logic(store);
        window.addMouseListener(new MouseListener(logic, window, PLAYER_VS_MACHINE));
        window.setVisible(true);
        java.util.List<Long> moveTimesA = new ArrayList<>();
        java.util.List<Long> moveTimesB = new ArrayList<>();
        long moveAStartTime = 0;
        long moveBStartTime = 0;

        long startTime = System.currentTimeMillis();
        if (!PLAYER_VS_MACHINE) {
            while (!logic.isGameOver()) {
                moveAStartTime = System.currentTimeMillis();
                logic.moveAsComputerA();
                moveTimesA.add(System.currentTimeMillis() - moveAStartTime);
                window.paintImmediately();
                moveBStartTime = System.currentTimeMillis();
                logic.moveAsComputerB();
                moveTimesB.add(System.currentTimeMillis() - moveBStartTime);
                window.paintImmediately();
            }
        }
        System.out.println("Game time: " + ((System.currentTimeMillis() - startTime) / 1_000) +
                " A:" + getAverageTime(moveTimesA) +
                " B:" + getAverageTime(moveTimesB));
    }

    private static long getAverageTime(java.util.List<Long> moveTimes) {
        long sum = 0;
        for (Long time : moveTimes) {
            sum += time;
        }
        return sum / moveTimes.size();
    }


}

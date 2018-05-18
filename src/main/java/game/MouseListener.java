package game;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {

    private final Logic logic;
    private final Window window;
    private final boolean playerVsMachine;

    public MouseListener(Logic logic, Window window, boolean playerVsMachine) {
        this.logic = logic;
        this.window = window;
        this.playerVsMachine = playerVsMachine;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (playerVsMachine && logic.handleHumanPut(e)) {
            window.repaint();
            logic.moveAsComputerB();
            window.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}

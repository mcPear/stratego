package game;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {

    private final Logic logic;
    private final Window window;

    public MouseListener(Logic logic, Window window) {
        this.logic = logic;
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (logic.handleHumanPut(e)) {
            window.repaint();
            logic.moveAsComputer();
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

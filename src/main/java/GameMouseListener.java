import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouseListener implements MouseListener {

    private final GameLogic gameLogic;
    private final GameWindow gameWindow;

    public GameMouseListener(GameLogic gameLogic, GameWindow gameWindow) {
        this.gameLogic = gameLogic;
        this.gameWindow = gameWindow;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        gameLogic.handleMouseClicked(e);
        gameWindow.repaint();
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

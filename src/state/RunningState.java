package state;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import handlers.GameStateHandler;
import manager.GameStateManager;

public class RunningState implements GameStateHandler{

    private GameStateManager gameStateManager;

    public RunningState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void update(double deltaTime) {
       
    }

    @Override
    public void render(Graphics g) {
        
    }

    @Override
    public void handleInput(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            gameStateManager.setState(GameState.PAUSE);
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            gameStateManager.setState(GameState.GAME_OVER);
        }
    }

}

package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import handlers.GameStateHandler;
import manager.GameStateManager;

public class GameOverState implements GameStateHandler{

    private GameStateManager gameStateManager;

    public GameOverState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void update(double deltaTime) {
        
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("Game Over", 300, 200);
    }

    @Override
    public void handleInput(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            gameStateManager.setState(GameState.RUNNING);
        }
    }

}

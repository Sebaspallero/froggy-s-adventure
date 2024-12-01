package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import handlers.GameStateHandler;
import manager.GameStateManager;

public class PauseState implements GameStateHandler{

    private GameStateManager gameStateManager;

    public PauseState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void update(double deltaTime) {
        System.out.println("Updating PAUSE STATE");
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Pausa", 100, 100);
    }

    @Override
    public void handleInput(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P && gameStateManager.getCurrentState().equals(GameState.PAUSE)) {
            gameStateManager.setState(GameState.RUNNING);
        }
    }

}

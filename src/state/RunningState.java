package state;

import java.awt.Color;
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
        System.out.println("Updating RUNNING STATE");
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(100, 100, 50, 50);
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

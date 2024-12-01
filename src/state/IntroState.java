package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import handlers.GameStateHandler;
import manager.GameStateManager;

public class IntroState implements GameStateHandler{

    private GameStateManager gameStateManager;

    public IntroState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void update(double deltaTime) {
        System.out.println("Updating INTRO STATE");
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Bienvenido al Juego", 100, 100);
        g.drawString("Presiona ENTER para empezar", 100, 150);
    }

    @Override
    public void handleInput(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("CHANGING TO TITLE STATE");
            gameStateManager.setState(GameState.RUNNING);
        }
    }

}

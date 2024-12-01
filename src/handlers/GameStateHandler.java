package handlers;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public interface GameStateHandler {

    void update(double deltaTime);
    void render(Graphics g);
    void handleInput(KeyEvent e);
}

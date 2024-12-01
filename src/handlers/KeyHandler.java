package handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyHandler implements KeyListener{

    private GamePanel gp;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }


    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
       gp.handleKeyInput(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

}

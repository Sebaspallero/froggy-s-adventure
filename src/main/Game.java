package main;
import javax.swing.JFrame;

public class Game extends JFrame{

    public static final int SCREEN_WIDTH = 750;
    public static final int SCREEN_HEIGHT = 500;

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Froggy's Adventure");
        GamePanel gp = new GamePanel();

        frame.add(gp);

        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        /* frame.setIconImage(icon); */

        gp.startGame();
        
    }
}

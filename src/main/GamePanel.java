package main;

import javax.swing.JPanel;

import entities.EntityState;
import entities.enemies.Enemy;
import entities.player.Player;
import handlers.KeyHandler;
import manager.EntityManager;
import manager.GameStateManager;
import state.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable {

    private Thread gameThread;

    private GameStateManager gameStateManager;
    private EntityManager entityManager;
    private Player player;
    private Enemy enemy;

    public GamePanel() {
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        player = new Player(150, 400, 32, 32, EntityState.IDLE);
        enemy = new Enemy(400, 400, 50, 50, 100, EntityState.WALKING);


        gameStateManager = new GameStateManager();
        entityManager = new EntityManager(player);

        entityManager.addEntity(enemy);
        this.addKeyListener(new KeyHandler(this));
    }

    @Override
    public void run() {
        final int targetFPS = 60;
        final long targetTime = 1_000_000_000 / targetFPS; // Tiempo por frame en nanosegundos
        long lastTime = System.nanoTime();

        while (true) {
            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
            lastTime = currentTime;

            gameStateManager.update(deltaTime); // Lógica del juego

            if (gameStateManager.getCurrentState() == GameState.RUNNING) {
                entityManager.update(deltaTime); // Actualizar entidades
            }

            repaint(); // Renderizado

            long elapsedTime = System.nanoTime() - currentTime;
            long sleepTime = targetTime - elapsedTime;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1_000_000); // Convertir a milisegundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startGame() {
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void handleKeyInput(KeyEvent e) {
        gameStateManager.handleInput(e);
        player.handleKeyPressed(e);
    }

    public void handleKeyReleased(KeyEvent e){
        player.handleKeyReleased(e);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameStateManager.render(g);
        if (gameStateManager.getCurrentState() == GameState.RUNNING) {
            entityManager.render(g); // Dibujar entidades
        }
    }
}

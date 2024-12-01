package entities.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import entities.Entity;
import entities.EntityState;

public class Player extends Entity {

    private final Set<Integer> pressedKeys = new HashSet<>(); // Para almacenar las teclas presionadas

    public Player(int x, int y, int width, int height, EntityState currentState) {
        super(x, y, width, height, currentState);
    }

    @Override
    public void update(double deltaTime) {
        // Movimiento horizontal
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            velocityX = -250; // Velocidad hacia la izquierda
            x += velocityX * deltaTime;
        } else if (pressedKeys.contains(KeyEvent.VK_D)) {
            velocityX = 250; // Velocidad hacia la derecha
            x += velocityX * deltaTime;
        } else if (currentState != EntityState.JUMPING) {
            velocityX = 0; // Detener movimiento horizontal si no se está saltando
        }

        // Lógica de salto
        if (currentState == EntityState.JUMPING) {
            y += velocityY * deltaTime; // Movimiento vertical
            velocityY += 9.8 * 100 * deltaTime; // Aplicar gravedad

            // Verificar si aterrizó
            if (y >= 400) { // Suelo
                y = 400;
                velocityY = 0;
                if (pressedKeys.contains(KeyEvent.VK_A) || pressedKeys.contains(KeyEvent.VK_D)) {
                    currentState = EntityState.WALKING; // Volver a caminar si se presionan teclas de movimiento
                } else {
                    currentState = EntityState.IDLE; // Pasar a IDLE si no hay movimiento
                }
            }
        } else if (currentState != EntityState.WALKING) {
            // Si no está saltando, ajustar estado según las teclas presionadas
            currentState = velocityX != 0 ? EntityState.WALKING : EntityState.IDLE;
        }
    }
    
    @Override
    //Manejar el tiempo de daño
    public void takeDamage(int amount) {
        
    }


    @Override
    public void render(Graphics g) {
        switch (currentState) {
            case IDLE:
                g.setColor(Color.BLUE);
                break;
            case WALKING:
                g.setColor(Color.GREEN);
                break;
            case JUMPING:
                g.setColor(Color.MAGENTA);
                break;
            case DEAD:
                g.setColor(Color.RED);
                break;
            default:
                System.out.println("Estado no válido");
                break;
        }

        // Dibujar el jugador como un rectángulo
        g.fillRect(x, y, width, height);
    }

    public void handleKeyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    
        // Detectar salto
        if (e.getKeyCode() == KeyEvent.VK_W && currentState != EntityState.JUMPING) {
            velocityY = -350; // Velocidad inicial de salto
            currentState = EntityState.JUMPING;
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    
        // Actualizar estado si no hay teclas de movimiento activas
        if (!pressedKeys.contains(KeyEvent.VK_A) && !pressedKeys.contains(KeyEvent.VK_D) && currentState != EntityState.JUMPING) {
            currentState = EntityState.IDLE;
        }
    }

    // Getter y Setter
    public EntityState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(EntityState currentState) {
        this.currentState = currentState;
    }

}

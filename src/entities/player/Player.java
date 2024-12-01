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
    private int health;
    private double invulnerabilityTimer;

    public Player(int x, int y, int width, int height, EntityState currentState) {
        super(x, y, width, height, currentState);
        this.health = 100;
        this.invulnerabilityTimer = 0;
    }

    @Override
    public void update(double deltaTime) {

        // Reducir temporizador de invulnerabilidad
        if (invulnerabilityTimer > 0) {
            invulnerabilityTimer -= deltaTime;
            if (invulnerabilityTimer <= 0) {
                invulnerabilityTimer = 0; // Finalizar invulnerabilidad
            }
        }

        // Reducir temporizador para estado HIT
        if (currentState == EntityState.HIT) {
            hitTimer -= deltaTime;
            if (hitTimer <= 0) {
                currentState = EntityState.IDLE; // Volver a estado normal después del HIT
            }
        }

        // Manejar lógica de movimiento y salto
        handleMovement(deltaTime);

        // Verificar si el jugador murió
        if (health <= 0 && currentState != EntityState.DEAD) {
            currentState = EntityState.DEAD;
            active = false; // Marcar como inactivo
            System.out.println("Player is DEAD!");
        }
    }

    private void handleMovement(double deltaTime) {
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
        } else if (currentState != EntityState.HIT && currentState != EntityState.DEAD) {
            // Si no está saltando ni en HIT, ajustar estado según las teclas presionadas
            currentState = velocityX != 0 ? EntityState.WALKING : EntityState.IDLE;
        }
    }

    @Override
    public void takeDamage(int amount) {
        if (currentState == EntityState.HIT || currentState == EntityState.DEAD || invulnerabilityTimer > 0) return;

        health -= amount; // Reducir salud
        currentState = EntityState.HIT; // Cambiar estado a HIT
        hitTimer = 1.0; // Duración del estado HIT (3 segundo)
        invulnerabilityTimer = 2.0; // Duración del estado de iinvulneravilidad (3 segundo)
        System.out.println("Player HIT! Health: " + health);
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
            case HIT:
                g.setColor(Color.ORANGE);
            default:
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
        if (!pressedKeys.contains(KeyEvent.VK_A) && !pressedKeys.contains(KeyEvent.VK_D)
                && currentState != EntityState.JUMPING) {
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

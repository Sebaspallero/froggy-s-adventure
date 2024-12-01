package entities.player;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import entities.Entity;
import entities.EntityState;
import manager.AnimationManager;

public class Player extends Entity {

    private final Set<Integer> pressedKeys = new HashSet<>(); // Para almacenar las teclas presionadas
    private int health;
    private double invulnerabilityTimer;
    private AnimationManager walkingAnimation;
    private AnimationManager idleAnimation;
    private AnimationManager currentAnimation;

    public Player(int x, int y, int width, int height, EntityState currentState) {
        super(x, y, width, height, currentState);
        this.health = 100;
        this.invulnerabilityTimer = 0;

        BufferedImage walkingSheet = loadSpriteSheet("/resources/player/player_run.png");
        BufferedImage idleSheet = loadSpriteSheet("/resources/player/player_idle.png");
          
        // Crear animaciones con los sprites cargados
        walkingAnimation = new AnimationManager(walkingSheet, 32, 32, 12, 50, 0);
        idleAnimation = new AnimationManager(idleSheet, 32, 32, 11, 50, 0);
        currentAnimation = idleAnimation;
    }   

    @Override
    public void update(double deltaTime) {

        // Actualizar la animación según el estado
        switch (currentState) {
            case WALKING:
                currentAnimation = walkingAnimation;
                break;
            case IDLE:
                currentAnimation = idleAnimation;
                break;
            default:
                currentAnimation = idleAnimation;
                break;
        }

        // Actualizar la animación activa
        currentAnimation.update();

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
        currentAnimation.draw(g, x, y, width, height);
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

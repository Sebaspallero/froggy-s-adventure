package entities.enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Entity;
import entities.EntityState;
import manager.AnimationManager;

public class Enemy extends Entity {

    private int health; // Salud del enemigo
    private int leftPatrol;
    private int rightPatrol;
    private boolean moovingRight = true;
    private int initialX;

    private AnimationManager walkingAnimation;
    private AnimationManager hitAnimation;
    private AnimationManager currentAnimation;

    private long startTime;
    private long leftTime, rightTime;

    public Enemy(int x, int y, int width, int height, EntityState currentState) {
        super(x, y, width, height, currentState);
        this.health = 500;
        this.initialX = x;
        this.rightPatrol = initialX + 150;
        this.leftPatrol =  initialX - 150;
        this.velocityX = 100; // Velocidad inicial
        this.startTime = System.currentTimeMillis();

        BufferedImage walkingSheet = loadSpriteSheet("/resources/enemies/pig/pig_run.png");
        BufferedImage hitSheet = loadSpriteSheet("/resources/enemies/pig/pig_hit.png");

        // Crear animaciones con los sprites cargados
        walkingAnimation = new AnimationManager(walkingSheet, width, height, 12, 50, 0);
        hitAnimation = new AnimationManager(hitSheet, width, height, 5, 100, 0);
        currentAnimation = walkingAnimation;
    }

    @Override
    public void update(double deltaTime) {
        switch (currentState) {
            case IDLE:
                velocityX = 0;
                break;
                case WALKING:
                currentAnimation = walkingAnimation;
                if (moovingRight) {
                    // Mover hacia la derecha con deltaTime
                    x += velocityX * deltaTime + 1;
                    currentAnimation.setFlip(true); // El flip debe ser false al moverse a la derecha
            
                    // Verificar si se ha alcanzado el límite derecho
                    if (x + width >= rightPatrol) {
                        recordRightTime();
                        x = rightPatrol - width; // Asegurarse de que no se pase del límite
                        moovingRight = false; // Cambiar la dirección
                    }
                } else {
                    // Mover hacia la izquierda con deltaTime
                    x -= velocityX * deltaTime; // Aplicar deltaTime también al movimiento hacia la izquierda
                    currentAnimation.setFlip(false); // El flip debe ser true al moverse a la izquierda
                   
            
                    // Verificar si se ha alcanzado el límite izquierdo
                    if (x <= leftPatrol) {
                        recordLeftTime();
                        x = leftPatrol; // Asegurarse de que no se pase del límite
                        moovingRight = true; // Cambiar la dirección
                    }
                }
                break;
            case ATTACKING:
                // Placeholder: lógica de ataque al jugador
                System.out.println("Enemy is attacking!");
                break;
            case HIT:
                currentAnimation = hitAnimation;
                hitTimer -= deltaTime;
                System.out.println("Enemy was Hit!, current health " + health);
                if (hitTimer <= 0) {
                    currentState = EntityState.WALKING; // Vuelve al estado por defecto
                }
                break;
            case DEAD:
                System.out.println("Enemy is Dead!");
                active = false;
                break;
            default:
                System.out.println("State not valid " + currentState);
                break;
        }

        // Actualizar la animación activa
        currentAnimation.update();
    }

    private void recordRightTime() {
        long currentTime = System.currentTimeMillis();
        rightTime = currentTime - startTime;
        System.out.println("Tiempo hacia la derecha: " + rightTime + " ms");
        startTime = currentTime;
    }

    private void recordLeftTime() {
        long currentTime = System.currentTimeMillis();
        leftTime = currentTime - startTime;
        System.out.println("Tiempo hacia la izquierda: " + leftTime + " ms");
        startTime = currentTime;
    }

    @Override
    public void takeDamage(int damage) {
        if (currentState == EntityState.HIT || currentState == EntityState.DEAD)
            return; // Evitar aplicar daño repetidamente
        health -= damage;
        if (health <= 0) {
            currentState = EntityState.DEAD;
        } else {
            currentState = EntityState.HIT;
            hitTimer = 0.5; // Duración del estado HIT
        }
    }

    @Override
    public void render(Graphics g) {
        currentAnimation.draw(g, x, y, width, height);
    }

    // Getters - Setters
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public EntityState getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(EntityState newState) {
        this.currentState = newState;
    }

}

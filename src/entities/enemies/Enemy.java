package entities.enemies;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import entities.EntityState;

public class Enemy extends Entity{

    private int health; //Salud del enemigo
    private double patrolRange; //Rango de patrullaje
    private int initialX; //Posicion inicial para calcular el rango

    public Enemy(int x, int y, int width, int height, double patrolRange, EntityState currentState){
        super(x, y, width, height, currentState);
        this.health = 100;
        this.patrolRange = patrolRange;
        this.initialX = x;
        this.velocityX = 100; // Velocidad inicial
    }

    @Override
    public void update(double deltaTime) {
        switch (currentState) {
            case IDLE:
                velocityX = 0;
                break;
            case WALKING:
             // Patrullar dentro del rango
                x += velocityX * deltaTime;
                if (x > initialX + patrolRange || x < initialX - patrolRange) {
                    velocityX *= -1; //Cambiar de dirección al llegar al limite
                }
                break;
            case ATTACKING:
                // Placeholder: lógica de ataque al jugador
                System.out.println("Enemy is attacking!");
                   break;
            case HIT:
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
    }

    @Override
    public void takeDamage(int damage) {
        if (currentState == EntityState.HIT || currentState == EntityState.DEAD) return; // Evitar aplicar daño repetidamente
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
        switch (currentState) {
            case IDLE:
                g.setColor(Color.ORANGE);
                break;
            case WALKING:
                g.setColor(Color.ORANGE);
                break;
            case ATTACKING:
                g.setColor(Color.ORANGE);
                break;
            default:
                g.setColor(Color.ORANGE);
                break;
        }

        g.fillRect(x, y, width, height);
    }

    //Getters - Setters
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public EntityState getCurrentState(){
        return this.currentState;
    }

    public void setCurrentState(EntityState newState){
        this.currentState = newState;
    }

}

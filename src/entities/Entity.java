package entities;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int velocityX;
    protected int velocityY;
    protected boolean active;
    protected double hitTimer;
    protected EntityState currentState;

    public Entity(int x, int y, int width, int height, EntityState currentState) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.currentState = currentState;
        this.velocityX = 0;
        this.velocityY = 0;
        this.active = true;
        this.hitTimer = 0;
    }

    // Métodos abstractos para que las clases derivadas los implementen
    public abstract void update(double deltaTime);
    public abstract void render(Graphics g);
    public abstract void takeDamage(int damage);

    // Métodos comunes

    // Obtener la hitbox para detectar colisiones
    public Rectangle getHitbox(){
        return new Rectangle(x, y, width, height);
    }


    //Getters - Setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getHitTimer() {
        return hitTimer;
    }

    public void setHitTimer(double hitTimer) {
        this.hitTimer = hitTimer;
    }


    //Getters - Setters
    

    
}

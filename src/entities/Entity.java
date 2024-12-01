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

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityX = 0;
        this.velocityY = 0;
        this.active = true;
    }

     // Métodos abstractos para que las clases derivadas los implementen
    public abstract void update(double deltaTime);
    public abstract void render(Graphics g);

    // Métodos comunes

    // Obtener el rectángulo delimitador para detectar colisiones
    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

     // Verificar colisiones con otra entidad
    public boolean intersects(Entity other){
        return this.getBounds().intersects(other.getBounds());
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

    public int getHeigth() {
        return height;
    }

    public void setHeigth(int height) {
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
}

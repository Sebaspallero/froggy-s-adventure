package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Entity{

    private EntityState currentState;

    public Player(int x, int y, int width, int height){
        super(x, y, width, height);
        this.currentState = EntityState.IDLE; //Estado incial
    }

    @Override
    public void update(double deltaTime) {
        switch (currentState) {
            case IDLE:
                velocityX = 0;
                break;
            case WALKING:
             // Moverse en función de la velocidad actual
                x += velocityX * deltaTime;
                break;
            case JUMPING:
             // Aplicar lógica de salto
                y += velocityY * deltaTime; // Movimiento vertical
                velocityY += 9.8 * deltaTime;
                break;
            case DEAD:
                active = false;
                break;
            default:
                System.out.println("Entity state not valid");
                break;
        }

        // Asegurarse de que el jugador no se caiga de la pantalla
        if (y > 500) {
            y = 500; //Suelo
            velocityY = 0;
            currentState = EntityState.IDLE; // Cambiar estado al aterrizar
        }
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
                System.out.println("Entity state not valid");
                break;
        }

         // Dibujar el jugador como un rectángulo simple (placeholder)
         g.fillRect(x, y, width, height);
    }

    
    // Métodos específicos del jugador
    public void handleInput(boolean left, boolean right, boolean jump, boolean attack) {
        if (jump && currentState != EntityState.JUMPING) {
            velocityY = -300; // Velocidad inicial de salto
            currentState = EntityState.JUMPING;
        } else if (left) {
            velocityX = -100; // Velocidad hacia la izquierda
            currentState = EntityState.WALKING;
        } else if (right) {
            velocityX = 100; // Velocidad hacia la derecha
            currentState = EntityState.WALKING;
        } else if (attack) {
            currentState = EntityState.ATTACKING;
        } else {
            currentState = EntityState.IDLE;
        }
    }


    //Getter - Setter
    public EntityState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(EntityState currentState) {
        this.currentState = currentState;
    }

    

}

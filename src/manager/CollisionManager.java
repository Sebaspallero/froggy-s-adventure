package manager;

import java.awt.Rectangle;

import entities.Entity;
import entities.enemies.Enemy;
import entities.player.Player;

public class CollisionManager {

    // Verificar colisiones entre entidades
    public static boolean isColliding(Entity a, Entity b){
        return a.getHitbox().intersects(b.getHitbox());
    }

    //Verificamos colision desde arriba
    public static boolean isCollidingFromAbove(Entity a, Entity b) {
        Rectangle aBox = a.getHitbox();
        Rectangle bBox = b.getHitbox();

        // Verificar si la parte inferior de 'a' está tocando la parte superior de 'b'
        return aBox.intersects(bBox) && 
               aBox.y + aBox.height <= bBox.y + 10 && // Verifica que esté justo arriba
               ((Entity) a).getVelocityY() > 0;      // Verifica que esté cayendo
    }

    //Que hacemos cuando se colisiona
    public static void handleCollision(Player player, Entity entity){
        if (!player.isActive()) return;
        if (entity instanceof Enemy) {
            Enemy enemy = (Enemy) entity;
            if(!enemy.isActive()) return;
            if (isCollidingFromAbove(player, entity)) {
                enemy.takeDamage(50);
                player.setVelocityY(-200);
                player.setY(entity.getHitbox().y - player.getHitbox().height); // Posicionamos al jugador justo encima del enemigo
            }else{
                System.out.println("colision normal con enemigo");
                player.takeDamage(50);
            }
        }
    }
}

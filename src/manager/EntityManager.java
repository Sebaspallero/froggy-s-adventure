package manager;

import entities.Entity;
import entities.Player;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityManager {

    private List <Entity> entities;
    private Player player;

    public EntityManager(Player player){
        this.entities = new ArrayList<>();
        this.player = player;
        addEntity(player); 
    }

     // Actualizar todas las entidades
     public void update(double deltaTime){
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity.isActive()) {
                entity.update(deltaTime);
            }else{
                // Eliminar entidades inactivas
                iterator.remove();
            }
        }
     }

    // Renderizar todas las entidades
     public void render(Graphics g){
        for (Entity entity : entities) {
            entity.render(g);
        }
    }

    //Agregar una nueva entidad
    public void addEntity(Entity entity){
        entities.add(entity);
    }

    // Obtener una lista de todas las entidades activas
    public List<Entity> getEntities() {
        return entities;
    }

    // Obtener la referencia al jugador
    public Player getPlayer() {
        return player;
    }
}

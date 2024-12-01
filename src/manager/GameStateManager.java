package manager;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import handlers.GameStateHandler;
import state.GameOverState;
import state.GameState;
import state.IntroState;
import state.PauseState;
import state.RunningState;

public class GameStateManager {

    private HashMap<GameState, GameStateHandler> stateHandler;
    private GameStateHandler currentGameStateHandler;

    public GameStateManager(){
        stateHandler = new HashMap<>();

        //Registrar Estados
        stateHandler.put(GameState.INTRO, new IntroState(this));
        stateHandler.put(GameState.RUNNING, new RunningState(this));
        stateHandler.put(GameState.PAUSE, new PauseState(this));
        stateHandler.put(GameState.GAME_OVER, new GameOverState(this));

        setState(GameState.INTRO);

    }

    public void setState(GameState newState){
        currentGameStateHandler = stateHandler.get(newState);
        if (currentGameStateHandler == null) {
            throw new IllegalStateException("State not registered: " + newState);
        }
        System.out.println("Cambiando al estado: " + newState);
    }

    public GameState getCurrentState() {
        // Determinar el estado actual según el handler
        for (GameState state : stateHandler.keySet()) {
            if (stateHandler.get(state) == currentGameStateHandler) {
                return state;
            }
        }
        return null; // Si no se encuentra el estado actual
    }

    public void update(double deltaTime){
        if (currentGameStateHandler != null) {
            currentGameStateHandler.update(deltaTime);
        }
    }

    public void render(Graphics g) {
        if (currentGameStateHandler != null) {
            currentGameStateHandler.render(g);
        }
    }

    public void handleInput(KeyEvent e) {
        if (currentGameStateHandler != null) {
            currentGameStateHandler.handleInput(e);
        }
    }
}

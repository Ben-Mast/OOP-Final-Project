package game.states;

import game.GameWorld;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.Set;

abstract public class GameState {
    protected final GameWorld gameWorld;

    public GameState(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public abstract void update(double deltaTime, Set<KeyCode> keyCodes);
    public abstract void render(GraphicsContext graphicContext);
}


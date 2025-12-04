package game.state;

import game.GameWorld;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class RunningGameState extends GameState {

    boolean escWasDown = true;

    public RunningGameState(GameWorld gameWorld) {
        super(gameWorld);
    }

    @Override
    public void update(double deltaTime, Set<KeyCode> keysCodes) {

        boolean escDown = keysCodes.contains(KeyCode.ESCAPE);

        if (escDown && !escWasDown) {
            gameWorld.updateGameState(State.PAUSED);
        }

        escWasDown = escDown;

        gameWorld.updateWorld(deltaTime, keysCodes);
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
        gameWorld.renderWorld(graphicsContext);
    }
}

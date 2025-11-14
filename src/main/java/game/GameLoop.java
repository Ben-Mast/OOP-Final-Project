package game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class GameLoop extends AnimationTimer {
    private double lastFrameTime = 0;
    private GraphicsContext graphicsContext;
    private Set<KeyCode> keys;
    private GameWorld gameWorld;

    public GameLoop(GraphicsContext graphicsContext, Set<KeyCode> keys,  GameWorld gameWorld) {
        this.graphicsContext = graphicsContext;
        this.keys = keys;
        this.gameWorld = gameWorld;
    }

    @Override
    public void handle(long now) {
        if (lastFrameTime == 0) {
            lastFrameTime = now;
        }
        double deltaTime = (now - lastFrameTime) / 1_000_000_000.0;
        lastFrameTime = now;

        gameWorld.updatePositions(deltaTime, keys);
        gameWorld.render(graphicsContext);
    }
}

package game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class GameLoop extends AnimationTimer {
    private static final double FIXED_TIMESTEP = 1.0 / 60.0;
    private double lastTime = 0;
    private final GraphicsContext graphicsContext;
    private final Set<KeyCode> keys;
    private final GameWorld gameWorld;
    private double accumulator = 0;

    public GameLoop(GraphicsContext graphicsContext, Set<KeyCode> keys,  GameWorld gameWorld) {
        this.graphicsContext = graphicsContext;
        this.keys = keys;
        this.gameWorld = gameWorld;
    }

    @Override
    public void handle(long now) {
        if (lastTime == 0) {
            lastTime = now;
            return;
        }

        double frameTime = (now - lastTime) / 1_000_000_000.0;
        lastTime = now;

        frameTime = Math.min(frameTime, 0.1);

        accumulator += frameTime;

        while (accumulator >= FIXED_TIMESTEP) {
            gameWorld.update(FIXED_TIMESTEP, keys);
            gameWorld.resolveCollisions();
            accumulator -= FIXED_TIMESTEP;
        }

        gameWorld.render(graphicsContext);
    }
}

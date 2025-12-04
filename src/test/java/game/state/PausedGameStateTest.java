package game.state;

import game.GameWorld;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class PausedGameStateTest {

    @Test
    void updateAndRenderDoNotThrow() {
        GameWorld world = new GameWorld();
        PausedGameState state = new PausedGameState(world);

        state.update(0.016, Collections.emptySet());

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        state.render(gc);
    }
}


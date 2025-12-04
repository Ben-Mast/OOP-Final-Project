package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Set;

public class GameLoopTest {

    @Test
    void constructorAndHandleDoNotThrow() {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Set<KeyCode> keys = EnumSet.noneOf(KeyCode.class);
        GameWorld world = new GameWorld();

        GameLoop loop = new GameLoop(graphicsContext, keys, world);
        loop.handle(0L);
        loop.handle(16_000_000L);
    }
}


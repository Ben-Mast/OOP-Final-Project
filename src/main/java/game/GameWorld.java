package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class GameWorld {
    private final List<GameObject> gameObjects = new ArrayList<GameObject>();
    private final Spaceship player;

    public GameWorld() {
        player = new Spaceship(0.0,0.0, new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("spaceship.png"))));
    }

    public void updatePositions (double deltaTime, Set<KeyCode> keys) {

        player.handleInput(
                keys.contains(KeyCode.W),
                keys.contains(KeyCode.S),
                keys.contains(KeyCode.A),
                keys.contains(KeyCode.D)
        );
        player.updatePosition(deltaTime);

        for (GameObject gameObject : gameObjects) {
            gameObject.updatePosition(deltaTime);
        }
    }

    public void render(GraphicsContext graphicsContext) {
        graphicsContext.clearRect(0, 0, GameApp.WINDOW_WIDTH, GameApp.WINDOW_HEIGHT);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, GameApp.WINDOW_WIDTH, GameApp.WINDOW_HEIGHT);

        player.render(graphicsContext);

        for (GameObject gameObject : gameObjects) {
            gameObject.render(graphicsContext);
        }
    }
}

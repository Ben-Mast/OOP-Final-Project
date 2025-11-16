package game;

import game.object.GameObject;
import game.object.PlayerSpaceship;
import game.object.Projectile;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class GameWorld {
    private static final Point2D DEFAULT_PLAYER_SPAWN = new Point2D(375, 550);

    private final List<GameObject> gameObjects = new ArrayList<>();
    private final List<GameObject> objectsToAdd = new ArrayList<>();
    private final List<GameObject> objectsToRemove = new ArrayList<>();


    public GameWorld() {
        PlayerSpaceship player = new PlayerSpaceship(DEFAULT_PLAYER_SPAWN, new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("spaceship.png"))), this::createProjectile);
        gameObjects.add(player);
    }

    public void update(double deltaTime, Set<KeyCode> keys) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime, keys);
        }

        gameObjects.removeAll(objectsToRemove);
        gameObjects.addAll(objectsToAdd);

        objectsToRemove.clear();
        objectsToAdd.clear();
    }

    public void render(GraphicsContext graphicsContext) {
        graphicsContext.clearRect(0, 0, GameApp.WINDOW_WIDTH, GameApp.WINDOW_HEIGHT);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, GameApp.WINDOW_WIDTH, GameApp.WINDOW_HEIGHT);

        for (GameObject gameObject : gameObjects) {
            gameObject.render(graphicsContext);
        }
    }

    private void createProjectile(GameObject projectile) {
        objectsToAdd.add(projectile);
    }
}

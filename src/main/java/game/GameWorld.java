package game;

import game.object.GameObject;
import game.object.GameObjectFactory;
import game.object.PlayerSpaceship;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.*;

public class GameWorld {
    private static final Point2D DEFAULT_PLAYER_SPAWN = new Point2D(375, 550);
    private static final GameObjectFactory gameObjectFactory = new GameObjectFactory();

    private final List<GameObject> gameObjects = new ArrayList<>();
    private final List<GameObject> objectsToAdd = new ArrayList<>();
    private final List<GameObject> objectsToRemove = new ArrayList<>();

    private final double timeBetweenWaves = 2;
    private double waveCooldownTimer = 0;
    private int waveSize = 2;


    public GameWorld() {
        PlayerSpaceship player = new PlayerSpaceship(DEFAULT_PLAYER_SPAWN, new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("spaceship.png"))), this::createGameObject);
        createGameObject(player);
    }

    public void update(double deltaTime, Set<KeyCode> keys) {
        attemptToSpawnWave(deltaTime);
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime, keys);
        }
    }

    public void processAddRemoveObjects() {
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

    private void createGameObject(GameObject gameObject) {
        objectsToAdd.add(gameObject);
        gameObject.setGameObjectDestroyer(this::removeGameObject);
    }

    private void createGameObjects(List<GameObject> gameObjects) {
        for (GameObject gameObject : gameObjects) {
            createGameObject(gameObject);
        }
    }

    private void removeGameObject(GameObject gameObject) {
        objectsToRemove.add(gameObject);
    }

    private void attemptToSpawnWave(double deltaTime) {
        if (waveCooldownTimer > 0) {
            waveCooldownTimer-= deltaTime;
        } else {
            waveCooldownTimer =  timeBetweenWaves;
            List<GameObject> asteroids = gameObjectFactory.createNRandomlySpawnedAsteroids(waveSize);
            createGameObjects(asteroids);
        }
    }

    public void resolveCollisions() {
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject gameObject1 = gameObjects.get(i);
                GameObject gameObject2 = gameObjects.get(j);
                if (isColliding(gameObject1, gameObject2)) {
                    gameObject1.onCollision(gameObject2);
                    gameObject2.onCollision(gameObject1);
                }
            }
        }
    }

    private boolean isColliding(GameObject gameObject1, GameObject gameObject2) {
        return gameObject1.getPosition().distance(gameObject2.getPosition()) < gameObject1.getHitRadius() + gameObject2.getHitRadius();
    }
}

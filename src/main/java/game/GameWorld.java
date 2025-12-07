package game;

import game.object.GameObject;
import game.object.GameObjectFactory;
import game.object.ship.PlayerShip;
import game.object.ship.Ship;
import game.state.*;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.*;

public class GameWorld {
    private static final Point2D DEFAULT_PLAYER_SPAWN = new Point2D(375, 550);

    private GameState gameState;

    private final GameState runningGameState = new RunningGameState(this);
    private final GameState pausedGameState = new PausedGameState(this);
    private final GameState gameOverGameState = new GameOverState(this);

    private final List<GameObject> gameObjects = new ArrayList<>();
    private final List<GameObject> objectsToAdd = new ArrayList<>();
    private final List<GameObject> objectsToRemove = new ArrayList<>();

    private final double timeBetweenMeteorWaves = 2;
    private double meteorWaveCooldownTimer = 0;
    private int meteorWaveSize = 1;

    private final double timeBetweenAIShipWaves = 8;
    private double shipWaveCooldownTimer = 0;
    private int shipWaveSize = 1;

    private double timeAlive;


    public GameWorld() {
        reset();
    }

    public void reset() {
        gameObjects.clear();
        Ship player = new PlayerShip(DEFAULT_PLAYER_SPAWN, this::createGameObject);
        createGameObject(player);
        processAddRemoveObjects();
        timeAlive = 0;
        gameState = runningGameState;
    }

    public void updateGameState(State newState) {
        gameState = switch (newState) {
            case RUNNING -> runningGameState;
            case PAUSED -> pausedGameState;
            case GAMEOVER -> gameOverGameState;
        };
    }

    public boolean isGameOver() {
        return gameObjects.stream().noneMatch(gameObject -> gameObject instanceof PlayerShip);
    }

    public void update(double deltaTime, Set<KeyCode> keyCodes) {
        gameState.update(deltaTime, keyCodes);
    }

    public void render(GraphicsContext graphicContext) {
        gameState.render(graphicContext);
    }

    public void updateWorld(double deltaTime, Set<KeyCode> keys) {
        processAddRemoveObjects();

        if (isGameOver()) {
            updateGameState(State.GAMEOVER);
        }
        timeAlive += deltaTime;
        attemptToSpawnMeteorWave(deltaTime);
        attemptToSpawnAIShipWave(deltaTime);
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime, keys);
        }

        processAddRemoveObjects();
    }

    public void processAddRemoveObjects() {
        gameObjects.removeAll(objectsToRemove);
        gameObjects.addAll(objectsToAdd);
        objectsToRemove.clear();
        objectsToAdd.clear();
    }

    private void renderTimeAlive(GraphicsContext graphicsContext) {
        double x = 10;
        double y = 30;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font(20));
        graphicsContext.fillText("Time Alive: " + String.format("%.1f", timeAlive), x, y);
    }

    public void renderWorld(GraphicsContext graphicsContext) {
        graphicsContext.clearRect(0, 0, GameApp.WINDOW_WIDTH, GameApp.WINDOW_HEIGHT);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, GameApp.WINDOW_WIDTH, GameApp.WINDOW_HEIGHT);

        renderTimeAlive(graphicsContext);

        for (GameObject gameObject : gameObjects) {
            gameObject.render(graphicsContext);
        }
    }

    protected void createGameObject(GameObject gameObject) {
        objectsToAdd.add(gameObject);
        gameObject.setGameObjectDestroyer(this::removeGameObject);
    }

    private void createGameObjects(List<GameObject> gameObjects) {
        for (GameObject gameObject : gameObjects) {
            createGameObject(gameObject);
        }
    }

    protected void removeGameObject(GameObject gameObject) {
        objectsToRemove.add(gameObject);
    }

    private void attemptToSpawnMeteorWave(double deltaTime) {
        if (meteorWaveCooldownTimer > 0) {
            meteorWaveCooldownTimer-= deltaTime;
        } else {
            meteorWaveCooldownTimer = timeBetweenMeteorWaves;
            List<GameObject> asteroids = GameObjectFactory.createNRandomlySpawnedAsteroids(meteorWaveSize, this::createGameObject);
            createGameObjects(asteroids);
        }
    }

    private void attemptToSpawnAIShipWave(double deltaTime) {
        if (shipWaveCooldownTimer > 0) {
            shipWaveCooldownTimer-= deltaTime;
        }  else {
            shipWaveCooldownTimer = timeBetweenAIShipWaves;
            List<GameObject> ships = GameObjectFactory.createNRandomlySpawnedAIShips(shipWaveSize, this::createGameObject);
            createGameObjects(ships);
        }
    }

    public void resolveCollisions() {
        processAddRemoveObjects();
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
        processAddRemoveObjects();
    }

    private boolean isColliding(GameObject gameObject1, GameObject gameObject2) {
        return gameObject1.getPosition().distance(gameObject2.getPosition()) < gameObject1.getHitRadius() + gameObject2.getHitRadius();
    }
}

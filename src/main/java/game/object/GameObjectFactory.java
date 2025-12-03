package game.object;

import game.GameApp;
import game.object.asteroid.*;
import game.object.projectile.EnemyProjectile;
import game.object.projectile.FriendlyProjectile;
import game.object.ship.AIShip;
import game.strategy.AIMovementStrategy;
import game.strategy.DefaultAIMovement;
import game.strategy.ZigZagAIMovement;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

public class GameObjectFactory {
    private static final Random RANDOM = new Random();
    private static final List<Function<AIShip, AIMovementStrategy>> MOVEMENT_STRATEGIES = List.of(
            DefaultAIMovement::new,
            ZigZagAIMovement::new
    );

    public static GameObject createRandomlySpawnedAsteroid(Consumer<GameObject> gameObjectCreator) {
        Point2D spawnPosition = getRandomTopScreenSpawnPosition();
        Point2D spawnVelocity = getRandomTopScreenAsteroidSpawnVelocity();
        AsteroidSize size = chooseRandomAsteroidSize();
        return createPreciselySpawnedAsteroid(size, spawnPosition,spawnVelocity,gameObjectCreator);
    }

    private static AsteroidSize chooseRandomAsteroidSize() {
        final int SMALL_CHANCE = 60;
        final int MEDIUM_CHANCE = 30;

        int roll = RANDOM.nextInt(100);

        if (roll < SMALL_CHANCE) return AsteroidSize.SMALL;
        else if (roll < SMALL_CHANCE + MEDIUM_CHANCE) return AsteroidSize.MEDIUM;
        else return AsteroidSize.BIG;
    }

    public static List<GameObject> createNRandomlySpawnedAsteroids(int numberOfAsteroids, Consumer<GameObject> gameObjectCreator) {
        List<GameObject> asteroids  = new ArrayList<>();
        for  (int i = 0; i < numberOfAsteroids; i++) {
            asteroids.add(createRandomlySpawnedAsteroid(gameObjectCreator));
        }
        return asteroids;
    }

    public static GameObject createPreciselySpawnedAsteroid(AsteroidSize size, Point2D spawnPosition, Point2D spawnVelocity, Consumer<GameObject> gameObjectCreator) {
        return switch (size) {
            case BIG -> new BigAsteroid(spawnPosition, spawnVelocity,gameObjectCreator);
            case MEDIUM -> new MediumAsteroid(spawnPosition, spawnVelocity,gameObjectCreator);
            case SMALL -> new SmallAsteroid(spawnPosition, spawnVelocity,gameObjectCreator);
        };
    }

    public static List<GameObject> createNPreciselySpawnedAsteroids(AsteroidSize size, int numberOfAsteroids, Point2D spawnPosition, Consumer<GameObject> gameObjectCreator) {
        List<GameObject> asteroids  = new ArrayList<>();
        for  (int i = 0; i < numberOfAsteroids; i++) {
            asteroids.add(createPreciselySpawnedAsteroid(size, spawnPosition, getRandomAsteroidSpawnVelocity(), gameObjectCreator));
        }
        return asteroids;
    }

    private static Point2D getRandomAsteroidSpawnVelocity() {
        double randomXVelocity = RANDOM.nextDouble() - .5;
        double randomYVelocity = RANDOM.nextDouble() - .5;
        return new Point2D(randomXVelocity, randomYVelocity).normalize();
    }

    private static Point2D getRandomTopScreenAsteroidSpawnVelocity() {
        double randomXVelocity = RANDOM.nextDouble() - .5;
        double randomYVelocity = Math.abs(RANDOM.nextDouble(.5));
        return new Point2D(randomXVelocity, randomYVelocity).normalize();
    }

    private static Point2D getRandomTopScreenSpawnPosition() {
        double randomX =  RANDOM.nextDouble(0, GameApp.WINDOW_WIDTH);
        return new Point2D(randomX, 0);
    }

    public static GameObject createFriendlyProjectile(Point2D position) {
        return new FriendlyProjectile(position);
    }

    public static GameObject createEnemyProjectile(Point2D position) {
        return new EnemyProjectile(position);
    }

    public static GameObject createRandomlySpawnedAIShip(Consumer<GameObject> gameObjectCreatorFunction) {
        Point2D position = getRandomTopScreenSpawnPosition();
        AIShip ship = new AIShip(position, gameObjectCreatorFunction);
        ship.setMovementStrategy(MOVEMENT_STRATEGIES.get(RANDOM.nextInt(MOVEMENT_STRATEGIES.size())).apply(ship));
        return ship;
    }

    public static List<GameObject> createNRandomlySpawnedAIShips(int numberOfShips, Consumer<GameObject> gameObjectCreatorFunction) {
        List<GameObject> ships  = new ArrayList<>();
        for  (int i = 0; i < numberOfShips; i++) {
            ships.add(createRandomlySpawnedAIShip(gameObjectCreatorFunction));
        }
        return ships;
    }
}

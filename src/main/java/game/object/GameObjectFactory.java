package game.object;

import game.GameApp;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameObjectFactory {
    private static final Random RANDOM = new Random();

    public GameObject createRandomlySpawnedAsteroid() {
        Point2D spawnPosition = getRandomAsteroidSpawnPosition();
        Point2D spawnVelocity = getRandomAsteroidSpawnVelocity();
        return new Asteroid(spawnPosition, spawnVelocity);
    }

    public List<GameObject> createNRandomlySpawnedAsteroids(int numberOfAsteroids) {
        List<GameObject> asteroids  = new ArrayList<>();
        for  (int i = 0; i < numberOfAsteroids; i++) {
            asteroids.add(createRandomlySpawnedAsteroid());
        }
        return asteroids;
    }

    private Point2D getRandomAsteroidSpawnVelocity() {
        double randomXVelocity = RANDOM.nextDouble() - .5;
        double randomYVelocity = Math.abs(RANDOM.nextDouble());
        return new Point2D(randomXVelocity, randomYVelocity).normalize().multiply(Asteroid.DEFAULT_SPEED);
    }

    private Point2D getRandomAsteroidSpawnPosition() {
        double randomX =  RANDOM.nextDouble(0, GameApp.WINDOW_WIDTH);
        return new Point2D(randomX, 0);
    }

}

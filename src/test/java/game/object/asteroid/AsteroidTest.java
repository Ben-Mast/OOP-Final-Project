package game.object.asteroid;

import game.object.GameObject;
import game.object.GameObjectFactory;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class AsteroidTest {

    private Consumer<GameObject> dummyCreator() {
        return _ -> {};
    }

    @Test
    void bigAsteroidConstructorAndCollision() {
        BigAsteroid asteroid = new BigAsteroid(
                new Point2D(0, 0),
                new Point2D(10, 0),
                dummyCreator()
        );

        assertEquals(0.0, asteroid.getPosition().getX(), 1e-6);
        asteroid.onCollision(asteroid);
    }

    @Test
    void mediumAsteroidConstructorAndCollision() {
        MediumAsteroid asteroid = new MediumAsteroid(
                new Point2D(0, 0),
                new Point2D(0, 10),
                dummyCreator()
        );

        asteroid.onCollision(asteroid);
    }

    @Test
    void smallAsteroidConstructorAndCollision() {
        SmallAsteroid asteroid = new SmallAsteroid(
                new Point2D(0, 0),
                new Point2D(-5, 5),
                dummyCreator()
        );

        asteroid.onCollision(asteroid);
    }

    @Test
    void createNPreciselySpawnedAsteroidsCreatesCorrectNumber() {
        List<GameObject> asteroids = GameObjectFactory.createNPreciselySpawnedAsteroids(
                AsteroidSize.BIG,
                3,
                new Point2D(50, 50),
                _ -> {}
        );

        assertEquals(3, asteroids.size());
    }

    @Test
    void bigAsteroidHandleHitByProjectileSpawnsMediumsAndDestroysItself() {
        List<GameObject> created = new ArrayList<>();
        AtomicBoolean destroyed = new AtomicBoolean(false);

        BigAsteroid asteroid = new BigAsteroid(
                new Point2D(0, 0),
                new Point2D(0, 0),
                created::add
        );
        asteroid.setGameObjectDestroyer(_ -> destroyed.set(true));

        asteroid.handleHitByProjectile();

        assertTrue(destroyed.get());
        assertEquals(2, created.size());
        assertInstanceOf(MediumAsteroid.class, created.getFirst());
    }

    @Test
    void mediumAsteroidHandleHitByProjectileSpawnsSmallsAndDestroysItself() {
        List<GameObject> created = new ArrayList<>();
        AtomicBoolean destroyed = new AtomicBoolean(false);

        MediumAsteroid asteroid = new MediumAsteroid(
                new Point2D(0, 0),
                new Point2D(0, 0),
                created::add
        );
        asteroid.setGameObjectDestroyer(_ -> destroyed.set(true));

        asteroid.handleHitByProjectile();

        assertTrue(destroyed.get());
        assertEquals(3, created.size());
        assertInstanceOf(SmallAsteroid.class, created.getFirst());
    }

    @Test
    void smallAsteroidHandleHitByProjectileCreatesNothingAndDestroysItself() {
        List<GameObject> created = new ArrayList<>();
        AtomicBoolean destroyed = new AtomicBoolean(false);

        SmallAsteroid asteroid = new SmallAsteroid(
                new Point2D(0, 0),
                new Point2D(0, 0),
                created::add
        );
        asteroid.setGameObjectDestroyer(_ -> destroyed.set(true));

        asteroid.handleHitByProjectile();

        assertTrue(destroyed.get());
        assertEquals(0, created.size());
    }

    @Test
    void asteroidBouncesOffWorldBoundary() {
        BigAsteroid asteroid = new BigAsteroid(
                new Point2D(-5, -10),
                new Point2D(1, 1),
                _ -> {}
        );

        asteroid.update(0.016, Collections.emptySet());

        assertTrue(asteroid.getVelocity().getX() < 0);
        assertTrue(asteroid.getVelocity().getY() < 0);
    }



}


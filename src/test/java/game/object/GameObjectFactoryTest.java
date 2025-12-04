package game.object;

import game.object.projectile.EnemyProjectile;
import game.object.projectile.FriendlyProjectile;
import game.object.ship.AIShip;
import game.object.ship.Ship;
import game.strategy.AIMovementStrategy;
import game.strategy.AIMovementType;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class GameObjectFactoryTest {

    private Consumer<GameObject> dummyCreator(AtomicInteger counter) {
        return _ -> counter.incrementAndGet();
    }

    @Test
    void createRandomlySpawnedAsteroidReturnsAsteroid() {
        AtomicInteger created = new AtomicInteger(0);
        GameObject asteroid = GameObjectFactory.createRandomlySpawnedAsteroid(dummyCreator(created));
        assertNotNull(asteroid);
        assertTrue(created.get() >= 0);
    }

    @Test
    void createNRandomlySpawnedAsteroidsReturnsListOfCorrectSize() {
        List<GameObject> asteroids = GameObjectFactory.createNRandomlySpawnedAsteroids(5, _ -> {});
        assertEquals(5, asteroids.size());
    }

    @Test
    void createFriendlyProjectileReturnsProjectile() {
        GameObject gameObject = GameObjectFactory.createFriendlyProjectile(new Point2D(0, 0));
        assertInstanceOf(FriendlyProjectile.class,gameObject);
    }

    @Test
    void createEnemyProjectileReturnsProjectile() {
        GameObject gameObject = GameObjectFactory.createEnemyProjectile(new Point2D(0, 0));
        assertInstanceOf(EnemyProjectile.class,gameObject);
    }

    @Test
    void createRandomlySpawnedAIShipReturnsAIShip() {
        GameObject gameObject = GameObjectFactory.createRandomlySpawnedAIShip(_ -> {});
        assertInstanceOf(AIShip.class,gameObject);
    }

    @Test
    void createNRandomlySpawnedAIShipsReturnsListOfCorrectSize() {
        List<GameObject> ships = GameObjectFactory.createNRandomlySpawnedAIShips(3, _ -> {});
        assertEquals(3, ships.size());
    }

    @Test
    void createAIMovementStrategyReturnsNonNullStrategy() {
        Ship dummyShip = AIShip
                .getNewBuilder(_ -> {})
                .givePosition(new Point2D(0, 0))
                .build();

        AIMovementStrategy strategy = GameObjectFactory.createAIMovementStrategy(AIMovementType.DEFAULT, dummyShip);
        assertNotNull(strategy);
    }
}

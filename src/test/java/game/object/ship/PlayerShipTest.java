package game.object.ship;

import game.object.GameObject;
import game.object.projectile.FriendlyProjectile;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerShipTest {

    @Test
    void constructorInitializesHealthAndPosition() {
        AtomicInteger created = new AtomicInteger(0);
        Consumer<GameObject> creator = _ -> created.incrementAndGet();

        PlayerShip ship = new PlayerShip(new Point2D(100, 200), creator);

        assertEquals(100, ship.getPosition().getX(), 1e-6);
        assertTrue(ship.getHealth() > 0);
    }

    @Test
    void updateWithoutKeysDoesNotThrow() {
        PlayerShip ship = new PlayerShip(new Point2D(0, 0), _ -> {});
        ship.update(0.016, Collections.emptySet());
    }

    @Test
    void movementKeysChangePosition() {
        PlayerShip ship = new PlayerShip(new Point2D(0, 0), _ -> {});

        Set<KeyCode> keys = EnumSet.of(KeyCode.S);
        ship.update(1.0, keys);

        assertNotEquals(0.0, ship.getPosition().getY(), 1e-6);
    }

    @Test
    void takeDamageReducesHealthAndCanKill() {
        PlayerShip ship = new PlayerShip(new Point2D(0, 0), _ -> {});
        ship.setGameObjectDestroyer(_ -> {});

        double initialHealth = ship.getHealth();
        ship.takeDamage(1.0);
        assertTrue(ship.getHealth() < initialHealth);

        ship.takeDamage(1000.0);
        assertTrue(ship.getHealth() <= 0.0);
    }

    @Test
    void handlesSelfCollisionGracefully() {
        PlayerShip ship = new PlayerShip(new Point2D(0, 0), _ -> {});

        ship.onCollision(ship);
    }

    @Test
    void getGameObjectCreatorReturnsSameConsumer() {
        Consumer<GameObject> creator = _ -> {};
        PlayerShip ship = new PlayerShip(new Point2D(0, 0), creator);

        assertSame(creator, ship.getGameObjectCreator());
    }

    @Test
    void attemptShotCreatesProjectile() {
        List<GameObject> created = new ArrayList<>();

        PlayerShip ship = new PlayerShip(new Point2D(0, 0), created::add);

        ship.setShotCooldownTimer(0);

        ship.attemptShot();

        assertEquals(1, created.size());
        assertInstanceOf(FriendlyProjectile.class, created.getFirst());
        assertEquals(ship.getShotCooldown(), ship.getShotCooldownTimer());
    }

}


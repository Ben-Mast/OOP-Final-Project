package game.object.ship;

import game.ResourceManager;
import game.object.GameObject;
import game.object.projectile.EnemyProjectile;
import game.object.projectile.FriendlyProjectile;
import game.strategy.AIMovementStrategy;
import game.strategy.AIMovementType;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class AIShipTest {

    private Consumer<GameObject> dummyCreator() {
        return _ -> {};
    }

    @Test
    void builderSetsDefaultsAndBuildsShip() {
        AIShip ship = AIShip
                .getNewBuilder(dummyCreator())
                .givePosition(new Point2D(50, 50))
                .build();

        assertNotNull(ship);
        assertEquals(50, ship.getPosition().getX(), 1e-6);
    }

    @Test
    void builderAllowsCustomStats() {
        AIShip ship = AIShip
                .getNewBuilder(dummyCreator())
                .givePosition(new Point2D(0, 0))
                .giveHealth(20.0)
                .giveMoveSpeed(100.0)
                .giveShotCooldown(0.5)
                .giveMovementStrategy(AIMovementType.DEFAULT)
                .giveSprite(ResourceManager.getInstance().getEnemyShipSprite())
                .build();

        assertEquals(20.0, ship.getHealth(), 1e-6);
        assertEquals(100.0, ship.getMoveSpeed(), 1e-6);
        assertEquals(0.5, ship.getShotCooldown(), 1e-6);
    }

    @Test
    void updateMovesShipAndAttemptsShots() {
        AIShip ship = AIShip
                .getNewBuilder(dummyCreator())
                .givePosition(new Point2D(0, 0))
                .build();

        Set<KeyCode> noKeys = Collections.emptySet();
        ship.update(1.0, noKeys);

        assertNotEquals(0.0, ship.getPosition().getY(), 1e-6);
    }

    @Test
    void setMovementStrategyUpdatesBehavior() {
        AIShip ship = AIShip
                .getNewBuilder(dummyCreator())
                .givePosition(new Point2D(0, 0))
                .build();

        AIMovementStrategy strategy = _ -> new Point2D(0, 10);
        ship.setMovementStrategy(strategy);

        ship.update(1.0, Collections.emptySet());
        assertEquals(10.0, ship.getPosition().getY(), 1e-6);
    }

    @Test
    void attemptShotCreatesProjectile() {
        List<GameObject> created = new ArrayList<>();

        AIShip ship = AIShip
                .getNewBuilder(created::add)
                .givePosition(new Point2D(0, 0))
                .build();

        ship.setShotCooldownTimer(0);

        ship.attemptShot();

        assertEquals(1, created.size());
        assertInstanceOf(EnemyProjectile.class, created.getFirst());
        assertEquals(ship.getShotCooldown(), ship.getShotCooldownTimer());
    }

    @Test
    void aiShipTakesDamageWhenHitByFriendlyProjectile() {
        AIShip ship = AIShip
                .getNewBuilder(go -> {})
                .givePosition(new Point2D(0, 0))
                .giveHealth(10.0)
                .build();

        FriendlyProjectile proj = new FriendlyProjectile(new Point2D(0, 0));
        ship.onCollision(proj);

        assertTrue(ship.getHealth()< 10);
    }

}


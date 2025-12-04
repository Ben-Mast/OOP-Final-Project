package game.object.projectile;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectileTest {

    @Test
    void friendlyProjectileConstructorSetsDefaults() {
        FriendlyProjectile proj = new FriendlyProjectile(new Point2D(10, 20));
        assertEquals(10, proj.getPosition().getX(), 1e-6);
        assertTrue(proj.getDamage() > 0);
    }

    @Test
    void enemyProjectileConstructorSetsDefaults() {
        EnemyProjectile proj = new EnemyProjectile(new Point2D(30, 40));
        assertEquals(30, proj.getPosition().getX(), 1e-6);
        assertTrue(proj.getDamage() > 0);
    }

    @Test
    void setAndGetDamageWork() {
        FriendlyProjectile proj = new FriendlyProjectile(new Point2D(0, 0));
        proj.setDamage(5.0);
        assertEquals(5.0, proj.getDamage(), 1e-6);
    }

    @Test
    void onCollisionDoesNotThrow() {
        FriendlyProjectile proj = new FriendlyProjectile(new Point2D(0, 0));
        proj.setGameObjectDestroyer(_ -> {});
        proj.onCollision(proj);
    }
}


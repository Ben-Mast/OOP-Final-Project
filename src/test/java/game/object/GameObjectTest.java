package game.object;

import game.ResourceManager;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class GameObjectTest {

    static class TestGameObject extends GameObject {

        public TestGameObject(Point2D position) {
            super(position, new WritableImage(10, 10));
        }

        @Override
        public void onCollision(GameObject otherObject) {
            // no-op for testing
        }

        @Override
        protected void checkAndResolveWorldBoundaryCollision() {
            // no-op
        }

        @Override
        public void update(double deltaTime, Set<KeyCode> keys) {
            setVelocity(new Point2D(1, 0));
            setPosition(getPosition().add(getVelocity().multiply(deltaTime)));
            super.update(deltaTime, keys);
        }

        public void collideWithKnockback(GameObject other) {
            handleCollisionWithKnockback(other);
        }
    }

    @Test
    void constructorSetsFields() {
        TestGameObject obj = new TestGameObject(new Point2D(5, 5));
        assertEquals(5, obj.getPosition().getX(), 1e-6);
        assertTrue(obj.getHitRadius() > 0);
        assertEquals(GameObject.DEFAULT_INVINCIBILITY_TIME, obj.getInvincibilityTime());
    }

    @Test
    void gettersAndSettersWork() {
        TestGameObject obj = new TestGameObject(new Point2D(0, 0));

        obj.setPosition(new Point2D(2, 3));
        assertEquals(2, obj.getPosition().getX());
        assertEquals(3, obj.getPosition().getY());

        obj.setVelocity(new Point2D(4, 5));
        assertEquals(4, obj.getVelocity().getX());

        obj.setHitRadius(7);
        assertEquals(7, obj.getHitRadius(), 1e-6);

        obj.setInvincibilityTime(1.2);
        assertEquals(1.2, obj.getInvincibilityTime(), 1e-6);

        obj.setInvincibilityCooldown(0.5);
        assertEquals(0.5, obj.getInvincibilityCooldown(), 1e-6);
    }

    @Test
    void gameObjectDestroyerIsCalled() {
        TestGameObject obj = new TestGameObject(new Point2D(0, 0));
        AtomicBoolean destroyed = new AtomicBoolean(false);
        obj.setGameObjectDestroyer(go -> destroyed.set(true));

        obj.getGameObjectDestroyer().accept(obj);
        assertTrue(destroyed.get());
    }

    @Test
    void renderDrawsSpriteAndHitbox() {
        TestGameObject obj = new TestGameObject(new Point2D(10, 10));
        Canvas canvas = new Canvas(200, 200);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        obj.render(gc);
    }

    @Test
    void updateHandlesInvincibilityTimer() {
        TestGameObject obj = new TestGameObject(new Point2D(0, 0));
        obj.setInvincibilityCooldown(0.1);
        obj.update(0.1, Collections.emptySet());
        assertEquals(0.0, obj.getInvincibilityCooldown(), 1e-6);
    }

    @Test
    void collisionWithKnockbackAdjustsPosition() {
        TestGameObject obj1 = new TestGameObject(new Point2D(0, 0));
        TestGameObject obj2 = new TestGameObject(new Point2D(1, 0));

        Point2D before = obj1.getPosition();
        obj1.collideWithKnockback(obj2);
        Point2D after = obj1.getPosition();

        assertNotEquals(before, after);
    }

    @Test
    void gameObjectIsDestroyedWhenOutOfBounds() {
        class TestObject extends GameObject {
            boolean destroyed = false;
            TestObject(Point2D position) { super(position, ResourceManager.getInstance().getEnemyShipSprite()); }
            @Override public void onCollision(GameObject object) {}
            @Override protected void checkAndResolveWorldBoundaryCollision() { super.checkAndResolveWorldBoundaryCollision(); }
            @Override public void update(double deltaTime, Set<KeyCode> keys) {
                checkAndResolveWorldBoundaryCollision();
            }
        }

        TestObject object = new TestObject(new Point2D(-10, -10));
        object.setGameObjectDestroyer(_ -> object.destroyed = true);
        object.update(0.016, Collections.emptySet());

        assertTrue(object.destroyed);
    }

}

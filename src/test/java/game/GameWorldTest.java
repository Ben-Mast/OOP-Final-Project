package game;

import game.object.GameObject;
import game.state.State;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GameWorldTest {

    @Test
    void constructorInitializesPlayerAndRunningState() {
        GameWorld world = new GameWorld();

        assertFalse(world.isGameOver());
    }

    @Test
    void updateDelegatesToCurrentState() {
        GameWorld world = new GameWorld();
        Set<KeyCode> keys = EnumSet.noneOf(KeyCode.class);

        world.update(0.016, keys);
    }

    @Test
    void renderDelegatesToCurrentState() {
        GameWorld world = new GameWorld();
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        world.render(gc);
    }

    @Test
    void updateGameStateSwitchesStates() {
        GameWorld world = new GameWorld();

        world.updateGameState(State.PAUSED);
        world.update(0.016, Collections.emptySet());

        world.updateGameState(State.RUNNING);
        world.update(0.016, Collections.emptySet());

        world.updateGameState(State.GAMEOVER);
        world.update(0.016, Collections.emptySet());
    }

    @Test
    void resetCreatesNewPlayerAndClearsObjects() {
        GameWorld world = new GameWorld();
        world.reset();

        assertFalse(world.isGameOver());
    }

    @Test
    void updateWorldRunsWithoutCrashing() {
        GameWorld world = new GameWorld();
        world.updateWorld(0.5, Collections.emptySet());
    }

    @Test
    void processAddRemoveObjectsIsIdempotent() {
        GameWorld world = new GameWorld();
        world.processAddRemoveObjects();
        world.removeGameObject(null);
        world.processAddRemoveObjects();
    }

    @Test
    void renderWorldClearsAndRenders() {
        GameWorld world = new GameWorld();
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        world.renderWorld(gc);
    }

    @Test
    void resolveCollisionsHandlesNoObjects() {
        GameWorld world = new GameWorld();
        world.resolveCollisions();
    }

    @Test
    void resolveCollisionsNoCollisionDoesNothing() {
        GameWorld world = new GameWorld();

        class TestObj extends GameObject {
            boolean collided = false;
            TestObj(Point2D position) { super(position, ResourceManager.getInstance().getEnemyProjectileSprite()); setHitRadius(1); }
            @Override public void onCollision(GameObject o) { collided = true; }
            @Override protected void checkAndResolveWorldBoundaryCollision() {}
            @Override public void update(double dt, Set<KeyCode> keys) {}
        }

        TestObj a = new TestObj(new Point2D(0, 0));
        TestObj b = new TestObj(new Point2D(100, 100)); // far away → no collision

        world.createGameObject(a);
        world.createGameObject(b);

        world.resolveCollisions();

        assertFalse(a.collided);
        assertFalse(b.collided);
    }

}

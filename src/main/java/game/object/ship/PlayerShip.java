package game.object.ship;

import game.GameApp;
import game.object.GameObject;
import game.object.GameObjectFactory;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class PlayerShip extends Ship {
    protected static final double DEFAULT_MOVE_SPEED = 150;
    protected static final double DEFAULT_SHOT_COOLDOWN = 1;
    private static final Image DEFAULT_SPRITE = new Image(Objects.requireNonNull(PlayerShip.class.getClassLoader().getResourceAsStream("player_ship.png")));

    public PlayerShip(Point2D position, Consumer<GameObject> gameObjectCreatorFunction) {
        super(position, DEFAULT_SPRITE, gameObjectCreatorFunction);
        moveSpeed = DEFAULT_MOVE_SPEED;
        shotCooldown = DEFAULT_SHOT_COOLDOWN;
    }

    @Override
    public void update(double deltaTime,  Set<KeyCode> keys) {
        shotCooldownTimer = shotCooldownTimer > 0 ? shotCooldownTimer - deltaTime : 0;
        handleMovement(
            keys.contains(KeyCode.W),
            keys.contains(KeyCode.S),
            keys.contains(KeyCode.A),
            keys.contains(KeyCode.D)
        );
        if (keys.contains(KeyCode.SPACE)) {
            attemptShot();
        }
        super.update(deltaTime, keys);
    }

    @Override
    protected void checkAndResolveWorldBoundaryCollision() {
        Point2D currentPosition = getPosition();
        double currentPositionX = currentPosition.getX();
        double currentPositionY = currentPosition.getY();
        if (currentPositionX < 0) {
            currentPositionX = 0;
        } else if (currentPositionX > GameApp.WINDOW_WIDTH) {
            currentPositionX = GameApp.WINDOW_WIDTH;
        }
        if (currentPositionY < 0) {
            currentPositionY = 0;
        } else if (currentPositionY > GameApp.WINDOW_HEIGHT) {
            currentPositionY = GameApp.WINDOW_HEIGHT;
        }
        position = new Point2D(currentPositionX, currentPositionY);
    }

    @Override
    public void onCollision(GameObject otherObject) {
        if (invincibilityCooldown > 0) return;
        invincibilityCooldown = invincibilityTime;

        switch(otherObject.getClass().getSimpleName()) {
            case "Asteroid":
                handleCollisionWithKnockback(otherObject);
        }
    }

    private void attemptShot() {
        if (shotCooldownTimer == 0) {
            shotCooldownTimer = shotCooldown;
            gameObjectCreatorFunction.accept(GameObjectFactory.createFriendlyProjectile(getPosition().subtract(0,getHeight()/2)));
        }
    }

    private void handleMovement(boolean up, boolean down, boolean left, boolean right) {
        double x = 0;
        double y = 0;

        if (up)    y -= moveSpeed;
        if (down)  y += moveSpeed;
        if (left)  x -= moveSpeed;
        if (right) x += moveSpeed;

        velocity = new Point2D(x, y);

        if (velocity.magnitude() > 0) {
            velocity = velocity.normalize().multiply(moveSpeed);
        }
    }

}

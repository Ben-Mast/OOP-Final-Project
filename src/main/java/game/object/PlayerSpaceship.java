package game.object;

import game.GameApp;
import game.strategy.ShootingStrategy;
import game.strategy.SingleShotStrategy;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import java.util.Set;
import java.util.function.Consumer;

public class PlayerSpaceship extends GameObject {
    private static final double DEFAULT_MOVE_SPEED = 150;
    private static final double DEFAULT_SHOT_COOLDOWN = .5;
    private static final ShootingStrategy DEFAULT_SHOOTING_STRATEGY = new SingleShotStrategy();

    private final Consumer<GameObject> gameObjectCreatorFunction;
    private ShootingStrategy shootingStrategy;
    private double shotCooldown;
    private double shotCooldownTimer = 0;
    private double moveSpeed;

    public PlayerSpaceship(Point2D position, Image sprite, Consumer<GameObject> gameObjectCreatorFunction) {
        super(position, sprite);
        moveSpeed = DEFAULT_MOVE_SPEED;
        shotCooldown = DEFAULT_SHOT_COOLDOWN;
        shootingStrategy = DEFAULT_SHOOTING_STRATEGY;
        this.gameObjectCreatorFunction = gameObjectCreatorFunction;
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

    private void attemptShot() {
        if (shotCooldownTimer == 0) {
            shotCooldownTimer = shotCooldown;
            shootingStrategy.shoot(this, gameObjectCreatorFunction);
        }
    }

}

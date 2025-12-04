package game.object.ship;

import game.GameApp;
import game.ResourceManager;
import game.object.GameObject;
import game.object.GameObjectFactory;
import game.object.projectile.Projectile;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Set;
import java.util.function.Consumer;

public class PlayerShip extends Ship {

    private static final double DEFAULT_MOVE_SPEED = 150;
    private static final double DEFAULT_SHOT_COOLDOWN = 1;
    private static final double DEFAULT_HEALTH = 20;
    private static final Image DEFAULT_SPRITE = ResourceManager.getInstance().getPlayerShipSprite();

    public PlayerShip(Point2D position, Consumer<GameObject> gameObjectCreatorFunction) {
        super(position, DEFAULT_SPRITE, gameObjectCreatorFunction, DEFAULT_HEALTH, DEFAULT_MOVE_SPEED, DEFAULT_SHOT_COOLDOWN);
    }

    @Override
    public void update(double deltaTime, Set<KeyCode> keys) {
        if (getShotCooldownTimer() > 0) {
            setShotCooldownTimer(Math.max(0, getShotCooldownTimer() - deltaTime));
        }

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
        double x = getPosition().getX();
        double y = getPosition().getY();

        if (x < 0) x = 0;
        else if (x > GameApp.WINDOW_WIDTH) x = GameApp.WINDOW_WIDTH;

        if (y < 0) y = 0;
        else if (y > GameApp.WINDOW_HEIGHT) y = GameApp.WINDOW_HEIGHT;

        setPosition(new Point2D(x, y));
    }

    @Override
    public void onCollision(GameObject otherObject) {
        if (getInvincibilityCooldown() > 0) return;

        setInvincibilityCooldown(getInvincibilityTime());

        switch (otherObject.getClass().getSimpleName()) {
            case "BigAsteroid", "MediumAsteroid", "SmallAsteroid", "AIShip":
                handleCollisionWithKnockback(otherObject);
                break;
            case "EnemyProjectile":
                Projectile  projectile = (Projectile) otherObject;
                takeDamage(projectile.getDamage());
                break;
        }
    }

    @Override
    protected void attemptShot() {
        if (getShotCooldownTimer() == 0) {
            setShotCooldownTimer(getShotCooldown());
            getGameObjectCreator().accept(GameObjectFactory.createFriendlyProjectile(getPosition().subtract(0, getHeight() / 2)));
        }
    }

    private void handleMovement(boolean up, boolean down, boolean left, boolean right) {
        double xVel = 0;
        double yVel = 0;
        double speed = getMoveSpeed();

        if (up)    yVel -= speed;
        if (down)  yVel += speed;
        if (left)  xVel -= speed;
        if (right) xVel += speed;

        Point2D newVelocity = new Point2D(xVel, yVel);

        if (newVelocity.magnitude() > 0) {
            newVelocity = newVelocity.normalize().multiply(speed);
        }

        setVelocity(newVelocity);
    }
}

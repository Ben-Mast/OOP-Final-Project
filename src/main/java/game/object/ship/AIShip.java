package game.object.ship;

import game.GameApp;
import game.ResourceManager;
import game.object.GameObject;
import game.object.GameObjectFactory;
import game.object.projectile.FriendlyProjectile;
import game.object.projectile.Projectile;
import game.strategy.AIMovementStrategy;
import game.strategy.DefaultAIMovement;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Set;
import java.util.function.Consumer;

public class AIShip extends Ship {
    private static final double DEFAULT_MOVE_SPEED = 50;
    private static final double DEFAULT_SHOT_COOLDOWN = 1;
    private static final double DEFAULT_HEALTH = 10;
    private static final Image DEFAULT_SPRITE = ResourceManager.getInstance().getEnemyShipSprite();
    private AIMovementStrategy movementStrategy;

    public AIShip (Point2D position, Consumer<GameObject> gameObjectCreatorFunction) {
        super(position, DEFAULT_SPRITE, gameObjectCreatorFunction, DEFAULT_HEALTH, DEFAULT_MOVE_SPEED, DEFAULT_SHOT_COOLDOWN);
        setMovementStrategy(new DefaultAIMovement(this));
    }

    @Override
    public void update(double deltaTime,  Set<KeyCode> keys) {
        if (getShotCooldownTimer() > 0) {
            setShotCooldownTimer(Math.max(0, getShotCooldownTimer() - deltaTime));
        }
        setVelocity(movementStrategy.updateVelocity(deltaTime));
        attemptShot();
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
            getGameObjectDestroyer().accept(this);
        }
        setPosition(new Point2D(currentPositionX, currentPositionY));
    }

    public void setMovementStrategy(AIMovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    protected void attemptShot() {
        if (shotCooldownTimer == 0) {
            shotCooldownTimer = shotCooldown;
            gameObjectCreator.accept(GameObjectFactory.createEnemyProjectile(getPosition().add(0,getHeight()/2)));
        }
    }

    @Override
    public void onCollision(GameObject otherObject) {
        switch (otherObject.getClass().getSimpleName()) {
            case "FriendlyProjectile":
                Projectile projectile = (Projectile) otherObject;
                takeDamage(projectile.getDamage());
                break;
        }
    }

}

package game.object.ship;

import game.object.GameObject;
import game.object.GameObjectFactory;
import game.strategy.AIMovementStrategy;
import game.strategy.DefaultAIMovement;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class AIShip extends Ship {
    protected static final double DEFAULT_MOVE_SPEED = 50;
    protected static final double DEFAULT_SHOT_COOLDOWN = 1;
    private static final Image DEFAULT_SPRITE = new Image(Objects.requireNonNull(AIShip.class.getClassLoader().getResourceAsStream("enemy_ship.png")));
    private AIMovementStrategy movementStrategy;

    public AIShip (Point2D position, Consumer<GameObject> gameObjectCreatorFunction) {
        super(position, DEFAULT_SPRITE, gameObjectCreatorFunction);
        moveSpeed = DEFAULT_MOVE_SPEED;
        shotCooldown = DEFAULT_SHOT_COOLDOWN;
        this.movementStrategy = new DefaultAIMovement(this);
    }

    @Override
    public void update(double deltaTime,  Set<KeyCode> keys) {
        shotCooldownTimer = shotCooldownTimer > 0 ? shotCooldownTimer - deltaTime : 0;
        this.velocity = movementStrategy.updateVelocity(deltaTime);
        attemptShot();
        super.update(deltaTime, keys);
    }

    public void setMovementStrategy(AIMovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    protected void attemptShot() {
        if (shotCooldownTimer == 0) {
            shotCooldownTimer = shotCooldown;
            gameObjectCreatorFunction.accept(GameObjectFactory.createEnemyProjectile(getPosition().add(0,getHeight()/2)));
        }
    }

}

package game.object.ship;

import game.GameApp;
import game.ResourceManager;
import game.object.GameObject;
import game.object.GameObjectFactory;
import game.object.projectile.Projectile;
import game.strategy.AIMovementStrategy;
import game.strategy.AIMovementType;
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
    private static final Point2D DEFAULT_SPAWN =  new Point2D((double) GameApp.WINDOW_WIDTH /2,0);
    private AIMovementStrategy movementStrategy;

    private AIShip(Builder builder) {
        super(
                builder.position,
                builder.sprite,
                builder.gameObjectCreator,
                builder.health,
                builder.moveSpeed,
                builder.shotCooldown
        );
    }

    public static Builder getNewBuilder(Consumer<GameObject> gameObjectCreator) {
        return new Builder(gameObjectCreator);
    }

    public static class Builder {

        private final Consumer<GameObject> gameObjectCreator;

        private Point2D position = DEFAULT_SPAWN;
        private Image sprite = DEFAULT_SPRITE;
        private double health = DEFAULT_HEALTH;
        private double moveSpeed = DEFAULT_MOVE_SPEED;
        private double shotCooldown = DEFAULT_SHOT_COOLDOWN;
        private AIMovementType movementType = AIMovementType.DEFAULT;

        public Builder(Consumer<GameObject> creator) {
            this.gameObjectCreator = creator;
        }

        public Builder givePosition(Point2D position) {
            this.position = position;
            return this;
        }

        public Builder giveSprite(Image sprite) {
            this.sprite = sprite;
            return this;
        }

        public Builder giveHealth(double health) {
            this.health = health;
            return this;
        }

        public Builder giveMoveSpeed(double moveSpeed) {
            this.moveSpeed = moveSpeed;
            return this;
        }

        public Builder giveShotCooldown(double shotCooldown) {
            this.shotCooldown = shotCooldown;
            return this;
        }

        public Builder giveMovementStrategy(AIMovementType type) {
            this.movementType = type;
            return this;
        }

        public AIShip build() {
            AIShip ship = new AIShip(this);
            ship.movementStrategy = GameObjectFactory.createAIMovementStrategy(movementType, ship);
            return ship;
        }
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

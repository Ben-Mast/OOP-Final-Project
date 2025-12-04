package game.object.ship;

import game.object.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.function.Consumer;

public abstract class Ship extends GameObject {
    protected Consumer<GameObject> gameObjectCreator;
    protected double shotCooldown;
    protected double shotCooldownTimer = 0;
    protected double moveSpeed;
    protected double health;

    public Ship(Point2D position, Image sprite, Consumer<GameObject> gameObjectCreator, double health, double moveSpeed, double shotCooldown) {
        super(position, sprite);
        setHealth(health);
        setMoveSpeed(moveSpeed);
        setShotCooldown(shotCooldown);
        setGameObjectCreator(gameObjectCreator);
    }

    protected void attemptShot() {
    }

    protected void handleDeath() {
        getGameObjectDestroyer().accept(this);
    }

    public void takeDamage(double damage) {
        double newHealth = getHealth() - damage;
        if (newHealth <= 0) {
            handleDeath();
        } else {
            setHealth(newHealth);
        }
    }

    protected void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    protected void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    protected void setShotCooldown(double shotCooldown) {
        this.shotCooldown = shotCooldown;
    }

    public double getShotCooldownTimer() {
        return shotCooldownTimer;
    }

    protected void setShotCooldownTimer(double shotCooldownTimer) {
        this.shotCooldownTimer = shotCooldownTimer;
    }

    public double getShotCooldown() {
        return shotCooldown;
    }

    public Consumer<GameObject> getGameObjectCreator() {
        return gameObjectCreator;
    }

    protected void setGameObjectCreator(Consumer<GameObject> gameObjectCreator) {
        this.gameObjectCreator = gameObjectCreator;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }
}

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

    public Ship(Point2D position, Image sprite, Consumer<GameObject> gameObjectCreator) {
        super(position, sprite);
        setGameObjectCreator(gameObjectCreator);
    }

    protected void attemptShot() {
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

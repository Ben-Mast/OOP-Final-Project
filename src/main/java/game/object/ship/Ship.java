package game.object.ship;

import game.object.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.function.Consumer;

public abstract class Ship extends GameObject {
    protected Consumer<GameObject> gameObjectCreatorFunction;
    protected double shotCooldown;
    protected double shotCooldownTimer = 0;
    protected double moveSpeed;

    public Ship(Point2D position, Image sprite, Consumer<GameObject> gameObjectCreatorFunction) {
        super(position, sprite);
        this.gameObjectCreatorFunction = gameObjectCreatorFunction;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }
}

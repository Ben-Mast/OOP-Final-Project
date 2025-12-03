package game.object.projectile;

import game.object.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public abstract class Projectile extends GameObject {

    public Projectile(Point2D position, Image sprite, Point2D velocity) {
        super(position, sprite);
        this.velocity = velocity;
    }

    @Override
    public void onCollision(GameObject otherObject) {
        gameObjectDestroyer.accept(this);
    }

}

package game.object.projectile;

import game.object.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public abstract class Projectile extends GameObject {
    private double damage;

    public Projectile(Point2D position, Image sprite, Point2D velocity, double damage) {
        super(position, sprite);
        setVelocity(velocity);
        setDamage(damage);
    }

    @Override
    public void onCollision(GameObject otherObject) {
        getGameObjectDestroyer().accept(this);
    }

    public double getDamage() {
        return damage;
    }
    protected void setDamage(double damage) {
        this.damage = damage;
    }

}

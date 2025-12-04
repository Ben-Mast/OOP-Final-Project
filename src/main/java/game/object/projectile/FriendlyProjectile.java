package game.object.projectile;

import game.ResourceManager;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class FriendlyProjectile extends Projectile {
    private static final Image DEFAULT_SPRITE = ResourceManager.getInstance().getFriendlyProjectileSprite();
    private static final Point2D DEFAULT_VEL = new Point2D(0,-200);
    private static final double DEFAULT_DAMAGE = 5;

    public FriendlyProjectile(Point2D position) {
        super(position,DEFAULT_SPRITE,DEFAULT_VEL, DEFAULT_DAMAGE);
    }
}

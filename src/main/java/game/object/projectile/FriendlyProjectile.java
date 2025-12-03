package game.object.projectile;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.Objects;

public class FriendlyProjectile extends Projectile {
    private static final Image DEFAULT_SPRITE = new Image(Objects.requireNonNull(Projectile.class.getClassLoader().getResourceAsStream("player_shot.png")));
    private static final Point2D DEFAULT_VEL = new Point2D(0,-200);

    public FriendlyProjectile(Point2D position) {
        super(position,DEFAULT_SPRITE,DEFAULT_VEL);
    }
}

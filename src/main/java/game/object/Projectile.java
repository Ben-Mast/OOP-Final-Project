package game.object;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import java.util.Objects;

public class Projectile extends GameObject {
    private static final Image DEFAULT_SPRITE = new Image(Objects.requireNonNull(Projectile.class.getClassLoader().getResourceAsStream("player_shot.png")));
    private static final Point2D DEFAULT_VEL = new Point2D(0,-200);

    public Projectile(Point2D position) {
        super(position, DEFAULT_SPRITE);
        velocity = DEFAULT_VEL;
    }

    @Override
    public void onCollision(GameObject otherObject) {
        System.out.println("Projectile collided with " + otherObject.getClass().getSimpleName());
    }
}

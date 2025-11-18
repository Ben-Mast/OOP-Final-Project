package game.object;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import java.util.Objects;

public class Asteroid extends GameObject {
    public static final Image DEFAULT_SPRITE = new Image(Objects.requireNonNull(Projectile.class.getClassLoader().getResourceAsStream("asteroid_1.png")));
    private static final Point2D DEFAULT_VEL = new Point2D(0,50);

    public Asteroid(Point2D position) {
        super(position, DEFAULT_SPRITE);
        this.velocity = DEFAULT_VEL;
        this.hitRadius = sprite.getWidth() / 2 * 0.9;
    }

    @Override
    public void onCollision(GameObject otherObject) {
        if (otherObject instanceof Projectile) {
            gameObjectDestroyer.accept(this);
        }
    }
}

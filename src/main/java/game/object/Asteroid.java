package game.object;

import game.GameApp;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import java.util.Objects;

public class Asteroid extends GameObject {
    public static final Image DEFAULT_SPRITE = new Image(Objects.requireNonNull(Projectile.class.getClassLoader().getResourceAsStream("asteroid_1.png")));
    public static final double DEFAULT_SPEED = 50.0;

    public Asteroid(Point2D position, Point2D velocity) {
        super(position, DEFAULT_SPRITE);
        this.velocity = velocity;
        this.hitRadius = sprite.getWidth() / 2 * 0.9;
    }

    @Override
    protected void checkAndResolveWorldBoundaryCollision() {
        double currentPositionX = position.getX();
        double currentPositionY = position.getY();
        double newVelocityX = velocity.getX();
        double newVelocityY = velocity.getY();

        if (currentPositionX < 0 ||  currentPositionX > GameApp.WINDOW_WIDTH) {
            newVelocityX = -newVelocityX;
        }
        if (currentPositionY > GameApp.WINDOW_HEIGHT) {
            gameObjectDestroyer.accept(this);
        }
        velocity = new Point2D(newVelocityX, newVelocityY);
    }

    @Override
    public void onCollision(GameObject otherObject) {
        String otherObjectType = otherObject.getClass().getSimpleName();

        switch (otherObjectType) {
            case "Projectile":
                gameObjectDestroyer.accept(this);
                break;
        }

    }
}

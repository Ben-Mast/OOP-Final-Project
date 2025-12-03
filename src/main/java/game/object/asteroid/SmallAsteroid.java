package game.object.asteroid;

import game.object.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.function.Consumer;

public class SmallAsteroid extends Asteroid {
    private static final double DEFAULT_SPEED = 100;
    private static final Image DEFAULT_SPRITE = new Image(Objects.requireNonNull(BigAsteroid.class.getClassLoader().getResourceAsStream("asteroid_small.png")));

    public SmallAsteroid(Point2D position, Point2D velocity, Consumer<GameObject> gameObjectCreator) {
        super(position, velocity.multiply(DEFAULT_SPEED), DEFAULT_SPRITE, gameObjectCreator);
    }

    @Override
    public void onCollision(GameObject otherObject) {
        String otherObjectType = otherObject.getClass().getSimpleName();

        switch (otherObjectType) {
            case "FriendlyProjectile", "EnemyProjectile":
                gameObjectDestroyer.accept(this);
                break;
        }
    }
}

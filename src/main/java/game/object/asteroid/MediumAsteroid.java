package game.object.asteroid;

import game.object.GameObject;
import game.object.GameObjectFactory;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class MediumAsteroid extends Asteroid {
    private static final double DEFAULT_SPEED = 75;
    private static final Image DEFAULT_SPRITE = new Image(Objects.requireNonNull(BigAsteroid.class.getClassLoader().getResourceAsStream("asteroid_medium.png")));

    public MediumAsteroid(Point2D position, Point2D velocity, Consumer<GameObject> gameObjectCreator) {
        super(position, velocity.multiply(DEFAULT_SPEED), DEFAULT_SPRITE, gameObjectCreator);
    }

    @Override
    public void onCollision(GameObject otherObject) {
        String otherObjectType = otherObject.getClass().getSimpleName();

        switch (otherObjectType) {
            case "FriendlyProjectile", "EnemyProjectile":
                List<GameObject> asteroidsToCreateOnDestruction = GameObjectFactory.createNPreciselySpawnedAsteroids(AsteroidSize.SMALL, 3, getPosition(), gameObjectCreator);
                for  (GameObject asteroid : asteroidsToCreateOnDestruction) {
                    gameObjectCreator.accept(asteroid);
                }
                gameObjectDestroyer.accept(this);
                break;
        }
    }
}

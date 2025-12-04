package game.object.asteroid;

import game.ResourceManager;
import game.object.GameObject;
import game.object.GameObjectFactory;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.List;
import java.util.function.Consumer;

public class BigAsteroid extends Asteroid {
    private static final double DEFAULT_SPEED = 50;
    private static final Image DEFAULT_SPRITE = ResourceManager.getInstance().getAsteroidSpriteBig();

    public BigAsteroid(Point2D position, Point2D velocity, Consumer<GameObject> gameObjectCreator) {
        super(position, velocity.multiply(DEFAULT_SPEED), DEFAULT_SPRITE, gameObjectCreator);
    }

    @Override
    protected void handleHitByProjectile() {
        List<GameObject> asteroidsToCreateOnDestruction = GameObjectFactory.createNPreciselySpawnedAsteroids(AsteroidSize.MEDIUM, 2, getPosition(), gameObjectCreator);
        for  (GameObject asteroid : asteroidsToCreateOnDestruction) {
            getGameObjectCreator().accept(asteroid);
        }
        super.handleHitByProjectile();
    }
}

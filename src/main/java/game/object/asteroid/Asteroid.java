package game.object.asteroid;

import game.GameApp;
import game.object.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.function.Consumer;

public abstract class Asteroid extends GameObject {

    protected final Consumer<GameObject> gameObjectCreator;

    public Asteroid(Point2D position, Point2D velocity, Image sprite, Consumer<GameObject> gameObjectCreator) {
        super(position, sprite);
        this.velocity = velocity;
        this.hitRadius = sprite.getWidth() / 2 * 0.9;
        this.gameObjectCreator = gameObjectCreator;
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
        if (currentPositionY < 0 ||  currentPositionY > GameApp.WINDOW_HEIGHT) {
            newVelocityY = -newVelocityY;
        }
        velocity = new Point2D(newVelocityX, newVelocityY);
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

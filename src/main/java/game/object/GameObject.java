package game.object;

import game.GameApp;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Set;
import java.util.function.Consumer;

public abstract class GameObject {
    public static final double DEFAULT_INVINCIBILITY_TIME = .3;
    private static final double COLLISION_KNOCKBACK = 2;

    protected double hitRadius;
    protected double invincibilityTime;
    protected Point2D position;
    protected Point2D velocity;
    protected final Image sprite;
    protected Consumer<GameObject> gameObjectDestroyer;

    protected double invincibilityCooldown = 0;

    public GameObject(Point2D position, Image sprite) {
        this.position = position;
        this.sprite = sprite;
        this.hitRadius = sprite.getWidth() / 2 * 0.3;
        this.invincibilityTime = DEFAULT_INVINCIBILITY_TIME;
    }

    public void setGameObjectDestroyer(Consumer<GameObject> gameObjectDestroyer) {
        this.gameObjectDestroyer = gameObjectDestroyer;
    }

    public void onCollision(GameObject otherObject) {

    }

    protected void handleCollisionWithKnockback(GameObject otherObject) {
        Point2D otherObjectPosition = otherObject.getPosition();
        Point2D myPosition = getPosition();

        Point2D deflectionVector = myPosition.subtract(otherObjectPosition);
        position = myPosition.add(deflectionVector.multiply(COLLISION_KNOCKBACK));
    }

    public double getWidth() {
        return sprite.getWidth();
    }

    public double getHeight() {
        return sprite.getHeight();
    }

    public Point2D getPosition() {
        return position;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public double getHitRadius() {
        return hitRadius;
    }

    public void update(double deltaTime, Set<KeyCode> keys) {
        invincibilityCooldown -= deltaTime;
        position = position.add(velocity.multiply(deltaTime));
        checkAndResolveWorldBoundaryCollision();
    }

    protected void checkAndResolveWorldBoundaryCollision() {
        Point2D currentPosition = getPosition();
        double currentPositionX = currentPosition.getX();
        double currentPositionY = currentPosition.getY();
        if (currentPositionX < 0 || currentPositionX > GameApp.WINDOW_WIDTH ||  currentPositionY < 0 || currentPositionY > GameApp.WINDOW_HEIGHT) {
            gameObjectDestroyer.accept(this);
        }
    }

    public void render(GraphicsContext graphicsContext) {
        drawSprite(graphicsContext);
//        drawHitbox(graphicsContext);
    }

    private void drawSprite(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(sprite, position.getX() - sprite.getWidth() / 2, position.getY() - sprite.getHeight() / 2);
    }

    private void drawHitbox(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillOval(
                position.getX() - hitRadius,
                position.getY() - hitRadius,
                hitRadius * 2,
                hitRadius * 2
        );
    }
}

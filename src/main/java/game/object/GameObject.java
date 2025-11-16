package game.object;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import java.util.Set;

public abstract class GameObject {
    protected Point2D position;
    protected Point2D velocity;
    protected final Image sprite;

    public GameObject(Point2D position, Image sprite) {
        this.position = position;
        this.sprite = sprite;
    }

    public double getWidth() {
        return sprite.getWidth();
    }
    public double getHeight() {
        return sprite.getHeight();
    }

    public Point2D getPosition() {
        return new Point2D(position.getX(), position.getY());
    }

    public void update(double deltaTime, Set<KeyCode> keys) {
        position = position.add(velocity.multiply(deltaTime));
    }

    public void render(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(sprite, position.getX() - sprite.getWidth() / 2, position.getY() - sprite.getHeight() / 2);;
    }
}

package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject {
    protected double x;
    protected double y;
    protected final Image sprite;
    protected double velX = 0;
    protected double velY = 0;

    public GameObject(double x, double y, Image sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void updatePosition(double deltaTime) {
        x += velX * deltaTime;
        y += velY * deltaTime;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(sprite, x, y);
    }
}

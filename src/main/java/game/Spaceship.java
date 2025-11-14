package game;

import javafx.scene.image.Image;

public class Spaceship extends GameObject {
    private static final double DEFAULT_SPEED = 300;

    private double moveSpeed;

    public Spaceship(double x, double y, Image sprite) {
        super(x, y, sprite);
        moveSpeed = DEFAULT_SPEED;
    }

    public void handleInput(boolean up, boolean down, boolean left, boolean right) {
        velX = 0;
        velY = 0;

        if (up)    velY -= moveSpeed;
        if (down)  velY += moveSpeed;
        if (left)  velX -= moveSpeed;
        if (right) velX += moveSpeed;

        if (velX != 0 && velY != 0) {
            velX *= 0.707;
            velY *= 0.707;
        }
    }

}

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

    public static final double DEFAULT_INVINCIBILITY_TIME = 0.3;
    private static final double COLLISION_KNOCKBACK = 2;

    private double hitRadius;
    private double invincibilityTime;
    private Point2D position;
    private Point2D velocity = Point2D.ZERO;
    private Image sprite;
    private Consumer<GameObject> gameObjectDestroyer;

    private double invincibilityCooldown = 0;

    public GameObject(Point2D position, Image sprite) {
        setPosition(position);
        setSprite(sprite);

        setHitRadius(sprite.getWidth() / 2 * 0.3);
        setInvincibilityTime(DEFAULT_INVINCIBILITY_TIME);
    }

    public Point2D getPosition() {
        return position;
    }

    protected void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    protected void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Image getSprite() {
        return sprite;
    }

    protected void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public double getHitRadius() {
        return hitRadius;
    }

    protected void setHitRadius(double hitRadius) {
        this.hitRadius = hitRadius;
    }

    public double getInvincibilityTime() {
        return invincibilityTime;
    }

    protected void setInvincibilityTime(double invincibilityTime) {
        this.invincibilityTime = invincibilityTime;
    }

    public double getInvincibilityCooldown() {
        return invincibilityCooldown;
    }

    protected void setInvincibilityCooldown(double invincibilityCooldown) {
        this.invincibilityCooldown = invincibilityCooldown;
    }

    public Consumer<GameObject> getGameObjectDestroyer() {
        return gameObjectDestroyer;
    }

    public void setGameObjectDestroyer(Consumer<GameObject> gameObjectDestroyer) {
        this.gameObjectDestroyer = gameObjectDestroyer;
    }

    public double getWidth() {
        return sprite.getWidth();
    }

    public double getHeight() {
        return sprite.getHeight();
    }

    public abstract void onCollision(GameObject otherObject);

    protected void handleCollisionWithKnockback(GameObject otherObject) {
        Point2D otherPos = otherObject.getPosition();
        Point2D myPos = getPosition();

        Point2D deflection = myPos.subtract(otherPos);
        setPosition(myPos.add(deflection.multiply(COLLISION_KNOCKBACK)));
    }

    public void update(double deltaTime, Set<KeyCode> keys) {

        setInvincibilityCooldown(getInvincibilityCooldown() - deltaTime);

        setPosition(getPosition().add(getVelocity().multiply(deltaTime)));

        checkAndResolveWorldBoundaryCollision();
    }

    protected void checkAndResolveWorldBoundaryCollision() {
        Point2D currentPosition = getPosition();
        double x = currentPosition.getX();
        double y = currentPosition.getY();

        if (x < 0 || x > GameApp.WINDOW_WIDTH || y < 0 || y > GameApp.WINDOW_HEIGHT) {
            getGameObjectDestroyer().accept(this);
        }
    }

    public void render(GraphicsContext graphicsContext) {
        drawSprite(graphicsContext);
        // drawHitbox(gc);
    }

    private void drawSprite(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(
                getSprite(),
                getPosition().getX() - getWidth() / 2,
                getPosition().getY() - getHeight() / 2
        );
    }

    /* COMMENTED FOR TEST COVERAGE, UNCOMMENT FOR DEBUGGING USE */

    /*
    private void drawHitbox(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(
                getPosition().getX() - getHitRadius(),
                getPosition().getY() - getHitRadius(),
                getHitRadius() * 2,
                getHitRadius() * 2
        );
    }
    */
}

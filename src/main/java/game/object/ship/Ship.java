package game.object.ship;

import game.object.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

public abstract class Ship extends GameObject {
    protected Consumer<GameObject> gameObjectCreator;
    protected double shotCooldown;
    protected double shotCooldownTimer = 0;
    protected double moveSpeed;
    protected double health;
    protected double maxHealth;

    public Ship(Point2D position, Image sprite, Consumer<GameObject> gameObjectCreator, double health, double moveSpeed, double shotCooldown) {
        super(position, sprite);
        setHealth(health);
        setMaxHealth(health);
        setMoveSpeed(moveSpeed);
        setShotCooldown(shotCooldown);
        setGameObjectCreator(gameObjectCreator);
    }

    abstract void attemptShot();

    protected void handleDeath() {
        getGameObjectDestroyer().accept(this);
    }

    public void takeDamage(double damage) {
        double newHealth = getHealth() - damage;
        if (newHealth <= 0) {
            setHealth(0);
            handleDeath();
        } else {
            setHealth(newHealth);
        }
    }

    private void renderHealthBar(GraphicsContext graphicsContext) {
        double Y_OFFSET = 32;
        double HEALTH_BAR_HEIGHT = 4;

        double y = getPosition().getY() + getSprite().getHeight() - Y_OFFSET;
        double healthBarWidth = getSprite().getWidth() * .8;
        double x = getPosition().getX() - healthBarWidth / 2;

        double ratio = getHealth() / getMaxHealth();

        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(x, y, healthBarWidth, HEALTH_BAR_HEIGHT);

        graphicsContext.setFill(Color.LIMEGREEN);
        graphicsContext.fillRect(x, y, healthBarWidth * ratio, HEALTH_BAR_HEIGHT);
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
        renderHealthBar(graphicsContext);
        super.render(graphicsContext);
    }

    protected void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    protected void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    protected void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    protected void setShotCooldown(double shotCooldown) {
        this.shotCooldown = shotCooldown;
    }

    public double getShotCooldownTimer() {
        return shotCooldownTimer;
    }

    protected void setShotCooldownTimer(double shotCooldownTimer) {
        this.shotCooldownTimer = shotCooldownTimer;
    }

    public double getShotCooldown() {
        return shotCooldown;
    }

    public Consumer<GameObject> getGameObjectCreator() {
        return gameObjectCreator;
    }

    protected void setGameObjectCreator(Consumer<GameObject> gameObjectCreator) {
        this.gameObjectCreator = gameObjectCreator;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }
}

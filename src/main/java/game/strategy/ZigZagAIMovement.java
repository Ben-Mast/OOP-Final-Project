package game.strategy;

import game.object.ship.Ship;
import javafx.geometry.Point2D;

public class ZigZagAIMovement implements AIMovementStrategy {
    Ship ship;
    private final double timeBetweenChangingDirections = 4.0;
    private double directionChangeTimer = timeBetweenChangingDirections;
    private double currentXMultiplier = -1;

    public ZigZagAIMovement(Ship ship) {
        this.ship = ship;
    }

    public Point2D updateVelocity (double deltaTime) {
        directionChangeTimer -= deltaTime;
        if (directionChangeTimer <= 0) {
            directionChangeTimer = timeBetweenChangingDirections;
            currentXMultiplier *= -1;
        }
        return new Point2D(ship.getMoveSpeed() / 1.414 * currentXMultiplier, ship.getMoveSpeed() / 1.414);
    }
}

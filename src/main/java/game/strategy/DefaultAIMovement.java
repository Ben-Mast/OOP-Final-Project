package game.strategy;

import game.object.ship.Ship;
import javafx.geometry.Point2D;

public class DefaultAIMovement implements AIMovementStrategy {
    Ship ship;

    public DefaultAIMovement(Ship ship) {
        this.ship = ship;
    }

    public Point2D updateVelocity (double deltaTime) {
        return new Point2D(0, ship.getMoveSpeed());
    }
}

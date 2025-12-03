package game.strategy;

import javafx.geometry.Point2D;

public interface AIMovementStrategy {
    Point2D updateVelocity (double deltaTime);
}

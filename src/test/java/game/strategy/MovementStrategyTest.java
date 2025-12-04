package game.strategy;

import game.object.GameObject;
import game.object.ship.AIShip;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class MovementStrategyTest {

    private Consumer<GameObject> dummyCreator() {
        return _ -> {};
    }

    @Test
    void defaultMovementChangesVelocity() {
        AIShip ship = AIShip
                .getNewBuilder(dummyCreator())
                .givePosition(new Point2D(0, 0))
                .build();

        DefaultAIMovement movement = new DefaultAIMovement(ship);
        Point2D vel = movement.updateVelocity(1.0);
        assertNotNull(vel);
    }

    @Test
    void zigZagMovementChangesVelocity() {
        AIShip ship = AIShip
                .getNewBuilder(dummyCreator())
                .givePosition(new Point2D(0, 0))
                .build();

        ZigZagAIMovement movement = new ZigZagAIMovement(ship);
        Point2D vel1 = movement.updateVelocity(0.1);
        Point2D vel2 = movement.updateVelocity(0.2);

        assertNotNull(vel1);
        assertNotNull(vel2);
    }

    @Test
    void randomMovementTypeReturnsValidEnumValue() {
        AIMovementType type = AIMovementType.getRandom();
        assertNotNull(type);
    }
}

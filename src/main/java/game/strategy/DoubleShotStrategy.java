package game.strategy;

import game.object.GameObject;
import game.object.PlayerSpaceship;
import game.object.Projectile;
import javafx.geometry.Point2D;

import java.util.function.Consumer;

public class DoubleShotStrategy implements ShootingStrategy{
    @Override
    public void shoot(PlayerSpaceship ship, Consumer<GameObject> gameObjectCreatorFunction) {
        Point2D centerPosition = ship.getPosition();
        Point2D xOffset = new Point2D(ship.getWidth()/4, 0);

        gameObjectCreatorFunction.accept(new Projectile(centerPosition.add(xOffset)));
        gameObjectCreatorFunction.accept(new Projectile(centerPosition.subtract(xOffset)));
    }
}

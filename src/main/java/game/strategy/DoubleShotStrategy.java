package game.strategy;

import game.object.PlayerSpaceship;
import game.object.Projectile;
import javafx.geometry.Point2D;

import java.util.function.Consumer;

public class DoubleShotStrategy implements ShootingStrategy{
    @Override
    public void shoot(PlayerSpaceship ship, Consumer<Projectile> projectileCreatorFunction) {
        Point2D centerPosition = ship.getPosition();
        Point2D xOffset = new Point2D(ship.getWidth()/4, 0);

        projectileCreatorFunction.accept(new Projectile(centerPosition.add(xOffset)));
        projectileCreatorFunction.accept(new Projectile(centerPosition.subtract(xOffset)));
    }
}

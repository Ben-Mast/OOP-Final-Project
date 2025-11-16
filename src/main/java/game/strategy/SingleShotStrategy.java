package game.strategy;

import game.object.PlayerSpaceship;
import game.object.Projectile;

import java.util.function.Consumer;

public class SingleShotStrategy implements ShootingStrategy{
    @Override
    public void shoot(PlayerSpaceship ship, Consumer<Projectile> projectileCreatorFunction) {
        projectileCreatorFunction.accept(new Projectile(ship.getPosition().subtract(0,ship.getHeight()/2)));
    }
}

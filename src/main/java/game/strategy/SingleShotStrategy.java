package game.strategy;

import game.object.GameObject;
import game.object.PlayerSpaceship;
import game.object.Projectile;

import java.util.function.Consumer;

public class SingleShotStrategy implements ShootingStrategy{
    @Override
    public void shoot(PlayerSpaceship ship, Consumer<GameObject> gameObjectCreatorFunction) {
        gameObjectCreatorFunction.accept(new Projectile(ship.getPosition().subtract(0,ship.getHeight()/2)));
    }
}

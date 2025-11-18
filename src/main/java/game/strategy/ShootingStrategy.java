package game.strategy;

import game.object.GameObject;
import game.object.PlayerSpaceship;
import game.object.Projectile;

import java.util.function.Consumer;

public interface ShootingStrategy {
    void shoot(PlayerSpaceship ship, Consumer<GameObject> gameObjectCreatorFunction);
}

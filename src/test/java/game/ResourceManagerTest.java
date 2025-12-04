package game;

import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceManagerTest {

    @Test
    void singletonReturnsSameInstance() {
        ResourceManager r1 = ResourceManager.getInstance();
        ResourceManager r2 = ResourceManager.getInstance();
        assertSame(r1, r2);
    }

    @Test
    void allSpritesLoadSuccessfully() {
        ResourceManager rm = ResourceManager.getInstance();

        Image player = rm.getPlayerShipSprite();
        Image enemy = rm.getEnemyShipSprite();
        Image big = rm.getAsteroidSpriteBig();
        Image med = rm.getAsteroidSpriteMedium();
        Image small = rm.getAsteroidSpriteSmall();
        Image friendly = rm.getFriendlyProjectileSprite();
        Image enemyProj = rm.getEnemyProjectileSprite();

        assertNotNull(player);
        assertNotNull(enemy);
        assertNotNull(big);
        assertNotNull(med);
        assertNotNull(small);
        assertNotNull(friendly);
        assertNotNull(enemyProj);
    }
}


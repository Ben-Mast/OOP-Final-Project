package game;

import javafx.scene.image.Image;
import java.util.Objects;

public class ResourceManager {

    private static final ResourceManager instance = new ResourceManager();

    private final Image playerShipSprite;
    private final Image enemyShipSprite;
    private final Image asteroidSpriteBig;
    private final Image asteroidSpriteMedium;
    private final Image asteroidSpriteSmall;
    private final Image friendlyProjectileSprite;
    private final Image enemyProjectileSprite;

    private ResourceManager() {
        playerShipSprite = load("player_ship.png");
        enemyShipSprite = load("enemy_ship.png");

        asteroidSpriteBig = load("asteroid_big.png");
        asteroidSpriteMedium = load("asteroid_medium.png");
        asteroidSpriteSmall = load("asteroid_small.png");

        friendlyProjectileSprite = load("player_shot.png");
        enemyProjectileSprite = load("player_shot.png");
    }

    private Image load(String filename) {
        return new Image(Objects.requireNonNull(
                ResourceManager.class.getClassLoader().getResourceAsStream(filename),
                "Missing resource: " + filename
        ));
    }

    public static ResourceManager getInstance() {
        return instance;
    }

    public Image getPlayerShipSprite() {
        return playerShipSprite;
    }

    public Image getEnemyShipSprite() {
        return enemyShipSprite;
    }

    public Image getAsteroidSpriteBig() {
        return asteroidSpriteBig;
    }

    public Image getAsteroidSpriteMedium() {
        return asteroidSpriteMedium;
    }

    public Image getAsteroidSpriteSmall() {
        return asteroidSpriteSmall;
    }

    public Image getFriendlyProjectileSprite() {
        return friendlyProjectileSprite;
    }

    public Image getEnemyProjectileSprite() {
        return enemyProjectileSprite;
    }
}

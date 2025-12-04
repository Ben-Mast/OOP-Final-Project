package game.state;

import game.GameApp;
import game.GameWorld;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Set;

public class PausedGameState extends GameState {

    boolean escWasDown = true;

    public PausedGameState(GameWorld gameWorld) {
        super(gameWorld);
    }

    @Override
    public void update(double deltaTime, Set<KeyCode> keyCodes) {

        boolean escDown = keyCodes.contains(KeyCode.ESCAPE);

        if (escDown && !escWasDown) {
            gameWorld.updateGameState(State.RUNNING);
        }
        escWasDown = escDown;
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
        gameWorld.renderWorld(graphicsContext);

        graphicsContext.setFill(Color.color(0, 0, 0, 0.5));
        graphicsContext.fillRect(0, 0, GameApp.WINDOW_WIDTH, GameApp.WINDOW_HEIGHT);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.font("Arial", FontWeight.BOLD, 48));

        String pausedText = "PAUSED";
        double textWidth = computeTextWidth(graphicsContext.getFont(), pausedText);
        graphicsContext.fillText(pausedText, (GameApp.WINDOW_WIDTH - textWidth) / 2, GameApp.WINDOW_HEIGHT / 2.0);

        graphicsContext.setFont(Font.font("Arial", 24));
        String resumeText = "Press Esc to Resume";
        double resumeWidth = computeTextWidth(graphicsContext.getFont(), resumeText);
        graphicsContext.fillText(resumeText, (GameApp.WINDOW_WIDTH - resumeWidth) / 2, GameApp.WINDOW_HEIGHT / 2.0 + 40);
    }

    private double computeTextWidth(Font font, String text) {
        Text temp = new Text(text);
        temp.setFont(font);
        return temp.getLayoutBounds().getWidth();
    }
}

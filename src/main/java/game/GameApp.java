package game;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

import java.util.HashSet;
import java.util.Set;

public class GameApp extends Application {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Scene scene = new Scene(new StackPane(canvas));

        Set<KeyCode> keys = new HashSet<>();
        scene.setOnKeyPressed(e -> keys.add(e.getCode()));
        scene.setOnKeyReleased(e -> keys.remove(e.getCode()));

        stage.setScene(scene);
        stage.setTitle("Asteroid Assault");
        stage.show();

        GameWorld gameWorld = new GameWorld();
        GameLoop gameLoop = new GameLoop(graphicsContext, keys, gameWorld);

        gameLoop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
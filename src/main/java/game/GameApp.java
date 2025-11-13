package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

public class GameApp extends Application {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final Color BACKGROUND_COLOR = Color.rgb(9, 21, 46, 1);

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(new StackPane(canvas));

        stage.setScene(scene);
        stage.setTitle("Asteroid Assault");
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
                gc.setFill(BACKGROUND_COLOR);
                gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            }
        };

        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
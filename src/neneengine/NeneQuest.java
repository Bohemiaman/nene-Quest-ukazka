package neneengine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nenelogic.GameController;
import nenelogic.Menu;

/**
 *
 * @author Martin Joukl
 */
public class NeneQuest extends Application {

    Pane root;
    Color backgroundColor = Color.WHITE;
    Canvas mainCanvas;
    Scene scene;
    GameController gameController;

    private final double HEIGHT = 1080;
    private final double WIDTH = 1920;

    Menu mainMenu;
    MenuRender menuRender;

    private List<KeyCode> pressedKeys;

    private static long currentTime;
    private static long deltaTime = 0;
    private static final long TARGET_FRAME_DURATION = 16666666;
    private double playerSpeed = 6;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        root = new Pane();
        root.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));

        mainCanvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(mainCanvas);

        pressedKeys = new ArrayList<>();

        mainMenu = new Menu("Jeden hráč", "Dva hráči", "Konec");

        menuRender = new MenuRender(mainMenu);

        gameController = new GameController(mainCanvas);

        clearCanvas();
        menuRender.render(mainCanvas);

        scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(selectFromMenu);
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("NeneQuest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void clearCanvas() {
        mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }
    EventHandler<KeyEvent> selectFromMenu = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case UP:
                    mainMenu.downIndex();
                    break;
                case DOWN:
                    mainMenu.upIndex();
                    break;
                case ENTER:
                    switch (mainMenu.getSelectedIndex()) {
                        case 0:
                            mainMenu = null;
                            menuRender = null;
                            scene.setOnKeyPressed(inputIntoGamePressed);
                            scene.setOnKeyReleased(inputIntoGameRelased);
                            clearCanvas();
                            gameController.render();
                            currentTime = System.nanoTime();
                            animationTimer.start();

                            return;
                        case 1:
                            break;
                        case 2:
                            Platform.exit();
                            break;
                    }
                    break;
            }
            clearCanvas();
            menuRender.render(mainCanvas);
        }
    };

    EventHandler<KeyEvent> inputIntoGamePressed = (event) -> {
        switch (event.getCode()) {
            case UP:
                //   gameController.getPlayer().setVelocityByAngle(90, 3);
                pressedKeys.remove(KeyCode.UP);
                pressedKeys.add(KeyCode.UP);
                break;
            case DOWN:
                //  gameController.getPlayer().setVelocityByAngle(270, 3);
                pressedKeys.remove(KeyCode.DOWN);
                pressedKeys.add(KeyCode.DOWN);
                break;
            case RIGHT:
                //    gameController.getPlayer().setVelocityByAngle(0, 3);
                pressedKeys.remove(KeyCode.RIGHT);
                pressedKeys.add(KeyCode.RIGHT);
                break;
            case LEFT:
                // gameController.getPlayer().setVelocityByAngle(180, 3);
                pressedKeys.remove(KeyCode.LEFT);
                pressedKeys.add(KeyCode.LEFT);
                break;
        }
        updateKeyPressed();
    };

    EventHandler<KeyEvent> inputIntoGameRelased = (event) -> {
        switch (event.getCode()) {
            case UP:
                //   gameController.getPlayer().setVelocityByAngle(90, 3);
                pressedKeys.remove(KeyCode.UP);
                break;
            case DOWN:
                //  gameController.getPlayer().setVelocityByAngle(270, 3);
                pressedKeys.remove(KeyCode.DOWN);
                break;
            case RIGHT:
                //    gameController.getPlayer().setVelocityByAngle(0, 3);
                pressedKeys.remove(KeyCode.RIGHT);
                break;
            case LEFT:
                // gameController.getPlayer().setVelocityByAngle(180, 3);
                pressedKeys.remove(KeyCode.LEFT);
                break;
        }
        updateKeyPressed();
    };

    private void updateKeyPressed() {
        if (pressedKeys.isEmpty()) {
            gameController.getPlayer().setVelocity(0, 0);
            return;
        }
        double angle = 0;
        if (pressedKeys.contains(KeyCode.UP)) {
            angle += 90;
        }
        if (pressedKeys.contains(KeyCode.DOWN)) {
            angle += 270;
            if (pressedKeys.contains(KeyCode.RIGHT)) {
                angle += 360;
            }
        }
        if (pressedKeys.contains(KeyCode.LEFT)) {
            angle += 180;
        }
        if (pressedKeys.contains(KeyCode.RIGHT)) {
            angle += 0;
        }
        if (pressedKeys.contains(KeyCode.RIGHT) && pressedKeys.contains(KeyCode.LEFT)) {
            angle -= 180;
        }
        if (pressedKeys.contains(KeyCode.UP) && pressedKeys.contains(KeyCode.DOWN)) {
            angle -= 360;
        }
        if (angle == 0 && pressedKeys.size() > 1) {
            gameController.getPlayer().setVelocity(0, 0);
            return;
        }
       System.out.println(gameController.getPlayer().getHitbox().getX() + gameController.getCamera().getDx());

        angle = angle / pressedKeys.size();
        gameController.getPlayer().setVelocityByAngle(angle, playerSpeed);
    }

    AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            deltaTime += now - currentTime;
            currentTime = now;
            while (deltaTime > TARGET_FRAME_DURATION) {
                clearCanvas();
                gameController.updatePosition();
                gameController.render();

                deltaTime -= TARGET_FRAME_DURATION;
            }
        }
    };
}

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class DotClick extends Application {

    private Circle target;
    private Timeline timeline;
    private Label resultLabel;
    private Button startButton;
    private Random random = new Random();

    private int hits = 0;
    private int misses = 0;
    private boolean clicked = false;
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();

        // Set up the target circle
        target = new Circle(50, Color.RED);
        target.setVisible(false);
        root.getChildren().add(target);

        // Set up the result label
        resultLabel = new Label("Hits: 0   Misses: 0");
        resultLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        resultLabel.setAlignment(Pos.CENTER);
        root.setBottom(resultLabel);

        // Set up the start button
        startButton = new Button("Start");
        startButton.setOnAction(event -> {
            startButton.setVisible(false);
            startGame();
        });
        root.setCenter(startButton);

        // Set up the scene
        Scene scene = new Scene(root, 900, 800);
        clicked = false;


        // Set up the mouse event handler to detect hits on the target
        scene.setOnMouseClicked(e -> {
            if (target.isVisible() && target.contains(e.getX(), e.getY())) {
                clicked = true;
                hits++;
                updateResultLabel();
            }
        });

        // Show the scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startGame() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    // Move the target to a random location within the scene bounds
                    double x = random.nextDouble() * (target.getRadius() * 2) + target.getRadius();
                    double y = random.nextDouble() * (target.getRadius() * 2) + target.getRadius();
                    target.setCenterX(x);
                    target.setCenterY(y);
                    target.setVisible(true);
                }),
                new KeyFrame(Duration.seconds(2), e -> {
                    target.setVisible(false);
                    if (!clicked){
                        misses++;
                        updateResultLabel();
                    }
                    clicked = false;
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateResultLabel() {
        resultLabel.setText("Hits: " + hits + "   Misses: " + misses);
        if (misses==1){

            timeline.stop();
//            root.getChildren().clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention!");
            alert.setHeaderText(null);
            alert.setContentText("Game Over! Your score is: " + hits);
            alert.show();
        }
    }

    public Pane getRoot() {
        return root;
    }

    public void injectBackButton(Button back) {
        root.getChildren().add(0, back);
    }
}

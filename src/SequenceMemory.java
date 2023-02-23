import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SequenceMemory extends Application {

    private static final int NUM_BUTTONS = 4;
    private static final int NUM_ROUNDS = 5;

    private final List<Button> buttonList = new ArrayList<>();
    private final List<Integer> sequence = new ArrayList<>();

    private int currentRound = 0;
    private int currentStep = 0;

    private final Pane pane = new Pane();

    @Override
    public void start(Stage primaryStage) {
        // Create the buttons and add them to the pane
        for (int i = 1; i <= NUM_BUTTONS; i++) {
            Button button = new Button(String.valueOf(i));
            button.setPrefSize(100, 100);
            button.setFont(Font.font(24));
            buttonList.add(button);
        }
        Collections.shuffle(buttonList);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < NUM_BUTTONS; i++) {
            Button button = buttonList.get(i);
            gridPane.add(button, i % 2, i / 2);
        }
        pane.getChildren().add(gridPane);

        // Create the start button and add it to the pane
        Button startButton = new Button("Start");
        startButton.setPrefSize(100, 50);
        startButton.setFont(Font.font(18));
        startButton.setLayoutX(150);
        startButton.setLayoutY(220);
        startButton.setOnAction(event -> startGame());
        pane.getChildren().add(startButton);

        Scene scene = new Scene(pane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sequence Memory Game");
        primaryStage.show();
    }

    private void startGame() {
        currentRound = 1;
        currentStep = 0;
        sequence.clear();
        displaySequence();
    }

    private void displaySequence() {
        for (Button b : buttonList) {
            b.setDisable(true);
        }
        if (currentRound <= NUM_ROUNDS) {
            // Generate a new random step and add it to the sequence
            int nextStep = (int) (Math.random() * NUM_BUTTONS);
            sequence.add(nextStep);
            // Display the sequence to the user
            new Thread(() -> {
                try {
                    for (int i : sequence) {
                        Thread.sleep(1000);
                        Button button = buttonList.get(i);
                        button.setStyle("-fx-background-color: #00FF00;");
                        button.setText(String.valueOf(i + 1));
                        Thread.sleep(500);
                        button.setStyle("");
                        button.setText("");
                    }
                    for (Button b : buttonList) {
                        b.setDisable(false);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            // End of game
            Text message = new Text("Game Over!");
            message.setFont(Font.font(30));
            message.setLayoutX(130);
            message.setLayoutY(120);
            pane.getChildren().add(message);
        }
    }

    private void handleButtonClick(Button button) {
        int buttonIndex = buttonList.indexOf(button);
        if (buttonIndex == sequence.get(currentStep)) {
            currentStep++;
            if (currentStep == currentRound) {
                currentRound++;
                currentStep = 0;
                displaySequence();
            }
        } else {
// End of game
            Text message = new Text("Game Over!");
            message.setFont(Font.font(30));
            message.setLayoutX(130);
            message.setLayoutY(120);
            pane.getChildren().add(message);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

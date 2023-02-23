import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberMemory {
    private final Pane root;
    private final Pane controls;
    private final StackPane gamePane;
    private final Button start;

    private IntegerProperty levelProperty;
    private int level;
    private List<Integer> numbers;
    private int currentNumberIndex;
    private boolean userIsGuessing;
    private long startTime;
    private Label statusLabel;
    private Label levelLabel;

    public NumberMemory() {
        root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.rgb(52, 152, 219), CornerRadii.EMPTY, null)));

        controls = new HBox(10);
        ((HBox) controls).setAlignment(Pos.CENTER);
        start = new Button("Start");
        start.setOnAction(this::start);
        controls.getChildren().addAll(start);

        gamePane = new StackPane();
        gamePane.setAlignment(Pos.CENTER);

        root.getChildren().addAll(gamePane, controls);

        levelProperty = new SimpleIntegerProperty(0);
        levelLabel = new Label();
        levelLabel.setFont(new javafx.scene.text.Font(40));
        levelLabel.setTextFill(Color.WHITE);
        levelLabel.textProperty().bind(levelProperty.add(1).asString("Level %d"));
    }

    private void start(ActionEvent actionEvent) {
        level = 0;
        numbers = new ArrayList<>();
        levelProperty.set(level);

        statusLabel = new Label();
        statusLabel.setFont(new javafx.scene.text.Font(30));
        statusLabel.setTextFill(Color.WHITE);
        gamePane.getChildren().setAll(statusLabel);

        userIsGuessing = false;
        currentNumberIndex = 0;

        startTime = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_500_000_000) {
                    if (userIsGuessing) {
                        if (currentNumberIndex == numbers.size()) {
                            statusLabel.setText("You got it!");
                            statusLabel.setTextFill(Color.GREEN);
                            level++;
                            levelProperty.set(level);
                            startNewRound();
                            userIsGuessing = false;
                        }
                    } else {
                        if (statusLabel.getText().equals("")) {
                            showNextNumber();
                        } else {
                            statusLabel.setText("");
                        }
                    }
                    lastUpdate = now;
                }
            }
        };
        timer.start();

        start.setVisible(false);
    }

    private void showNextNumber() {
        int newNumber = new Random().nextInt(10);
        numbers.add(newNumber);
        statusLabel.setText(String.valueOf(newNumber));
        currentNumberIndex = 0;
        userIsGuessing = true;
    }

    private void startNewRound() {
        numbers.clear();
        currentNumberIndex = 0;
        showNextNumber();
    }

    public Pane getRoot() {
        return root;
    }

    public void injectBackButton(Button back) {
        controls.getChildren().add(0, back);
    }
}

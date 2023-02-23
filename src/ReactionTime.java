import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ReactionTime {

    //Initializing the root variable along with other variables
    private final Pane root;
    private final Random rand;
    private LongProperty reactionTimeValue;
    private long startTime;
    private int numTries;
    private Queue<Long> scores;
    private Timeline timer;
    private boolean isGreen;

    /**
     * Constructor to initialize the game components like title and the layout of the game
     */
    public ReactionTime() {
        root = new VBox(10);
        root.setStyle("-fx-background-color: red");
        Label startLabel = new Label("Click anywhere to start!");
        startLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: white;" +
                " -fx-text-alignment: center;");
        rand = new Random();
        reactionTimeValue = new SimpleLongProperty(0);
        numTries = 0;
        scores = new LinkedList<>();
        timer = new Timeline();
        isGreen = false;

        root.getChildren().addAll(startLabel);
        root.setOnMouseClicked(event -> startTimer(null));
    }



    public void startTimer(ActionEvent actionEvent) {

        if (numTries == 0){root.getChildren().remove(1);}
        else if (numTries%5 == 0) {
            numTries -= 1;
            displayResults();
            return;
        }
        reactionTimeValue.setValue(0);

        int delay = rand.nextInt(4000) + 1000;
        timer.getKeyFrames().clear();
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> {
            root.setStyle("-fx-background-color: green");
            Label clickNow = new Label("Click now!");
            clickNow.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-alignment: center");
            root.getChildren().add(clickNow);
            isGreen = true;
            root.setOnMouseClicked(event1 -> {
                if (!isGreen) {
                    Label tooEarly = new Label("Too early!");
                    tooEarly.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-alignment: center");
                    root.getChildren().add(tooEarly);
                    timer.stop();
                    root.setOnMouseClicked(restartEvent -> {
                        root.setStyle("-fx-background-color: red");
                        root.getChildren().removeAll(clickNow, tooEarly);
                        isGreen = false;
                        numTries = 0;
                        scores.clear();
                        startTimer(null);
                    });
                } else if (isGreen) {  // Add this check to make sure background is green before proceeding
                    root.getChildren().remove(clickNow);
                    long finishTime = System.nanoTime();
                    long reactionTimeNano = finishTime - startTime;
                    long milliValue = TimeUnit.NANOSECONDS.toMillis(reactionTimeNano);
                    reactionTimeValue.setValue(milliValue);
                    root.setStyle("-fx-background-color: #49d5ec");
                    Label scoreLabel = new Label(milliValue + " ms\nClick to keep going");
                    scoreLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
                    root.getChildren().add(scoreLabel);
                    root.setOnMouseClicked(restartEvent -> {
                        root.setStyle("-fx-background-color: red");
                        root.getChildren().remove(scoreLabel);
                        scores.add(milliValue);
                        numTries++;
                        startTimer(null);
                    });
                }
            });

            startTime = System.nanoTime();
        }));
        timer.play();
    }


    private void displayResults() {
        timer.stop();
        long sum = 0;
        for (Long score : scores) {
            sum += score;
        }
        double average = (double) sum / scores.size();
        Label resultsLabel = new Label("Average reaction time: " + String.format("%.2f", average) + " ms");
        resultsLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-alignment: center;" +
                " -fx-text-fill: white");
        root.getChildren().remove(resultsLabel);
        root.setStyle("-fx-background-color: #41e0e5");
        root.getChildren().add(resultsLabel);
    }

    public Pane getRoot() {
        return root;
    }

    public void injectBackButton(Button back) {
        root.getChildren().add(0, back);
    }


}

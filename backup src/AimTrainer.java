import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AimTrainer {

    private final Pane root;
    private final Pane controls;
    private final Button start;
    private final Label scoreLabel;
    private final int numTargets;

    private IntegerProperty targetsRemaining;
    private boolean stop = false;
    private int targetsHit = 0;
    private List<Double> hitTimes = new ArrayList<>();
    private long startTime;
    private Color targetColor = Color.BLUE;

    public AimTrainer(int numTargets) {
        this.numTargets = numTargets;
        root = new Pane();
        controls = new HBox(10);
        start = new Button("Start");
        start.setOnAction(this::start);
        Label targetsRemainingLabel = new Label("Remaining:");
        scoreLabel = new Label("");
        controls.getChildren().addAll(targetsRemainingLabel, start, scoreLabel);

        root.getChildren().add(controls);

        targetsRemaining = new SimpleIntegerProperty(numTargets);
        targetsRemainingLabel.textProperty().bind(targetsRemaining.asString());
        root.setStyle("-fx-background-color: #18b8e8;");
    }

    private void hitTarget(Circle target) {
        hitTimes.add((System.nanoTime() - startTime) / 1e9);
        targetsRemaining.setValue(targetsRemaining.get() - 1);
        targetsHit++;
        root.getChildren().remove(target);

        if (targetsRemaining.get() <= 0) {
            stop = true;
            showScore();
        }
    }


    private Pair<Double, Double> getRandXY(double lowerBound, double radius) {
        Random rand = new Random();
        double randX = rand.nextDouble(lowerBound + radius, root.getWidth() - radius);
        double randY = rand.nextDouble(lowerBound + radius, root.getHeight() - radius);
        return new Pair<>(randX, randY);
    }

    private void createTarget(double radius) {
        if (targetsRemaining.get() <= 0 || stop) {
            return;
        }

        Pair<Double, Double> xy = getRandXY(radius, radius);
        Circle target = new Circle(xy.getKey(), xy.getValue(), radius, targetColor);
        target.setStroke(Color.BLACK);
        root.getChildren().add(target);

        target.setOnMouseClicked(event -> {
            hitTarget(target);
            if (!stop) {
                createTarget(radius);
            }
        });

        targetColor = targetColor.darker(); // change target color for next target
    }

    public void start(ActionEvent actionEvent) {
        reset(); // reset targets hit count and hit times

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (targetsRemaining.get() <= 0 || stop) {
                    stop = true;
                    this.stop();
                    showScore();
                } else if (root.getChildren().size() < numTargets) {
                    createTarget(25);
                }
            }
        };
        timer.start();

        start.setVisible(false);
        createTarget(25);
    }

    private void reset() {
        targetsRemaining.setValue(numTargets);
        stop = false;
        targetsHit = 0;
        hitTimes.clear();
        scoreLabel.setVisible(false);
        root.getChildren().removeIf(node -> node instanceof Circle); // remove all targets
        targetColor = Color.BLUE;
    }


    private void showScore() {
        if (hitTimes.isEmpty()) {
            scoreLabel.setText("No targets hit");
            return;
        }

        double totalTime = 0;
        for (double hitTime : hitTimes) {
            totalTime += hitTime;
        }
        double avgTime = totalTime / hitTimes.size();
        double score = Math.round(1000 / avgTime * targetsHit / numTargets);
        scoreLabel.setText(String.format("Score: %.1f", score));
        scoreLabel.setVisible(true);
        start.setText("Restart");

    }


    public Pane getRoot() {
        return root;
    }

    public void injectBackButton(Button back) {
        controls.getChildren().add(0, back);
    }
}
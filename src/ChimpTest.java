import javafx.animation.AnimationTimer;
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

public class ChimpTest {
    private final Pane root;
    private final Pane controls;
    private final Button start;
    private Label levelLabel;
    private Label strikeLabel;

    private int level = 1;
    private int strikes = 0;
    private boolean stop = false;

    private long startTime;

    private List<Integer> sequence;



    public ChimpTest() {
        root = new Pane();
        controls = new HBox(10);
        start = new Button("Start");
        start.setOnAction(this::start);
        levelLabel = new Label("Level: 1");
        strikeLabel = new Label("Strikes: 0");
        controls.getChildren().addAll(levelLabel, strikeLabel, start);

        root.getChildren().add(controls);
    }

    private void levelUp() {
        level++;
        levelLabel.setText("Level: " + level);

        sequence = generateSequence(level);
        displaySequence(sequence);
    }


    private void strike() {
        strikes++;
        strikeLabel.setText("Strikes: " + strikes);
        if (strikes >= 3) {
            stop = true;
            end();
        }
    }

    private void hitTarget(int num) {
        if (num != sequence.get(0)) {
            strike();
        } else {
            sequence.remove(0);
            if (sequence.isEmpty()) {
                levelUp();
            }
        }
    }

    private void displaySequence(List<Integer> sequence) {
        double x = root.getWidth() / 2 - 15 * sequence.size();
        double y = root.getHeight() / 2 - 20;

        for (int i = 0; i < sequence.size(); i++) {
            int num = sequence.get(i);
            Label label = new Label(String.valueOf(num));
            label.setLayoutX(x + 30 * i);
            label.setLayoutY(y);
            root.getChildren().add(label);
        }
    }

    private Pair<Double, Double> getRandXY(double lowerBound) {
        Random rand = new Random();
        double randX = rand.nextDouble(lowerBound, root.getWidth() - lowerBound);
        double randY = rand.nextDouble(lowerBound, root.getHeight() - lowerBound);
        return new Pair<>(randX, randY);
    }

    private void createTarget(double radius) {
        Pair<Double, Double> xy = getRandXY(radius);

        Circle target = new Circle(xy.getKey(), xy.getValue(), radius, Color.RED);
        target.setId(String.valueOf(sequence.get(0)));
        root.getChildren().add(target);

        target.setOnMouseClicked(event -> {
            hitTarget(Integer.parseInt(target.getId()));
            root.getChildren().remove(target);
            if (!stop) {
                createTarget(radius);
            }
        });
    }


    private void end() {
        long endTime = System.nanoTime();
        long timeTaken = (endTime - startTime) / 1_000_000; // convert to milliseconds
        double score = level * 100.0 / timeTaken;
        Label scoreLabel = new Label("Score: " + String.format("%.2f", score));
        scoreLabel.setLayoutX(root.getWidth() / 2 - 50);
        scoreLabel.setLayoutY(root.getHeight() / 2);
        root.getChildren().add(scoreLabel);
    }

    private void start(ActionEvent actionEvent) {
        level = 1;
        strikes = 0;
        levelLabel.setText("Level: 1");
        strikeLabel.setText("Strikes: 0");

        startTime = System.nanoTime();
        stop = false;

        sequence = generateSequence(level);
        displaySequence(sequence);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (stop) {
                    this.stop();
                }
            }
        };
        timer.start();

        start.setVisible(false);
        createTarget(20);
    }

    public Pane getRoot() {
        return root;
    }

    public List<Integer> generateSequence(int level) {
        List<Integer> sequence = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < level; i++) {
            sequence.add(rand.nextInt(9) + 1);
        }

        return sequence;
    }

    public void injectBackButton(Button back) {
        root.getChildren().add(0, back);
    }
}

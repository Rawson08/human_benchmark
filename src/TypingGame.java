import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TypingGame {
    private final Pane root;
    private final VBox controls;
    private final Button start;
    private Label label = new Label();
    private TextField textField = new TextField();
    private final Button exitButton = new Button("Exit");
    private final Button scoresButton = new Button("Scores");
    private final Button backButton = new Button("Back");
    private final Button saveScoreButton = new Button("Save");
    private Label speedLabel = new Label("Speed: 0");
    private Label accuracyLabel = new Label("Accuracy: 0%");
    private int totalKeyStrokes = 0;
    private int totalMistakes = 0;
    private Timeline speedTimeline;
    private Timeline timerTimeline;
    private int remainingTime = 60;

    private int score = 0;
    private List<String> sentences = new ArrayList<>(Arrays.asList(
            "The quick brown fox jumps over the lazy dog.",
            "The rain in Spain falls mainly on the plain.",
            "Four score and seven years ago.",
            "Now is the time for all good men to come to the aid of the party.",
            "Ask not what your country can do for you, ask what you can do for your country.",
            "There's no place like home.",
            "Houston, we have a problem.",
            "I think, therefore I am.",
            "To be or not to be, that is the question.",
            "Elementary, my dear Watson.",
            "Frankly, my dear, I don't give a damn.",
            "May the Force be with you."
    ));

    private int index = 0;
    private long startTime = 0;

    public TypingGame() {
        root = new Pane();
        root.setStyle("-fx-background-color: #F4F4F4;");

        controls = new VBox(10);
        controls.setAlignment(Pos.CENTER);
        controls.setTranslateY(250);
        start = new Button("Start");
        start.setStyle("-fx-font-size: 24; -fx-padding: 10 20;");
        start.setOnAction(this::start);

        controls.getChildren().addAll(start, exitButton, scoresButton);
        root.getChildren().addAll(controls);
    }

    public Pane getRoot() {
        return root;
    }

    private void start(ActionEvent event) {
        label.setAlignment(Pos.CENTER);
        textField.setAlignment(Pos.CENTER);
        speedLabel.setAlignment(Pos.CENTER);
        accuracyLabel.setAlignment(Pos.CENTER);
        controls.getChildren().remove(start);
        VBox typingBox = new VBox(10, label, textField, speedLabel, accuracyLabel);
        typingBox.setAlignment(Pos.CENTER);
        root.getChildren().add(typingBox);
        startGame();
    }

    private void startGame() {
        remainingTime = 60;
        totalKeyStrokes = 0;
        totalMistakes = 0;
        index = 0;
        score = 0;

        Collections.shuffle(sentences, new Random());

        startTimer();
        nextSentence();
    }

    private void nextSentence() {
        label.setText(sentences.get(index));
        textField.clear();
        textField.requestFocus();
        startTime = System.nanoTime();
        totalKeyStrokes = 0;
        totalMistakes = 0;
        index++;
        score = 0;
        speedLabel.setText("Speed: 0");
        accuracyLabel.setText("Accuracy: 0%");
        if (index == sentences.size()) {
            endGame();
        }
    }

    private void generateSentences() {
        String[] easySentences = {"The quick brown fox jumps over the lazy dog.", "The rain in Spain falls mainly on the plain.", "Four score and seven years ago."};
        String[] mediumSentences = {"The quick brown fox jumps over the lazy dog. It then runs back and jumps over again.", "The rain in Spain falls mainly on the plain, but sometimes it also rains in the mountains.", "Four score and seven years ago our fathers brought forth on this continent a new nation."};
        String[] hardSentences = {"Pneumonoultramicroscopicsilicovolcanoconiosis is a word that describes a lung disease caused by inhaling very fine silica particles.", "The bookkeeper is the only word in the English language with three consecutive double letters.", "A quokka is a small wallaby-like marsupial native to Western Australia."};

        sentences = List.of(new String[easySentences.length + mediumSentences.length + hardSentences.length]);
        int index = 0;
        for (String sentence : easySentences) {
            sentences.set(index++, sentence);
        }
        for (String sentence : mediumSentences) {
            sentences.set(index++, sentence);
        }
        for (String sentence : hardSentences) {
            sentences.set(index++, sentence);
        }

        // Shuffle the sentences
        for (int i = 0; i < sentences.size(); i++) {
            int randomIndex = (int) (Math.random() * sentences.size());
            String temp = sentences.get(i);
            sentences.set(i, sentences.get(randomIndex));
            sentences.set(randomIndex, temp);
        }
    }

    private void updateAccuracy(Label accuracyLabel, String sentence, String input) {
        int errors = 0;
        for (int i = 0; i < Math.min(sentence.length(), input.length()); i++) {
            if (sentence.charAt(i) != input.charAt(i)) {
                errors++;
            }
        }
        double accuracy = (double) (sentence.length() - errors) / sentence.length() * 100;
        accuracyLabel.setText("Accuracy: " + (int) accuracy + "%");
    }

    private int calculateWPM(long startTime, long endTime, int numChars) {
        double elapsedTime = (endTime - startTime) / 1000000000.0;
        double minutes = elapsedTime / 60.0;
        int wpm = (int) (numChars / 5.0 / minutes);
        return wpm;
    }

    private void endGame() {
        timerTimeline.stop();
        speedTimeline.stop();
        double accuracy = 100 - ((double) totalMistakes / totalKeyStrokes) * 100;
        label.setText(String.format("Game over! Your score is %d\nAccuracy: %.2f%%", score, accuracy));

        // Create a new VBox to hold buttons for saving scores or going back to main menu
        VBox buttonBox = new VBox(10, saveScoreButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        root.getChildren().add(buttonBox);

        // Attach event handlers for buttons
        saveScoreButton.setOnAction(this::saveScore);
        backButton.setOnAction(this::goBack);
    }

    private void saveScore(ActionEvent event) {
        // Append the score to a text file
        try (FileWriter writer = new FileWriter("scores.txt", true)) {
            writer.write(score + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display a message indicating that the score has been saved
        label.setText("Score saved!");

        // Remove the Save button
        VBox buttonBox = (VBox) saveScoreButton.getParent();
        buttonBox.getChildren().remove(saveScoreButton);
    }

    private void goBack(ActionEvent event) {
        // Remove the game elements from the root Pane
        root.getChildren().remove(label.getParent());

        // Remove the button VBox
        root.getChildren().remove(backButton.getParent());

        // Add the start button back to the controls VBox
        controls.getChildren().add(start);
    }

    private void startTimer() {
        Label timerLabel = new Label("Time: " + remainingTime);
        timerLabel.setAlignment(Pos.CENTER);
        controls.getChildren().add(timerLabel);

        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            remainingTime--;
            timerLabel.setText("Time: " + remainingTime);

            if (remainingTime <= 0) {
                endGame();
            }
        }));
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();
    }

    private void updateSpeed() {
        double elapsedTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
        int keystrokes = textField.getText().length();
        int mistakes = 0;

        for (int i = 0; i < keystrokes; i++) {
            if (textField.getText().charAt(i) != label.getText().charAt(i)) {
                mistakes++;
            }
        }

        int netKeystrokes = keystrokes - mistakes;

        if (netKeystrokes > 0) {
            double speed = netKeystrokes / elapsedTime * 60;
            speedLabel.setText(String.format("Speed: %.0f", speed));
        } else {
            speedLabel.setText("Speed: 0");
        }

        totalKeyStrokes += keystrokes;
        totalMistakes += mistakes;

        if (keystrokes == label.getText().length() && textField.getText().equals(label.getText())) {
            score++;
            speedTimeline.stop();
            speedTimeline.play();
            nextSentence();
        }
    }

    public void play() {
        Stage stage = new Stage();
        stage.setScene(new Scene(getRoot(), 800, 600));
        stage.show();
    }


    public void injectBackButton(Button back) {
        root.getChildren().add(0, back);
    }
}

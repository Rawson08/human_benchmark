import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VerbalMemoryTest extends Application {
    private final Pane root;
    private final Pane controls;
    private final Button start;
    private final Button seenButton;
    private final Button newButton;

    private final Label wordLabel;

    private int numMistakes = 0;

    private List<String> words;
    private List<String> seenWords = new ArrayList<>();
//    private List<String> newWords = new ArrayList<>();
//    private int currentWordIndex = 0;
    private boolean gameOver = false;


    public VerbalMemoryTest() {
        root = new Pane();
        root.setStyle("-fx-background-color: #1ae8e1");
        controls = new HBox(10);

        start = new Button("Start");
        start.setOnAction(this::start);
        start.setAlignment(Pos.CENTER);
        seenButton = new Button("Seen");
        seenButton.setAlignment(Pos.TOP_CENTER);
        seenButton.setOnAction(e -> markWordAsSeen());
        newButton = new Button("New");
        newButton.setAlignment(Pos.TOP_CENTER);
        newButton.setOnAction(e -> markWordAsNew());

        wordLabel = new Label("");
        wordLabel.setFont(new Font(30));
        wordLabel.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(wordLabel);

//        Label wordsRemainingLabel = new Label("Words Remaining:");
        controls.getChildren().addAll(start, seenButton, newButton);

        root.getChildren().add(controls);

//        wordsRemaining = new SimpleIntegerProperty(0);

        words = loadDictionary();
    }

    private List<String> loadDictionary() {
        List<String> dictionary = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dictionary.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
            }
//            Collections.shuffle(dictionary);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    private void markWordAsSeen() {
        if (gameOver) {
            return;
        }
        if (!seenWords.contains(wordLabel.getText())){
            numMistakes++;
            System.out.println("numMistakes " + numMistakes);
        }
        else {
            seenWords.add(wordLabel.getText());
        }
        checkGameOver();
        showNextWord();
    }

    private void markWordAsNew() {
        if (gameOver) {
            return;
        }
        if (seenWords.contains(wordLabel.getText())){
            numMistakes++;
            System.out.println("numMistakes " + numMistakes);
        }
        seenWords.add(wordLabel.getText());
        checkGameOver();
        showNextWord();
    }

    private void checkGameOver() {
        if (numMistakes == 3) {
            gameOver = true;
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    this.stop();
                    Platform.runLater(() -> {
                        ButtonType tryAgainButton = new ButtonType("Try Again", ButtonBar.ButtonData.OK_DONE);
                        Alert gameOverAlert = new Alert(Alert.AlertType.CONFIRMATION, "Total strikes reached. Do you want to try again?", tryAgainButton);
                        gameOverAlert.setHeaderText(null);
                        Optional<ButtonType> result = gameOverAlert.showAndWait();
                        result.ifPresent(buttonType -> {
                            if (buttonType == tryAgainButton) {
                                gameOver = false;
                                start.fire(); // restart the game
                            }
                        });
                    });
                }
            };
            timer.start();
        }
    }



    private void showNextWord() {
        if (words.size() > 0) {
            int randomIndex = (int) (Math.random() * words.size());
            String word = words.get(randomIndex);
            System.out.println("Current word: " + word);
            wordLabel.setText(word);
            wordLabel.setStyle("-fx-font-size: 40; -fx-text-alignment: center; -fx-text-fill: green; -fx-alignment: top-center;" +
                    "-fx-pref-height: 40; -fx-font-weight: bold");
//            wordsRemaining.setValue(words.size() - 1);
        } else {
        }
    }



    public void start(ActionEvent actionEvent) {
        Collections.shuffle(words);
//        currentWordIndex = 0;
        numMistakes = 0;
        seenWords.clear();
//        newWords.clear();
//        wordsRemaining.setValue(words.size());
//        long startTime = System.nanoTime();


        start.setVisible(false);
        seenButton.setDisable(false);
        newButton.setDisable(false);

        showNextWord();
    }



    public Pane getRoot() {
        return root;
    }

    public void injectBackButton(Button back) {
        controls.getChildren().add(0, back);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VerbalMemoryTest verbalMemoryTest = new VerbalMemoryTest();
        Scene scene = new Scene(verbalMemoryTest.getRoot(), 900, 800);
        stage.setScene(scene);
        stage.show();
    }
}
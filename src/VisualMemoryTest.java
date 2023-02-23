import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VisualMemoryTest {

    private static final int TILE_SIZE = 50;
    private static final int NUM_TILES = 9;
    private static final int GRID_SIZE = (int) Math.sqrt(NUM_TILES);
    private static final int NUM_SECONDS = 5;
    private static final Color TILE_COLOR = Color.GRAY;
    private static final Color HIGHLIGHT_COLOR = Color.YELLOW;

    private int score = 0;
    private boolean gameOver = false;
    private GridPane gridPane;
    private int[] sequence;

    private Label scoreLabel;
    private Button newGameButton;


    public void start(Stage primaryStage) {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < NUM_TILES; i++) {
            Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE, TILE_COLOR);
            StackPane tilePane = new StackPane(tile, new Label(""));
            gridPane.add(tilePane, i % GRID_SIZE, i / GRID_SIZE);
        }

        scoreLabel = new Label("Score: 0");
        newGameButton = new Button("New Game");
        newGameButton.setOnAction(event -> startNewGame());

        HBox controlPane = new HBox(10, scoreLabel, newGameButton);
        controlPane.setAlignment(Pos.CENTER);

        StackPane rootPane = new StackPane(gridPane, controlPane);

        Scene scene = new Scene(rootPane, NUM_TILES * TILE_SIZE, NUM_TILES * TILE_SIZE + 50);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Visual Memory Test");
        primaryStage.setResizable(false);
        primaryStage.show();

        startNewGame();
    }

    private void startNewGame() {
        score = 0;
        scoreLabel.setText("Score: 0");
        gameOver = false;

        // Generate a new sequence of tiles
        List<Integer> tileList = new ArrayList<>();
        for (int i = 1; i <= NUM_TILES; i++) {
            tileList.add(i);
        }
        Collections.shuffle(tileList);
        sequence = tileList.stream().mapToInt(i -> i).toArray();

        // Show the tiles for a brief period
        for (int i = 0; i < NUM_TILES; i++) {
            StackPane tilePane = (StackPane) getNodeByRowColumnIndex(i / GRID_SIZE, i % GRID_SIZE);
            Rectangle tile = (Rectangle) tilePane.getChildren().get(0);
            Label label = (Label) tilePane.getChildren().get(1);
            tile.setFill(TILE_COLOR);
            label.setText(String.valueOf(sequence[i]));
        }

        AnimationTimer timer = new AnimationTimer() {
            private long startTime = 0;
            private int currentIndex = 0;

            @Override
            public void handle(long now) {
                if (startTime == 0) {
                    startTime = now;
                }

                if (now - startTime >= NUM_SECONDS * 1_000_000_000L) {
                    // Hide the tiles
                    for (int i = 0; i < NUM_TILES; i++) {
                        StackPane tilePane = (StackPane) getNodeByRowColumnIndex(i / GRID_SIZE, i % GRID_SIZE);
                        Rectangle tile = (Rectangle) tilePane.getChildren().get(0);
                        tile.setFill(TILE_COLOR);
                        Label label = (Label) tilePane.getChildren().get(1);
                        label.setText("");
                    }
                    startTime = 0;
                    currentIndex = 0;
                    this.stop();
                    // Enable the tiles for the user to click
                    for (int i = 0; i < NUM_TILES; i++) {
                        StackPane tilePane = (StackPane) getNodeByRowColumnIndex(i / GRID_SIZE, i % GRID_SIZE);
                        Rectangle tile = (Rectangle) tilePane.getChildren().get(0);
                        tile.setFill(TILE_COLOR);
                        tile.setOnMouseClicked(event -> handleTileClick(tilePane));
                    }
                } else if (currentIndex < NUM_TILES) {
                    // Highlight the next tile in the sequence
                    int nextIndex = sequence[currentIndex];
                    StackPane tilePane = (StackPane) getNodeByRowColumnIndex((nextIndex - 1) / GRID_SIZE, (nextIndex - 1) % GRID_SIZE);
                    Rectangle tile = (Rectangle) tilePane.getChildren().get(0);
                    tile.setFill(HIGHLIGHT_COLOR);
                    currentIndex++;
                }
            }
        };

        timer.start();
    }

    private void handleTileClick(StackPane tilePane) {
        if (gameOver) {
            return;
        }

        // Disable the tile to prevent multiple clicks
        Rectangle tile = (Rectangle) tilePane.getChildren().get(0);
        tile.setFill(HIGHLIGHT_COLOR);
        tile.setOnMouseClicked(null);

        // Check if the clicked tile is in the correct sequence
        int clickedIndex = getRowColumnIndexByNode(tilePane)[0] * GRID_SIZE + getRowColumnIndexByNode(tilePane)[1];
        if (clickedIndex == sequence[score]) {
            score++;
            scoreLabel.setText("Score: " + score);

            // Check if the game is over
            if (score == NUM_TILES) {
                gameOver = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("Congratulations, you won!\nYour score: " + score);
                alert.showAndWait();
            }
        } else {
            gameOver = true;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("You lose");
            alert.showAndWait();
        }
    }

    private Node getNodeByRowColumnIndex(final int row, final int column) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    private int[] getRowColumnIndexByNode(Node node) {
        Integer row = GridPane.getRowIndex(node);
        row = row == null ? 0 : row;
        Integer column = GridPane.getColumnIndex(node);
        column = column == null ? 0 : column;
        return new int[]{row, column};
    }

    public Pane getRoot() {
        return gridPane;
    }

    public void injectBackButton(Button back) {
        gridPane.getChildren().add(0, back);
    }
}

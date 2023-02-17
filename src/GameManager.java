import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

public class GameManager extends Application {
    private String userName;
    private BorderPane root;
    private boolean nameTaken = false;

    public static void main(String[] args) {launch(args);}


    private static Node mainLogo() {
        Path path = new Path();
        path.setFill(Color.WHITE);

        path.getElements().addAll(
                new MoveTo(0.719527, 59.616),
                new LineTo(32.8399, 2.79148),
                new LineTo(33.8149, 1.06655),
                new LineTo(35.6429, 0),
                new LineTo(37.6243, 0),
                new LineTo(94.4947, 0),
                new LineTo(98.9119, 0),
                new LineTo(101.524, 4.94729),
                new LineTo(99.0334, 8.59532),
                new LineTo(71.201, 49.357),
                new LineTo(68.7101, 53.0051),
                new LineTo(71.3225, 57.9524),
                new LineTo(75.7397, 57.9524),
                new LineTo(82.2118, 57.9524),
                new LineTo(87.3625, 57.9524),
                new LineTo(89.6835, 64.4017),
                new LineTo(85.7139, 67.6841),
                new LineTo(14.34, 126.703),
                new LineTo(9.85287, 130.413),
                new LineTo(3.43339, 125.513),
                new LineTo(5.82845, 120.206),
                new LineTo(25.9709, 75.5735),
                new LineTo(27.6125, 71.936),
                new LineTo(24.9522, 67.8166),
                new LineTo(20.9615, 67.8166),
                new LineTo(5.50391, 67.8166),
                new LineTo(1.29539, 67.8166),
                new LineTo(-1.35146, 63.2798),
                new LineTo(0.719527, 59.616)
        );

        return path;
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Human Benchmark");
        root = new BorderPane();
        Scene scene = new Scene(root,800, 900);

        String[] names = {"Reaction Time", "Sequence Memory", "Aim Trainer", "Number Memory",
                "Verbal Memory", "Chimp Test", "Visual Memory", "Typing", "Unnamed"};
        Button[] buttons = new Button[9];
        Button back = new Button("<-");
        back.setOnAction(event -> {
            Scene scene1 = new Scene(getRoot(), 800, 900);
            primaryStage.setScene(scene1);
            primaryStage.show();
        });



        FlowPane buttonPane = new FlowPane();
        buttonPane.setPadding(new Insets(10));
        buttonPane.setVgap(10);
        buttonPane.setHgap(10);
        buttonPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new Button(names[i]);
            buttons[i].setId(names[i]);
            buttons[i].setPrefSize(180, 155);
            buttons[i].setFont(Font.font("Arial", FontWeight.BOLD, 16));
            int finalI = i;
            buttons[i].setOnMouseEntered(e -> {
                buttons[finalI].setTranslateY(-10);
                buttons[finalI].setTextFill(Color.RED);
            });
            int finalI1 = i;
            buttons[i].setOnMouseExited(e -> {
                buttons[finalI1].setTranslateY(0);
                buttons[finalI1].setTextFill(Color.BLACK);
            });
            buttonPane.getChildren().add(buttons[i]);
            buttons[i].setOnAction(event -> takeUsername());
        }


        buttons[0].setOnAction(event -> {

            if (!nameTaken){takeUsername();}
            ReactionTime reactionTime = new ReactionTime();
            reactionTime.injectBackButton(back);
            Scene scene01 = new Scene(reactionTime.getRoot(), 800, 900);
            primaryStage.setScene(scene01);
            primaryStage.show();
        });

        buttons[1].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            SequenceMemory sequenceMemory = new SequenceMemory(100,100);
            sequenceMemory.injectBackButton(back);
            Scene scn = new Scene(sequenceMemory.getRoot(), 800, 900);
            primaryStage.setScene(scn);
            primaryStage.show();
        });

        buttons[2].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            AimTrainer aimTrainer = new AimTrainer(10);
            aimTrainer.injectBackButton(back);
            Scene scene1 = new Scene(aimTrainer.getRoot(), 800, 900);
            primaryStage.setScene(scene1);
            primaryStage.show();
        });

        buttons[3].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            NumberMemory numberMemory = new NumberMemory();
            numberMemory.injectBackButton(back);
            Scene scene1 = new Scene(numberMemory.getRoot(), 800, 900);
            primaryStage.setScene(scene1);
            primaryStage.show();
        });

        buttons[4].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            VisualMemoryTest visualMemoryTest = new VisualMemoryTest();
            visualMemoryTest.injectBackButton(back);
            Scene scene1 = new Scene(visualMemoryTest.getRoot(), 800, 900);
            primaryStage.setScene(scene1);
            primaryStage.show();
        });

        buttons[5].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            ChimpTest chimpTest = new ChimpTest();
            chimpTest.injectBackButton(back);
            Scene scene1 = new Scene(chimpTest.getRoot(), 800, 900);
            primaryStage.setScene(scene1);
            primaryStage.show();
        });

        buttons[6].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            VerbalMemoryTest verbalMemoryTest = new VerbalMemoryTest();
            verbalMemoryTest.injectBackButton(back);
            Scene scene1 = new Scene(verbalMemoryTest.getRoot(), 800, 900);
            primaryStage.setScene(scene1);
            primaryStage.show();
        });


        buttons[7].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            TypingGame typingGame = new TypingGame();
            typingGame.injectBackButton(back);
            Scene scene1 = new Scene(typingGame.getRoot(), 800, 900);
            primaryStage.setScene(scene1);
            primaryStage.show();
        });



        //VBox for the top part of main screen with title and subtitle texts
        VBox topHalf = new VBox(8);
        topHalf.setPadding(new Insets(10));
        Text titleText = new Text("Human Benchmark");
        titleText.setFont(Font.font("Arial Arabic Regular", 80));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.WHITE);
        Text subTitle = new Text("Measure your abilities with brain games and cognitive tests.");
        subTitle.setFont(Font.font("Sequel Sans Headline Medium", 20));
        subTitle.setFill(Color.WHITE);
        topHalf.setStyle("-fx-background-color: #2B87D1FF");

        //VBox for the buttons on the bottom of main screen
        VBox bottomHalf = new VBox(10);
        bottomHalf.setSpacing(10);
        bottomHalf.setPadding(new Insets(20));

        //Tile implementation for the game buttons
        TilePane tilePane = new TilePane();
        Button getStartedButton = new Button("Get Started");
        getStartedButton.setLayoutY(0);
        getStartedButton.getStyleClass().add("button");

        //Adding a hover effect using transition of the buttons
        getStartedButton.setOnMouseEntered(event -> {
            getStartedButton.getStyleClass().add("button:hover");
            getStartedButton.setLayoutY(getStartedButton.getLayoutY() - 5);
        });
        getStartedButton.setOnMouseExited(event -> {
            getStartedButton.getStyleClass().add("button");
            getStartedButton.setLayoutY(getStartedButton.getLayoutY() + 5);
        });

        //Taking username from the "Get Started" button
        getStartedButton.setOnAction(event -> {
            nameTaken = true;
            takeUsername();
        });

        topHalf.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button-styles.css")).toExternalForm());


        topHalf.getChildren().addAll(mainLogo(), titleText, subTitle, getStartedButton);
        topHalf.setAlignment(Pos.CENTER);
        topHalf.setPrefHeight(scene.getHeight()/4);
        bottomHalf.getChildren().addAll(tilePane, buttonPane);
        bottomHalf.setAlignment(Pos.CENTER);
        bottomHalf.setPrefHeight(2*(scene.getHeight()/4));
        root.setTop(topHalf);
        root.setBottom(bottomHalf);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Parent getRoot() {
        return root;
    }


    private String takeUsername() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Welcome! Enter you name to continue");
        dialog.setHeaderText("Enter your name to get started!");
        dialog.setContentText("Please enter your name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> userName = name);
//        dialog.setOnCloseRequest(event -> takeToMainScreen());
        return userName;
    }

    private void takeToMainScreen() {
        System.out.println("pressed back");
    }
}

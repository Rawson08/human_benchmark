/* Human Benchmark Game
 * Roshan Subedi
 * CS351
 * */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Scene scene;
    private boolean nameTaken = false;

    public static void main(String[] args) {launch(args);}


    //Drawing the top logo using Path. The co-ordinate was extracted from the website using inspect element.
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
        Button back = new Button("Back");
        back.setLayoutY(10);
        back.setPrefSize(50, 30);
        back.setOnMouseEntered(e -> {
            back.setTranslateY(-5);
            back.setTextFill(Color.RED);
        });
        back.setOnMouseExited(e -> {
            back.setTranslateY(0);
            back.setTextFill(Color.BLACK);
        });
        back.setOnAction(event -> scene.setRoot(root));
        scene = new Scene(root,800, 900);

        String[] names = {"Reaction Time", "Sequence Memory", "Aim Trainer", "Number Memory",
                "Verbal Memory", "Chimp Test", "Visual Memory", "Typing", "Hit Me!"};
        Button[] buttons = new Button[9];



        //Tile implementation for the game buttons
        FlowPane buttonPane = new FlowPane();
        buttonPane.setPadding(new Insets(10));
        buttonPane.setVgap(10);
        buttonPane.setHgap(10);
        buttonPane.setAlignment(Pos.CENTER);

        //Creating 9 buttons using for-loop and setting text using String[] names
        for (int i = 0; i < 9; i++) {
            buttons[i] = new Button(names[i]);
            buttons[i].setId(names[i]);
            buttons[i].setPrefSize(180, 155);
            buttons[i].setFont(Font.font("Arial", FontWeight.BOLD, 16));

            //Transition effect in the buttons when mouse hovered and unhovered
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

        //ReactionTime game button action implementation to load ReactionTime class
        buttons[0].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            ReactionTime reactionTime = new ReactionTime();
            reactionTime.injectBackButton(back);
            scene.setRoot(reactionTime.getRoot());
        });

//        SequenceMemory game button action implementation to load SequenceMemory class
//        buttons[1].setOnAction(event -> {
//            if (!nameTaken){takeUsername();}
//            SequenceMemory sequenceMemory = new SequenceMemory();
//            sequenceMemory.injectBackButton(back);
//            scene.setRoot(sequenceMemory.getRoot());
//        });

        //AimTrainer game button action implementation to load AimTrainer class
        buttons[2].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            AimTrainer aimTrainer = new AimTrainer(10);
            aimTrainer.injectBackButton(back);
            scene.setRoot(aimTrainer.getRoot());

        });

        //NumberMemory game button action implementation to load NumberMemory class
        buttons[3].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            NumberMemory numberMemory = new NumberMemory();
            numberMemory.injectBackButton(back);
            scene.setRoot(numberMemory.getRoot());

        });

        //VerbalMemoryTest game button action implementation to load VerbalMemoryTest class
        buttons[4].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            VerbalMemoryTest verbalMemoryTest = new VerbalMemoryTest();
            verbalMemoryTest.injectBackButton(back);
            scene.setRoot(verbalMemoryTest.getRoot());

        });

        //ChimpTest game button action implementation to load ChimpTest class
        buttons[5].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            ChimpTest chimpTest = new ChimpTest();
            chimpTest.injectBackButton(back);
            scene.setRoot(chimpTest.getRoot());

        });

        //VisualMemoryTest game button action implementation to load VisualMemoryTest class
        buttons[6].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            VisualMemoryTest visualMemoryTest = new VisualMemoryTest();
            visualMemoryTest.injectBackButton(back);
            scene.setRoot(visualMemoryTest.getRoot());

        });


        //TypingGame game button action implementation to load TypingGame class
        buttons[7].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
            TypingGame typingGame = new TypingGame();
            typingGame.injectBackButton(back);
            scene.setRoot(typingGame.getRoot());
        });


        //DemoGame game button action implementation to load DemoGame class
        buttons[8].setOnAction(event -> {
            if (!nameTaken){takeUsername();}
//            DotClick dotClick = new DotClick();
//            dotClick.injectBackButton(back);
//            scene.setRoot(dotClick.getRoot());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention!");
            alert.setHeaderText(null);
            alert.setContentText("I am really sorry, " + userName + ". I am still working on this game.");
            alert.showAndWait();
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
            if (nameTaken){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Attention!");
                alert.setHeaderText(null);
                alert.setContentText("Welcome again, " + userName + ". Click on any game to play.");
                alert.showAndWait();
            }
            else{
                takeUsername();
            }
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

    public Pane getRoot() {
        return root;
    }


    private String takeUsername() {
        nameTaken = true;
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Welcome! Enter your name to continue");
        dialog.setHeaderText("Enter your name to get started!");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField = new TextField();
        textField.setPromptText("Please enter your name");
        dialog.getDialogPane().setContent(textField);
        Platform.runLater(textField::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return textField.getText();
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> userName = name);
        System.out.println(userName);
        return userName;
    }

}

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
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
    ButtonLogos buttonLogos = new ButtonLogos();


    public GameManager() {
    }

    public static void main(String[] args) {launch(args);}


    private static Node mainLogo() {
        Path path = new Path();
        path.setFill(Color.WHITE);

        double[][] controlPoints = new double[][] {
                {0.719527, 59.616},
                {32.8399, 2.79148},
                {33.8149, 1.06655},
                {37.6243, 0},
                {35.6429, 0},
                {98.9119, 0},
                {94.4947, 0},
                {101.524, 4.94729},
                {99.0334, 8.59532},
                {71.201, 49.357},
                {68.7101, 53.0051},
                {71.3225, 57.9524},
                {75.7397, 57.9524},
                {82.2118, 57.9524},
                {87.3625, 57.9524},
                {89.6835, 64.4017},
                {85.7139, 67.6841},
                {14.34, 126.703},
                {9.85287, 130.413},
                {3.43339, 125.513},
                {5.82845, 120.206},
                {25.9709, 75.5735},
                {27.6125, 71.936},
                {24.9522, 67.8166},
                {20.9615, 67.8166},
                {24.2933, 62.3651}
        };

        MoveTo moveTo = new MoveTo();
        moveTo.setX(controlPoints[0][0]);
        moveTo.setY(controlPoints[0][1]);
        path.getElements().add(moveTo);

        for (int i = 1; i < controlPoints.length; i++) {
            CubicCurveTo cubicCurveTo = new CubicCurveTo();
            cubicCurveTo.setControlX1(controlPoints[i][0]);
            cubicCurveTo.setControlY1(controlPoints[i][1]);
            cubicCurveTo.setControlX2(controlPoints[i][0]);
            cubicCurveTo.setControlY2(controlPoints[i][1]);
            cubicCurveTo.setX(controlPoints[i][0]);
            cubicCurveTo.setY(controlPoints[i][1]);
            path.getElements().add(cubicCurveTo);
        }

        return path;
    }




    private Path createPath() {
        Path path = new Path();
        path.setFill(Color.WHITE);
        MoveTo moveTo = new MoveTo(0.719527, 59.616);
        LineTo lineTo1 = new LineTo(32.8399, 2.79148);
        LineTo lineTo2 = new LineTo(37.6243, 0);
        LineTo lineTo3 = new LineTo(94.4947, 0);
        LineTo lineTo4 = new LineTo(101.524, 8.59532);
        LineTo lineTo5 = new LineTo(71.201, 49.357);
        LineTo lineTo6 = new LineTo(75.7397, 57.9524);
        LineTo lineTo7 = new LineTo(82.2118, 57.9524);
        LineTo lineTo8 = new LineTo(85.7139, 67.6841);
        LineTo lineTo9 = new LineTo(14.34, 126.703);
        LineTo lineTo10 = new LineTo(5.82845, 120.206);
        LineTo lineTo11 = new LineTo(25.9709, 75.5735);
        LineTo lineTo12 = new LineTo(20.9615, 67.8166);
        LineTo lineTo13 = new LineTo(5.50391, 67.8166);
        LineTo lineTo14 = new LineTo(0.719527, 59.616);
        path.getElements().addAll(moveTo, lineTo1, lineTo2, lineTo3, lineTo4, lineTo5, lineTo6, lineTo7, lineTo8, lineTo9, lineTo10, lineTo11, lineTo12, lineTo13, lineTo14);

        return path;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        String[] names = {"Reaction Time", "Sequence Memory", "Aim Trainer", "Number Memory",
                "Verbal Memory", "Chimp Test", "Visual Memory", "Typing", "Unnamed"};
        String[] subtext = {"Test your visual reflexes.", "Remember an increasingly long pattern of button presses.",
                "How quickly can you hit all the targets?"};
        Button[] buttons = new Button[9];


        FlowPane buttonPane = new FlowPane();
        buttonPane.setPadding(new Insets(10));
        buttonPane.setVgap(10);
        buttonPane.setHgap(10);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new Button(names[i]);
            buttons[i].setId(names[i]);
            buttons[i].setPrefSize(180, 180);
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

//        buttons[0].setOnAction(event -> );

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,1000, 900);




        //Drawing the main logo

        //Drawing the Reaction Time game logo




        primaryStage.setTitle("Human Benchmark");

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




        VBox bottomHalf = new VBox(10);
        bottomHalf.setSpacing(10);
        bottomHalf.setPadding(new Insets(20));

        Button typingGame = new Button("Typing Game");


        TilePane tilePane = new TilePane();


        Button getStartedButton = new Button("Get Started");
        getStartedButton.setLayoutY(0);
        getStartedButton.getStyleClass().add("button");
        getStartedButton.setOnMouseEntered(event -> {
            getStartedButton.getStyleClass().add("button:hover");
            getStartedButton.setLayoutY(getStartedButton.getLayoutY() - 5);
        });
        getStartedButton.setOnMouseExited(event -> {
            getStartedButton.getStyleClass().add("button");
            getStartedButton.setLayoutY(getStartedButton.getLayoutY() + 5);
        });

        getStartedButton.setOnAction(event -> {
            takeUsername();
        });

        topHalf.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button-styles.css")).toExternalForm());


        topHalf.getChildren().addAll(mainLogo(), titleText, subTitle, getStartedButton);
        topHalf.setAlignment(Pos.CENTER);
        topHalf.setPrefHeight(scene.getHeight()/3);
        bottomHalf.getChildren().addAll(tilePane, buttonPane);
        bottomHalf.setAlignment(Pos.CENTER);
        bottomHalf.setPrefHeight(2*(scene.getHeight()/3));
        root.setTop(topHalf);
        root.setBottom(bottomHalf);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void takeUsername() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Welcome! Enter you name to continue");
        dialog.setHeaderText("Enter your name to get started!");
        dialog.setContentText("Please enter your name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> userName = name);
    }
}

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

public class GameManager extends Application {
    private String userName;

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();

        Scene scene = new Scene(root,1000, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button-styles.css")).toExternalForm());


        primaryStage.setTitle("Human Benchmark");

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        Text titleText = new Text("Human Benchmark");
        titleText.setFont(Font.font("Arial Arabic Regular", 80));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.WHITE);

        Text subTitle = new Text("Measure your abilities with brain games and cognitive tests.");
        subTitle.setFont(Font.font("Sequel Sans Headline Medium", 20));
        subTitle.setFill(Color.WHITE);
        vBox.setStyle("-fx-background-color: #2B87D1FF");


        // Create a file called "button-style.css" and add the following CSS styles to it:



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
//        getStartedButton.setLayoutY(30);

        getStartedButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Welcome! Enter you name to continue");
            dialog.setHeaderText("Enter your name to get started!");
            dialog.setContentText("Please enter your name:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> userName = name);
        });

        vBox.getChildren().addAll(titleText, subTitle, getStartedButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefHeight(scene.getHeight()/3);
        root.setTop(vBox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

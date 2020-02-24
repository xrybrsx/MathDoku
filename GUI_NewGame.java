import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI_NewGame extends Application {

    private VBox vBox;
    private ChoiceBox newGame;

    public void start(Stage primaryStage) {

        vBox = new VBox();
        vBox.getChildren().addAll(newGame = new ChoiceBox(),
                new Button("Load from file"),
                new Button("Load from input"));
        newGame.setValue("New Game");
        newGame.getItems().addAll("New Game","2x2", "3x3", "4x4", "5x5", "6x6", "7x7");
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(10);


        Scene scene = new Scene(vBox, 200, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MathDoku");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}


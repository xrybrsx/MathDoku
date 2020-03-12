import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

// GUI for menu before actual game
public class NewGameGUI extends Application {

    private ChoiceBox newGame;
    private File inputFile;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {


        VBox vBox = new VBox();
        // set button for loading file from PC
        Button loadFileButton = new Button("Load from file");
        loadFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            setFile(fileChooser.showOpenDialog(primaryStage));
            try {
                readFile();
                primaryStage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        // Set Dialog for loading from input
        Button inputButton = new Button("Load from input");
        inputButton.setOnAction(e -> {
            Dialog inputData = new Dialog();
            DialogPane dialog = inputData.getDialogPane();
            dialog.setHeaderText("Write down your custom MathDoku");
            TextArea area = new TextArea();
            dialog.setContent(area);
            dialog.setStyle("-fx-background-color: #fff;");

            // Set the button types.
            ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getButtonTypes().addAll(okButtonType, cancelButtonType);
            Optional<ButtonType> result = inputData.showAndWait();
            if (result.get() == okButtonType) {
                // read game
                try {
                    GameReader reader = new GameReader(area);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else inputData.close();
        });
        // set new game options
        newGame = new ChoiceBox();
        newGame.setTooltip(new Tooltip("Select Difficulty"));
        vBox.getChildren().addAll(newGame, loadFileButton,
                inputButton);
        newGame.setValue("New Game");
        newGame.setItems(FXCollections.observableArrayList("New Game", "2x2", "3x3", "4x4", "5x5", "6x6", "7x7", "8x8"));
        newGame.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observableValue, number, newValue) -> {

            try {
                GameReader reader = new GameReader("src/" + newValue + ".txt");
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        Scene scene = new Scene(vBox, 200, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MathDoku");
        primaryStage.show();
    }

    public File getFile() {
        return inputFile;
    }

    public void setFile(File file) {
        inputFile = file;
    }

    public void readFile() throws Exception {
        GameReader reader = new GameReader(getFile().toString());
    }

}


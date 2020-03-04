import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class GUI_NewGame extends Application {

    private VBox vBox;
    private ChoiceBox newGame;
    private File inputFile;

    public void start(Stage primaryStage) {

        VBox vBox = new VBox();
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
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        newGame = new ChoiceBox();
        newGame.setTooltip(new Tooltip("Select Difficulty"));
        vBox.getChildren().addAll(newGame, loadFileButton,
                new Button("Load from input"));
        newGame.setValue("New Game");
        newGame.setItems(FXCollections.observableArrayList("New Game", "2x2", "3x3", "4x4", "5x5", "6x6", "7x7","8x8"));
        newGame.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observableValue, number, newValue) -> {

            try {
              GameReader reader = new GameReader(  "src/" + newValue + ".txt"  );
              primaryStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(10);

        Scene scene = new Scene(vBox, 200, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MathDoku");
        primaryStage.show();
    }
    public File getFile(){
        return inputFile;
    }
    public void setFile(File file){
        inputFile = file;
    }
    public void readFile() throws IOException {
        GameReader reader = new GameReader(getFile().toString());
    }

    public static void main(String[] args) {
        launch(args);
    }

}


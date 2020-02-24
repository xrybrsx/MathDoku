import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SIXxSIX extends Application {

    private ArrayList<BorderStroke> border;
    private ArrayList<Cage> cages;
    private Cell cell;
    private Cage cage;

    public  ArrayList<Cage> getCages() {
        return cages;
    }

    public void start(Stage primaryStage) {

        GUI gui = new GUI(6);
        gui.start(primaryStage);
        gui.start(primaryStage);
        cages = new ArrayList<>();
        Cage cage = new Cage();

        cage.addCells(gui.getGridCell(1), gui.getGridCell(2), gui.getGridCell(3));

    }

    public static void main(String[] args) {
        launch(args);
    }
    public void addCage(Cage cage){
        getCages().add(cage);
    }

}

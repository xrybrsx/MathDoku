import javafx.application.Application;
import javafx.scene.layout.BorderStroke;
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

        GUI gui = new GUI(4);
        gui.start(primaryStage);
        cages = new ArrayList<>();
        Cage cage = new Cage();
        cage.setCells(new ArrayList<>());
        cage.addCellsToCage(gui.getGridCell(1), gui.getGridCell(2), gui.getGridCell(3),gui.getGridCell(4),gui.getGridCell(5));
        System.out.println(gui.getGridCell(1).getAdjacentCells());
        System.out.println(gui.getGridCell(2).getAdjacentCells());
        System.out.println(gui.getGridCell(3).getAdjacentCells());
        System.out.println(cage.getCells());
       //cage.setBorder(new Border( new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 4, 4, 4), Insets.EMPTY)));
       // cage.getChildren().get(0).getBoundsInParent();


    }

    public static void main(String[] args) {
        launch(args);
    }
    public void addCage(Cage cage){
        getCages().add(cage);
    }

}

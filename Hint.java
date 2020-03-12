import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hint {

    private Controller controller;

    Hint(Controller controller) {
        this.controller = controller;
    }

    // main method for showing mistakes
    //coloring mistaken cell in red
    public void showMistakes() {
        for (int k = 0; k < controller.getHardnessLevel(); k++) {
            for (int i = 0; i < controller.getHardnessLevel(); i++) {
                controller.getCell(i, k).setBackground(Background.EMPTY);
            }
            checkAllColumns();
            checkAllRows();
            checkAllCages();
        }
    }
    //checks for repetition in a row and colors it
    public void showRowMistake(int i) {

        BackgroundFill background_fill = new BackgroundFill(Color.rgb(161, 3, 47, 0.5),
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);

        controller.iterateRow(i);
        if (controller.iterateColumn(i) != null) {
            Set set = new HashSet();
            for (int j = 0; j < controller.getRowCells().size(); j++) {
                set.add(controller.getRowCells().get(j).getText());
            }

            if (set.size() < controller.getHardnessLevel()) {
                for (int k = 0; k < controller.getHardnessLevel(); k++) {
                    controller.getCell(k, i).setBackground(background);

                }
            }
        }
    }
    //checks for repetition in a column and colors it
    public void showColumnMistake(int i) {
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(161, 3, 47, 0.5),
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        controller.iterateColumn(i);
        if (controller.iterateColumn(i) != null) {
            Set set = new HashSet();
            for (int j = 0; j < controller.getColumn().size(); j++) {
                set.add(controller.getColumn().get(j).getText());
            }
            if (set.size() < controller.getHardnessLevel()) {
                for (int k = 0; k < controller.getHardnessLevel(); k++) {
                    controller.getCell(i, k).setBackground(background);

                }
            }
        }
    }
    //checks the algebra expression in the cage and colors if wrong
    public void showCageMistake(Cage cage){
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(161, 3, 47, 0.5),
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        if (!isCageRight(cage)){
            for(Cell cell: cage.getCells()){
                cell.setBackground(background);
            }
        }
    }

    /* Methods for going through all cages, rows, and cages.*/
    public void checkAllColumns() {
        for (int i = 0; i < controller.getHardnessLevel(); i++) {
            showColumnMistake(i);
        }
    }
    public void checkAllCages(){
        for (Cage cage: controller.getCages()) {
            showCageMistake(cage);
        }
    }

    public void checkAllRows() {
        for (int i = 0; i < controller.getHardnessLevel(); i++) {
            showRowMistake(i);
        }
    }
     //clear color if user wants to stop showing mistakes
    public void stop() {
        for (int i = 0; i < controller.getHardnessLevel(); i++) {
            for (int k = 0; k < controller.getHardnessLevel(); k++) {
                controller.getCell(k, i).setBackground(Background.EMPTY);
            }
        }

    }
    //checks the algebra expressions
    public boolean isCageRight(Cage cage) {
        for (Cell cell: cage.getCells()){
            if (cell.getText().equals(""))
                return false;
        }
        int result = 1;
            switch (String.valueOf(cage.getLeadingCell().getResult().getText())) {
                case "+":
                    result = 0;
                    for (Cell cell : cage.getCells()) {
                        if (cell.getText() != "")
                            result = result +cell.getTextInt();
                    }
                    break;
                case "x":
                    for (Cell cell : cage.getCells()) {
                        if (cell.getText() != "")
                            result = cell.getTextInt() * result;
                    }
                    break;
                case "÷":
                    List<Cell> temp1 = new ArrayList<>(cage.getCells());
                    if(cage.getCells().size()>1) {
                        temp1.remove(cage.getBiggestCell());
                        result = cage.getBiggestCellInt();
                    }
                    for (Cell cell : temp1) {
                        result = result / cell.getTextInt();
                    } break;
                case "-":
                    if(cage.getCells().size()>1) {
                        result = cage.getBiggestCellInt();
                    }
                    for (Cell cell : cage.getCells()) {
                        result = result - cell.getTextInt();
                    }
                    result = result+cage.getBiggestCellInt();
                break;
                default:
                    break;
            }
            if (result == Integer.parseInt(cage.getLeadingCell().getOperator().getText()) || cage.getLeadingCell().getResult().getText() == "")
                return true;
            else return false;
    }
}


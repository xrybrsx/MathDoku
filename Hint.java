import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public class Hint {

    private GUI gui;

    Hint(GUI gui) {
        this.gui = gui;
    }

    public void showRowMistake(int i) {

        BackgroundFill background_fill = new BackgroundFill(Color.rgb(161, 3, 47, 0.5),
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);

        gui.iterateRow(i);
        if (gui.iterateColumn(i) != null) {
            Set set = new HashSet();
            for (int j = 0; j < gui.getRowCells().size(); j++) {
                set.add(gui.getRowCells().get(j).getText());
            }

            if (set.size() < gui.getHardnessLevel()) {
                for (int k = 0; k < gui.getHardnessLevel(); k++) {
                    gui.getCell(k, i).setBackground(background);

                }
            } else {
                for (int k = 0; k < gui.getHardnessLevel(); k++) {
                    gui.getCell(k, i).setBackground(Background.EMPTY);

                }
            }
        }
    }

    public void showColumnMistake(int i) {
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(161, 3, 47, 0.5),
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        gui.iterateColumn(i);
        if (gui.iterateColumn(i) != null) {
            Set set = new HashSet();
            for (int j = 0; j < gui.getColumn().size(); j++) {
                set.add(gui.getColumn().get(j).getText());
            }
            if (set.size() < gui.getHardnessLevel()) {
                for (int k = 0; k < gui.getHardnessLevel(); k++) {
                    gui.getCell(i, k).setBackground(background);

                }
            } else {
                for (int k = 0; k < gui.getHardnessLevel(); k++) {
                    gui.getCell(i, k).setBackground(Background.EMPTY);
                }
            }
        }
    }

    public void checkAllColumns() {
        for (int i = 0; i < gui.getHardnessLevel(); i++) {
            showColumnMistake(i);
            checkAllRows();
        }
    }

    public void checkAllRows() {
        for (int i = 0; i < gui.getHardnessLevel(); i++) {
            showRowMistake(i);
        }
    }

    public void stop() {
        for (int i = 0; i < gui.getHardnessLevel(); i++) {
            for (int k = 0; k < gui.getHardnessLevel(); k++) {
                gui.getCell(k, i).setBackground(Background.EMPTY);
            }
        }

    }

    public boolean isCageRight() {
        int result = 1;
        for (Cage cage : gui.getCages()) {
            switch (String.valueOf(cage.getLeadingCell().getResult().getText())) {
                case "+":
                    for (Cell cell : cage.getCells()) {
                        if(cell.getText() != "")
                        result = +cell.getTextInt();
                    }
                case "x":
                    for (Cell cell : cage.getCells()) {
                        if(cell.getText() != "")
                        result = cell.getTextInt() * result;
                    }
                case "÷":
                    for (int i = 0; 0 < cage.getCells().size(); i++) {
                        if(cage.getCells().get(1).getText() != "")
                        result = result / cage.getCells().get(i).getTextInt() / cage.getCells().get(i + 1).getTextInt();
                    }
                case "-":
                    for (Cell cell : cage.getCells()) {
                        if(cell.getText() != "")
                        result = -cell.getTextInt();
                    }
                default:
                    break;

            }result = Math.abs(result);
        }return  true;
    }
}
//    public boolean hasEqualRow(Cell cell) {
//        int i;
//        if (cell.getID() % gui.getHardnessLevel() == 0) i = cell.getID() - gui.getHardnessLevel() + 1;
//        else i = cell.getID() - cell.getID() % gui.getHardnessLevel() + 1;
//        int stop;
//        if (cell.getID() % gui.getHardnessLevel() == 0) stop = cell.getID();
//        else stop = cell.getID() - cell.getID() % gui.getHardnessLevel() + gui.getHardnessLevel();
//        for (; i <= stop; i++) {
//            //System.out.println(i);
//            for (int j = i + 1; j <= stop; j++) {
//                //System.out.println(j+"//"+i);
//                if (getCellByID(j).getValue() == getCellByID(i).getValue() && getCellByID(j).getValue() != 0) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


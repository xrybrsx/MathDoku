import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public class Hint {

    private GUI gui;

    Hint(GUI gui) {
        this.gui = gui;

    }

    public void checkRow(int i) {
        BackgroundFill background_fill = new BackgroundFill(Color.INDIANRED,
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        gui.iterateRow(i);
        Set set = new HashSet();
        for (int j = 0; j< gui.getHardnessLevel(); j++){
            set.add(gui.getRow().get(j).getText());
        }
        if (set.size()< gui.getHardnessLevel()) {
            for (int k = 0; k < gui.getHardnessLevel(); k++) {
                gui.getCell(k, i).setBackground(background);

            }
        } else {
            for (int k = 0; k < gui.getHardnessLevel(); k++) {
                gui.getCell(k, i).setBackground(Background.EMPTY);

            }
        }
    }
    public void checkColumn(int i) {
        BackgroundFill background_fill = new BackgroundFill(Color.INDIANRED,
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);

        gui.iterateColumn(i);
        Set set = new HashSet();
        for (int j = 0; j< gui.getHardnessLevel(); j++){
            set.add(gui.getColumn().get(j).getText());
        }
        if (set.size()< gui.getHardnessLevel()) {
            for (int k = 0; k < gui.getHardnessLevel(); k++) {
                gui.getCell(i, k).setBackground(background);
            }
        } else {
            for (int k = 0; k < gui.getHardnessLevel(); k++) {
                gui.getCell(i, k).setBackground(Background.EMPTY);

            }
        }
    }
    public void checkAllColumns(){
        for (int i = 0; i < gui.getHardnessLevel(); i++) {
            checkColumn(i);
        }
    }
    public void checkAllRows(){
        for (int i = 0; i < gui.getHardnessLevel(); i++) {
            checkRow(i);
        }
    }
    public void stop(){
        for (int i = 0; i < gui.getHardnessLevel(); i++) {
            for (int k = 0; k < gui.getHardnessLevel(); k++) {
                gui.getCell(k, i).setBackground(Background.EMPTY);
            }
        }
    }
}

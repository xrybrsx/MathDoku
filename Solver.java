import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Solver {

    private GUI gui;

    Solver(GUI gui){
        this.gui = gui;
        checkRows();
    }
    public void checkRows(){
        Set tempSet = new HashSet();
        for (int i = 0; i<gui.getHardnessLevel()-1; i++ ) {
        for (TextField str : gui.getRow(i)) {
            if (!tempSet.add(str.getText())) {
                TextFieldSkin highlight = new TextFieldSkin(str);

            }
        }

    }
//
//        for (int i = 0; i<gui.getHardnessLevel()-1; i++ ) {
//            for (int j = 0; j < gui.getHardnessLevel() - 1; j++) {
//                gui.getRow(i).get(j);
//            }
//        }
    }
}

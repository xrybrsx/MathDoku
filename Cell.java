import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Objects;

public class Cell extends VBox {
    private TextField textField;
    private Label operator;
    private Label result;
    private Cage cage;
    private GUI gui;
    private int column;
    private int row;
    private ArrayList<Cell> adjacentCells;

    Cell(int column, int row) {
        this.column = column;
        this.row = row;

    }

    public TextField getTextField() {
        return this.textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
        this.getChildren().add(this.textField);
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /* Adding a listener for when the cursor is in the textField -
     * sets a limit of 1 and only numbers in the input */
    public void setTextFieldLimit() {
        int maxLength = 1;
        getTextField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (!textField.getText().matches("[1-9]") || Integer.parseInt(textField.getText()) > gui.getHardnessLevel() || textField.getText() == null) { // if the text doesn't match integers 0-9:
                    String s = (Objects.requireNonNull(textField.getText())).substring(0, 0);
                    // or is bigger than the limit of hardness level
                    textField.setText(s);
                }
//                if (textField.getText().length() > maxLength) { // if the text is more than 1
////                //String s = textField.getText().substring(0, maxLength);
////                //textField.getText().replaceAll(oldValue,newValue);
////                textField.setText(textField.getText().replaceAll(oldValue,newValue));
////                //textField.setText(s);
//                tempNum.add(newValue);
//                }
                char c = textField.getText().charAt(1);
                int len = textField.getText().length();
                if (len < maxLength) {
                    return;
                } else {
                    if ((c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE) ||
                            (c == KeyEvent.VK_ENTER) ||
                            (c == KeyEvent.VK_TAB))
                        return;
                    else {
                        textField.setText(null);
                        textField.setText(textField.getText().replaceAll(oldValue, newValue));
                    }
                }
            }
        });
    }

    public Label getOperator() {
        return operator;
    }

    public void setOperator(Label operator) {
        this.operator = operator;
    }

    public Label getResult() {
        return result;
    }

    public void setResult(Label result) {
        this.result = result;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Cage getCage() {
        return cage;
    }

    public void setCage(Cage cage) {
        this.cage = cage;
    }

    public ArrayList<Cell> getAdjacentCells() {
        adjacentCells = new ArrayList<>();
        if (hasTop()) {
            adjacentCells.add(gui.getCell(getColumn(), getRow() - 1));
        }
        if (hasRight()) {
            adjacentCells.add(gui.getCell(getColumn() + 1, getRow()));
        }
        if (hasBottom()) {
            adjacentCells.add(gui.getCell(getColumn(), getRow() + 1));
        }
        if (hasLeft()) {
            adjacentCells.add(gui.getCell(getColumn() - 1, getRow()));
        }
        return adjacentCells;
    }

    public boolean hasRight() {
        if (getColumn() == gui.getHardnessLevel() - 1) return false;
        else return true;
    }

    public boolean hasLeft() {
        if (getColumn() == 0) return false;
        else return true;
    }

    public boolean hasTop() {
        if (getRow() == 0) return false;
        else return true;
    }

    public boolean hasBottom() {
        if (getRow() == gui.getHardnessLevel() - 1) return false;
        else return true;
    }
}

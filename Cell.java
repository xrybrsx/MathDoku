import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Cell extends VBox {
    private TextField textField;
    private Label operator;
    private Label result;
    private Cage cage;
    private FlowPane flow;
    private GUI gui;
    private int column;
    private int row;
    private ArrayList<Cell> adjacentCells;

    Cell(int column, int row) {
        this.column = column;
        this.row = row;

    }

    public FlowPane getFlow() {
        return flow;
    }

    public void setFlow(FlowPane flow) {
        this.flow = flow;
        this.getChildren().add(flow);
    }

    public GUI getGui() {
        return gui;
    }

    public void setGui(GUI gui) {
        this.gui = gui;

    }

    public TextField getTextField() {
        return this.textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
        this.getChildren().add(this.textField);
        this.textField.alignmentProperty().setValue(Pos.BOTTOM_CENTER);
    }

    /* Adding a listener for when the cursor is in the textField -
     * sets a limit of 1 and only numbers in the input */
    public void setTextFieldLimit() {
        getTextField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (!newValue.matches("[1-9]") || Integer.parseInt(newValue) > gui.getHardnessLevel() || textField.getText() == null) { // if the text doesn't match integers 0-9:
                    textField.clear();

//                } int c = textField.textProperty().;
//                    if ((c == KeyEvent.VK_BACK_SPACE) ||
//                            (c == KeyEvent.VK_DELETE) ||
//                            (c == KeyEvent.VK_ENTER) ||
//                            (c == KeyEvent.VK_TAB))
//                        textField.setText("");
                } else {
                    textField.getText().replace(oldValue, newValue);
                    textField.getText().replace(oldValue, newValue);
                }

               if (getGui().getHintButton().isSelected()){

                   getGui().getHint().checkAllColumns();
                   //getGui().getHint().checkAllRows();
               }else getGui().getHint().stop();
            }
        });
    }

    public Label getOperator() {
        return operator;
    }

    public void setOperator(Label operator) {
        this.operator = operator;
        flow.getChildren().add(this.operator);
        flow.setPrefSize(10.0, 10.0);
        flow.alignmentProperty().set(Pos.TOP_LEFT);
        this.operator.setAlignment(Pos.TOP_CENTER);
    }

    public Label getResult() {
        return result;
    }

    public void setResult(Label result) {
        this.result = result;
        flow.getChildren().add(this.result);
        flow.setPrefWrapLength(50.0);
        flow.alignmentProperty().set(Pos.TOP_LEFT);
        this.result.setAlignment(Pos.TOP_LEFT);
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

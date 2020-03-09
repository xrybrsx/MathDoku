import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

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
    Stack<String> undoStack = new Stack<>();
    Stack<String> redoStack = new Stack<>();
    boolean toggle;
//    public Circle getMarker() {
//        return marker;
//    }

//    public void setMarker(Circle marker) {
//        this.marker = marker;
//        getChildren().add(marker);
 //   }

//    private Circle marker;
public Stack getRedoStack() {
    return this.redoStack;
}

    public Stack getUndoStack() {
        return this.undoStack;
    }

    public TextField getEdit() {
        return edit;
    }

    public void setEdit(TextField edit) {
        this.edit = edit;
    }

    private TextField edit;

    Cell(int column, int row) {
        this.column = column;
        this.row = row;

    }

    public FlowPane getFlow() {
        return flow;
    }

    public void setFlow(FlowPane flow) {
        this.flow = flow;
        this.getChildren().addAll(flow);
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
        this.getChildren().addAll(this.textField);
        this.textField.alignmentProperty().setValue(Pos.BOTTOM_CENTER);
    }

    /* Adding a listener for when the text in the field is changed -
     * sets a limit of length 1 and numbers only */
    public void setTextFieldLimit() {


        getTextField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {

                if (newValue.equals("") || // if user has pressed "delete"
                        (newValue.substring(newValue.length() - 1).matches("[1-9]") // if matches integers 1-9
                                && Integer.parseInt(newValue.substring(newValue.length() - 1)) <= gui.getHardnessLevel())){
                    textField.setText(newValue);
                    addUndo(newValue);
                    if (newValue.length() > 1) { // update the newValue if it gets longer than 1
                        textField.setText(newValue.substring(newValue.length() - 1));

                    }
                    /*check for correctness and show mistakes if incorrect*/
                    if (getGui().getHintButton().isSelected()) {
                        getGui().getHint().checkAllColumns();
                        getGui().getHint().isCageRight();
                    }
                }
                // if input is invalid set the oldValue so the text doesn't change
                else {
                    textField.setText(oldValue);
                }
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

    public int getID() {
        int i = row * gui.getHardnessLevel() + column;
        return i - 1;
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

    public int getTextInt() {
        return Integer.parseInt(getTextField().getText());
    }

    public String getText() {
        return getTextField().getText();
    }

    public String undo() {
        if (getUndoStack().isEmpty()|| getUndoStack().peek().equals("")){
            return "";
        }else {
            if (toggle == false ){
                getRedoStack().push(getUndoStack().pop());
            }
            try {
            String s = getUndoStack().pop().toString();
            toggle = true;
            return s;
            }

            catch (EmptyStackException e){
                System.out.println("no more numbers to undo!");
            }
        }
        return "";
    }
    public String redo(){
        if (getRedoStack().isEmpty() || getRedoStack().peek().equals("")){
            return "";
        }
        else {
            if (toggle == true ){
            getUndoStack().push(getRedoStack().pop());
        }
            String s = getRedoStack().pop().toString();

            if (!getUndoStack().peek().equals(s))
            getUndoStack().push(s);
            toggle = false;
            return s;
        }
    }
    public void addUndo(String s){
        if(undoStack.isEmpty()){
            getUndoStack().push(s);
            toggle = false;
        }
        else if (s.length()==1 && !getUndoStack().peek().equals(s))
        getUndoStack().push(s);
        toggle = false;
    }

}

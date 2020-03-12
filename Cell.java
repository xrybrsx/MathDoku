import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class Cell extends BorderPane {
    private TextField textField;
    private Label operator;
    private Label result;
    private Cage cage;
    private FlowPane flow;
    private Controller controller;
    private int column;
    private int row;
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();
    private boolean toggle;

    Stack getRedoStack() {
    return this.redoStack;
}

    Stack getUndoStack() {
        return this.undoStack;
    }

    Cell(int column, int row) {
        this.column = column;
        this.row = row;

    }

    public FlowPane getFlow() {
        return flow;
    }

    void setFlow(FlowPane flow) {
        this.flow = flow;
       // this.getChildren().addAll(flow);
        setTop(flow);
    }

    Controller getController() {
        return controller;
    }

    void setController(Controller controller) {
        this.controller = controller;

    }

    TextField getTextField() {
        return this.textField;
    }

    void setTextField(TextField textField) {
        this.textField = textField;
        setBottom(textField);

    }

    /* Adding a listener for when the text in the field is changed -
     * sets a limit of length 1 and numbers only */
    void setTextFieldLimit() {


        getTextField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {

                if (newValue.equals("") || // if user has pressed "delete"
                        (newValue.substring(newValue.length() - 1).matches("[1-9]") // if matches integers 1-9
                                && Integer.parseInt(newValue.substring(newValue.length() - 1)) <= controller.getHardnessLevel())){
                    textField.setText(newValue);
                    addUndo(newValue);
                    if (newValue.length() > 1) { // update the newValue if it gets longer than 1
                        textField.setText(newValue.substring(newValue.length() - 1));

                    }
                    /*check for correctness and show mistakes if incorrect*/
                    if (getController().getHintButton().isSelected()) {
                        getController().getHint().showMistakes();
                    }
                }
                // if input is invalid set the oldValue so the text doesn't change
                else {
                    textField.setText(oldValue);
                }
            }
        });
    }

    Label getOperator() {
        return operator;
    }

    void setOperator(Label operator) {
        this.operator = operator;
        flow.getChildren().add(this.operator);
        flow.setPrefSize(10.0, 10.0);
        flow.alignmentProperty().set(Pos.TOP_LEFT);
        this.operator.setAlignment(Pos.TOP_CENTER);
    }

    Label getResult() {
        return result;
    }

    void setResult(Label result) {
        this.result = result;
        flow.getChildren().add(this.result);
        flow.setPrefWrapLength(50.0);
        flow.alignmentProperty().set(Pos.TOP_LEFT);
        this.result.setAlignment(Pos.TOP_LEFT);
    }

    int getColumn() {
        return column;
    }

    int getRow() {
        return row;
    }

    void setCage(Cage cage) {
        this.cage = cage;
    }

    int getID() {
        int i = row * controller.getHardnessLevel() + column;
        return i - 1;
    }

    ArrayList<Cell> getAdjacentCells() {
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        if (hasTop()) {
            adjacentCells.add(controller.getCell(getColumn(), getRow() - 1));
        }
        if (hasRight()) {
            adjacentCells.add(controller.getCell(getColumn() + 1, getRow()));
        }
        if (hasBottom()) {
            adjacentCells.add(controller.getCell(getColumn(), getRow() + 1));
        }
        if (hasLeft()) {
            adjacentCells.add(controller.getCell(getColumn() - 1, getRow()));
        }
        return adjacentCells;
    }

    private boolean hasRight() {
        return getColumn() != controller.getHardnessLevel() - 1;
    }

    private boolean hasLeft() {
        return getColumn() != 0;
    }

    private boolean hasTop() {
        return getRow() != 0;
    }

    private boolean hasBottom() {
        return getRow() != controller.getHardnessLevel() - 1;
    }

    int getTextInt() {
        if (!getText().equals(""))
        return Integer.parseInt(getTextField().getText());
        return 0;
    }

    String getText() {
        return getTextField().getText();
    }

    String undo() {
        if (getUndoStack().isEmpty()|| getUndoStack().peek().equals("")){
            return "";
        }else {
            if (!toggle){
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
    String redo(){
        if (getRedoStack().isEmpty() || getRedoStack().peek().equals("")){
            return "";
        }
        else {
            if (toggle){
            getUndoStack().push(getRedoStack().pop());
        }
            String s = getRedoStack().pop().toString();

            if (!getUndoStack().peek().equals(s))
            getUndoStack().push(s);
            toggle = false;
            return s;
        }
    }
    private void addUndo(String s){
        if(undoStack.isEmpty()){
            getUndoStack().push(s);
            toggle = false;
        }
        else if (s.length()==1 && !getUndoStack().peek().equals(s))
        getUndoStack().push(s);
        toggle = false;
    }

}

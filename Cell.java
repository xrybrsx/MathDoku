import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.awt.event.KeyEvent;
import java.util.Objects;

public class Cell extends VBox {
    private TextField textField;
    private Label operator;
    private Label result;
    private Cage cage;
    private GUI gui;


    public TextField getTextField() {
        return this.textField;
    }
    public void setGui(GUI gui){
        this.gui = gui;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
        this.getChildren().add(this.textField);
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

    private int column;
    private int row;

    Cell(int column,int row){
        this.column = column;
        this.row = row;

    }

    public void setCage(Cage cage) {
        this.cage = cage;
    }

    public Cage getCage(){
        return cage;
    }
    public boolean isAdjacentTo(Cell cell){
        if (getRow() == cell.getRow()+1 || getRow() == cell.getRow()-1) {
            return true;
        }else if (getColumn() == cell.getColumn()+1 || getColumn() == cell.getColumn()-1){
                return true;
            }else {
            return false;
        }

    }


}

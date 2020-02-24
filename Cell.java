import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Cell extends VBox {
    private TextField textField;
    private Label operator;
    private Label result;
    private Cage cage;


    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
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

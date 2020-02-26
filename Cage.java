import javafx.scene.control.Label;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Cage extends Shape {

    private ArrayList<Cell> cells;
    private Cell cell;
    private Cell leadingCell;
    private GUI gui;

    public void setLeadingCell(Label operator, Label result) {
        leadingCell = cells.get(0);
        leadingCell.setOperator(operator);
        leadingCell.setResult(result);
    }

    public GUI getGui() {
        return gui;
    }

    public ArrayList<Cell> getCells() {
        return cells;

    }

    public void setCells(ArrayList<Cell> cells) {
        cells = new ArrayList<>();
        this.cells = cells;

    }

    public Cell getLeadingCell() {
        return leadingCell;
    }


    public void addCellsToCage(Cell... all) {
        ArrayList tmp = new ArrayList<>();
        for (Cell cell : all) {
            if (!checkIfAdjacent(cell)) tmp.add(cell);
            else getCells().add(cell);
        }
        tmp.clear();
    }

    public boolean checkIfAdjacent(Cell cell) {
        int count = 0;
        for (Cell thisCell : getCells()) {
            if (!cell.getAdjacentCells().contains(thisCell) && getCells() != null) count++;
            if (getCells().size() == count) return false;
        }
        return true;
    }

}

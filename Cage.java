import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Cage {

    private ArrayList<Cell> cells;
    private Cell cell;
    private Cell leadingCell;
    private GUI gui;
    private String operator;

    public String getOperator() {
        return operator;
    }

    public String getResult() {
        return result;
    }

    private String result;

    public void setLeadingCell(String operator, String result) {
        this.operator = operator;
        this.result = result;
        ArrayList<Cell> duplicate = new ArrayList<>();
        for (int i = 0; i < cells.size() ; i++) {
            duplicate.add(cells.get(i));
        }

        while (duplicate.size()>1){
            if (duplicate.get(0).getID() > duplicate.get(1).getID())
              duplicate.remove(0);
            if (duplicate.size() > 1) {
                if (duplicate.get(0).getID() < duplicate.get(1).getID())
                    duplicate.remove(1);
            }
        }
        for (Cell cell: cells){
            if(cell == duplicate.get(0)){
            leadingCell = cell;
            break;
            }
        }
        leadingCell.getOperator().setText(operator);
        leadingCell.getResult().setText(result);
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
            else {
                cell.setCage(this);
                getCells().add(cell);
            }
        }
        tmp.clear();
    }

    public boolean hasRight(Cell cell) {
        if (getCells().contains(cell.getGui().getCell(cell.getColumn() + 1, cell.getRow()))) return true;
        else return false;
    }

    public boolean hasLeft(Cell cell) {
        if (getCells().contains(cell.getGui().getCell(cell.getColumn() - 1, cell.getRow()))) return true;
        else return false;
    }

    public boolean hasTop(Cell cell) {
        if (getCells().contains(cell.getGui().getCell(cell.getColumn(), cell.getRow() - 1))) return true;
        else return false;
    }

    public boolean hasBottom(Cell cell) {
        if (getCells().contains(cell.getGui().getCell(cell.getColumn(), cell.getRow() + 1))) return true;
        else return false;
    }

    public boolean checkIfAdjacent(Cell cell) {
        int count = 0;
        for (Cell thisCell : getCells()) {
            if (!cell.getAdjacentCells().contains(thisCell) && getCells() != null) count++;
            if (getCells().size() == count) return false;
        }
        return true;
    }

    public void setBorder() {
        for (Cell cell : getCells()) {
            if (getCells().size() == 1)
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 4, 4, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && hasRight(cell) == false && hasLeft(cell) == false && hasBottom(cell) == false)
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 4, 4, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && hasRight(cell) && hasLeft(cell) == false && hasBottom(cell) == false)
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 4, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && hasRight(cell) && hasLeft(cell) && hasBottom(cell) == false)
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 4, 0))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) == false && hasRight(cell) && hasLeft(cell) && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 0, 0, 0))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) == false && hasRight(cell) == false && hasLeft(cell) && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 4, 0, 0))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) == false && hasRight(cell) == false && hasLeft(cell) == false && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 4, 0, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) == false && hasRight(cell) && hasLeft(cell) && hasBottom(cell) == false)
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 0, 4, 0))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) == false && hasRight(cell) && hasLeft(cell) == false && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 0, 0, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) == false && hasRight(cell) == false && hasLeft(cell) && hasBottom(cell) == false)
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 4, 4, 0))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) == false && hasRight(cell) && hasLeft(cell) == false && hasBottom(cell) == false)
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 0, 4, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && hasRight(cell) == false && hasLeft(cell) == false && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 4, 0, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && hasRight(cell) == false && hasLeft(cell) && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 4, 0, 0))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && hasRight(cell) && hasLeft(cell) == false && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && hasRight(cell) == false && hasLeft(cell) && hasBottom(cell) == false)
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 4, 4, 0))));

        }
    }

}

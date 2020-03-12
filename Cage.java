import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Cage {

    private ArrayList<Cell> cells;
    private Cell cell;
    private Cell leadingCell; //the cell containing the operator and result
    private Controller controller;
    private String operator;
    private String result;

    public String getOperator() {
        return operator;
    }

    public String getResult() {
        return result;
    }

    public void setLeadingCell(String operator, String result) {
        this.operator = operator;
        this.result = result;
        ArrayList<Cell> duplicate = new ArrayList<>();
        for (Cell value : cells) {
            duplicate.add(value);
        }

        while (duplicate.size() > 1) {
            if (duplicate.get(0).getID() > duplicate.get(1).getID())
                duplicate.remove(0);
            if (1 < duplicate.size()) {
                if (duplicate.get(0).getID() < duplicate.get(1).getID())
                    duplicate.remove(1);
            }
        }
        for (Cell cell : cells) {
            if (cell == duplicate.get(0)) {
                leadingCell = cell;
                break;
            }
        }
        leadingCell.getOperator().setText(operator);
        leadingCell.getResult().setText(result);
    }

    public Controller getController() {
        return controller;
    }

    ArrayList<Cell> getCells() {
        return cells;

    }

    void setCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        this.cells = cells;

    }

    Cell getLeadingCell() {
        return leadingCell;
    }


    void addCellsToCage(Cell... all) {
        for (Cell cell : all) {
                cell.setCage(this);
                getCells().add(cell);
            }
    }

    private boolean hasRight(Cell cell) {
        return getCells().contains(cell.getController().getCell(cell.getColumn() + 1, cell.getRow()));
    }

    private boolean hasLeft(Cell cell) {
        return getCells().contains(cell.getController().getCell(cell.getColumn() - 1, cell.getRow()));
    }

    private boolean hasTop(Cell cell) {
        return getCells().contains(cell.getController().getCell(cell.getColumn(), cell.getRow() - 1));
    }

    private boolean hasBottom(Cell cell) {
        return getCells().contains(cell.getController().getCell(cell.getColumn(), cell.getRow() + 1));
    }

    private boolean checkIfAdjacent(Cell cell) {
        int count = 0;
        for (Cell thisCell : getCells()) {
            if (!cell.getAdjacentCells().contains(thisCell) && getCells() != null && getCells().size() != 1) count++;
            assert getCells() != null;
            if (getCells().size() == count) return false;
        }
        return true;
    }
    boolean checkCageRight() {
        boolean bool = true;
        for (Cell cell : getCells()) {
            if (!checkIfAdjacent(cell))
                bool = false;
            }
        if (!bool) getCells().clear();
       return bool;
    }

    void setBorder() {
        for (Cell cell : getCells()) {
            if (getCells().size() == 1)
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 4, 4, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && !hasRight(cell) && !hasLeft(cell) && !hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 4, 4, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && hasRight(cell) && !hasLeft(cell) && !hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 4, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && hasRight(cell) && hasLeft(cell) && !hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 4, 0))));
            else if (cell.borderProperty().getValue() == null && !hasTop(cell) && hasRight(cell) && hasLeft(cell) && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 0, 0, 0))));
            else if (cell.borderProperty().getValue() == null && !hasTop(cell) && !hasRight(cell) && hasLeft(cell) && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 4, 0, 0))));
            else if (cell.borderProperty().getValue() == null && !hasTop(cell) && !hasRight(cell) && !hasLeft(cell) && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 4, 0, 4))));
            else if (cell.borderProperty().getValue() == null && !hasTop(cell) && hasRight(cell) && hasLeft(cell) && !hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 0, 4, 0))));
            else if (cell.borderProperty().getValue() == null && !hasTop(cell) && hasRight(cell) && !hasLeft(cell) && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 0, 0, 4))));
            else if (cell.borderProperty().getValue() == null && !hasTop(cell) && !hasRight(cell) && hasLeft(cell) && !hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 4, 4, 0))));
            else if (cell.borderProperty().getValue() == null && !hasTop(cell) && hasRight(cell) && !hasLeft(cell) && !hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 0, 4, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && !hasRight(cell) && !hasLeft(cell) && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 4, 0, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && !hasRight(cell) && hasLeft(cell) && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 4, 0, 0))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && hasRight(cell) && !hasLeft(cell) && hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 4))));
            else if (cell.borderProperty().getValue() == null && hasTop(cell) && !hasRight(cell) && hasLeft(cell) && !hasBottom(cell))
                cell.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 4, 4, 0))));

        }
    }

    int getBiggestCellInt() {
        Cell cell = new Cell(0,0);
        for (int i = 0; i < getCells().size() - 1; i++) {
            if (!getCells().get(i).getText().equals("")) {
                if (getCells().get(i).getTextInt() >= getCells().get(i + 1).getTextInt()) {
                     cell = getCells().get(i);
                }else cell = getCells().get(i+1);

            }
        }
        return cell.getTextInt();
    }
    Cell getBiggestCell() {
        Cell cell = new Cell(0,0);
        for (int i = 0; i < getCells().size() - 1; i++) {
            if (!getCells().get(i).getText().equals("")) {
                if (getCells().get(i).getTextInt() >= getCells().get(i + 1).getTextInt()) {
                    cell = getCells().get(i);
                }else cell = getCells().get(i+1);
            }
        }
        return cell;
    }
}

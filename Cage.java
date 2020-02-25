import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Cage {

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    private ArrayList<Cell> cells;
    Cell cell;
    //Cell last;
    Cell leadingCell;
    BorderStroke stroke;

    public void setLeadingCell(Label operator, Label result) {
        leadingCell = cells.get(0);
        cell.setOperator(operator);
        cell.setResult(result);
    }

    public ArrayList<Cell> getCells() {
        return cells;

    }

    public Cell getLeadingCell() {
        return leadingCell;
    }


    public boolean addCells(Cell... all) {
        for (Cell cell : all) {
            getCells().add(cell);
        }
        for (int i = 0; i < getCells().size()-1; i++) {
            if (getCells().get(i) != null) {
                if (!getCells().get(i).isAdjacentTo(getCells().get(i + 1))) {
                    getCells().clear();
                    return false;
                }
            }
        }
        return true;
    }


    public Cell findAdjacent(Cell cell) {
        Iterator<Cell> i = getCells().iterator();
        while (!getCells().contains(cell.isAdjacentTo(i.next()))) {
            return cell;
        }
        return null;
    }

    public void setBorer() {
        for (Cell cell : getCells()) {
            cell.setBorder(new Border(stroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4, 4, 4, 4), Insets.EMPTY)));
        }
    }


}

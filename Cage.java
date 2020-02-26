import javafx.geometry.BoundingBox;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Cage extends Shape {

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


    public void addCellsToCage(Cell... all) {
       ArrayList tmp = new ArrayList<>();
        for (Cell cell : all) {
            if(!checkIfAdjacent(cell)) tmp.add(cell);
            else getCells().add(cell);
        }
        tmp.clear();
    }
    public boolean checkIfAdjacent(Cell cell){
        int count = 0;
        for (Cell thisCell : getCells()){
            if (!cell.getAdjacentCells().contains(thisCell) && getCells() != null) count++;
            if(getCells().size()==count) return false;
        }
        return true;
    }

}

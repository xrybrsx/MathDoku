import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.util.*;


public class GUI extends Application {

    private TextField textField;
    private ArrayList<TextField> textFields;
    private Cell cell;
    private int hardnessLevel; /* Number of columns and rows. */
    private HBox hBox;
    private VBox menuBar;
    private ArrayList<Cell> gridCells;/* Contains every Cell in the grid cell, sorted (column,row) */
    private ArrayList<Button> numberButtons;
    private UndoRedo tempNum;



    GUI(int hardnessLevel) {
        this.setHardnessLevel(hardnessLevel);
    }

    public static void main(String[] args) {
        launch(args);

    }

    public TextField getTextField() {
        return textField;
    }

    public ArrayList<TextField> getTextFields() {
        return textFields;
    }

    public Cell getCell(int column, int row) {
        Cell cell = new Cell(column, row);
        return cell;
    }

    public int getHardnessLevel() {
        return hardnessLevel;
    }

    public HBox getHBox() {
        return hBox;
    }

    public VBox getMenuBar() {
        return menuBar;
    }

    public ArrayList<Button> getNumberButtons() {
        return numberButtons;
    }

    public void start(Stage primaryStage) {

        /* GridPane with visible grids as a base for the MathDoku. */
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setGridLinesVisible(true);
        pane.setAlignment(Pos.CENTER);
        pane.setMinSize(300,300);

        /*Creating a VBox in each grid cell, adding constraints and text field in each one.
         * Nested for-loop for n columns and n rows according to hardness level.*/
        setGridCells(new ArrayList<Cell>());
        setTextFields(new ArrayList<>());
        for (int i = 0; i < getHardnessLevel(); i++) {
            ColumnConstraints column = new ColumnConstraints(20, 20, Double.MAX_VALUE);
            RowConstraints row = new RowConstraints(20, 20, Double.MAX_VALUE);
            row.setVgrow(Priority.ALWAYS);            //creating constraints and giving them priority
            column.setHgrow(Priority.ALWAYS);         //allows my pane to grow with the size of the scene
            pane.getColumnConstraints().add(column);
            pane.getRowConstraints().add(row);
            for (int j = 0; j < getHardnessLevel(); j++) {
                pane.add(cell = new Cell(i,j), i, j);
                getGridCells().add(cell);
                cell.getChildren().add(textField = new TextField());
                cell.setAlignment(Pos.BOTTOM_CENTER);
                getTextField().setPrefHeight(10);
                getTextFields().add(getTextField());
                setTextFieldLimit(getTextField()); // restrict text input to size 1 and to numbers only
            }
        }
        /* The HBox contains the available buttons with a number to put in a cell. */
        sethBox(new HBox());
        Button number;
        setNumberButtons(new ArrayList<>());
        for (int i = 1; i < getHardnessLevel() + 1; i++) {
            getHBox().getChildren().add(number = new Button(Integer.toString(i)));
            getNumberButtons().add(number);
        }

        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        getHBox().getChildren().addAll(undo, redo);
        getHBox().setAlignment(Pos.CENTER);
        getHBox().setPadding(new Insets(10, 10, 10, 10));
        getHBox().setSpacing(10);


        /* VBox containing essential buttons. */
        setMenuBar(new VBox());
        Button clearAll = new Button("Clear all");
        clearAll.setOnAction(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to clear all?");
            alert.setHeaderText("Warning!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
                clearAll();
            else alert.close();

        });
        getMenuBar().getChildren().addAll(new Button("New Game"), clearAll,
                new Button("Hint"));
        getMenuBar().setAlignment(Pos.CENTER);
        getMenuBar().setPadding(new Insets(10, 10, 10, 10));
        getMenuBar().setSpacing(10);


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(getHBox());
        borderPane.setRight(getMenuBar());

        Scene scene = new Scene(borderPane, 450, 400);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MathDoku");
        primaryStage.show();

        /* Setting OnAction Event for the buttons with number to add the number on then
         * to the TextField we are currently on. */
        for (Button button : getNumberButtons()) {
            button.setFocusTraversable(false); // this prevents the buttons from taking focus over the current textField
            button.setOnAction(evt -> {
                Node fo = scene.getFocusOwner(); // the current textField
                if (fo instanceof TextInputControl) {
                    ((TextInputControl) fo).setText(button.getText());
                    tempNum.add(button.getText());
                }
            });
        }
        tempNum = new UndoRedo(); //can store only this much numbers
        undo.setFocusTraversable(false);
        undo.setOnAction(e -> {
            Node fo = scene.getFocusOwner(); // the current textField
            if (fo instanceof TextInputControl) {
                ((TextInputControl) fo).setText( tempNum.undo());
            }
        });
        redo.setFocusTraversable(false);
        redo.setOnAction(e -> {
            Node fo = scene.getFocusOwner(); // the current textField
            if (fo instanceof TextInputControl) {
                ((TextInputControl) fo).setText( tempNum.redo());
            }
        });
    }

    public ArrayList<Cell> getGridCells() {
        return gridCells;
    }

    public Cell getGridCell(int i) {
        return getGridCells().get(i);
    }

    /* Adding a listener for when the cursor is in the textField -
     * sets a limit of 1 and only numbers in the input */
    public void setTextFieldLimit(TextField textField) {
        int maxLength = 1;
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (!textField.getText().matches("[0-9]") || Integer.parseInt(textField.getText()) > hardnessLevel || textField.getText() == null) { // if the text doesn't match integers 0-9:
                    String s = Objects.requireNonNull(textField.getText()).substring(0, 0);
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
                }
                else {
                    if((c== KeyEvent.VK_BACK_SPACE)||
                            (c==KeyEvent.VK_DELETE) ||
                            (c==KeyEvent.VK_ENTER)||
                            (c==KeyEvent.VK_TAB))
                        return;
                    else {
                        textField.setText(null);
                        textField.setText(textField.getText().replaceAll(oldValue,newValue));
                    }
                }
            }
        });
    }
    public void clearAll(){
        for (TextField textField : textFields )
        textField.clear();
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public void setTextFields(ArrayList<TextField> textFields) {
        this.textFields = textFields;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setHardnessLevel(int hardnessLevel) {
        this.hardnessLevel = hardnessLevel;
    }

    public void sethBox(HBox hBox) {
        this.hBox = hBox;
    }

    public void setMenuBar(VBox menuBar) {
        this.menuBar = menuBar;
    }

    public void setGridCells(ArrayList<Cell> gridCells) {
        this.gridCells = gridCells;
    }

    public void setNumberButtons(ArrayList<Button> numberButtons) {
        this.numberButtons = numberButtons;
    }
}

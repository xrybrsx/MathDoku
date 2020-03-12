import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;


public class Controller extends Application {

    private TextField textField;
    private ArrayList<TextField> textFields;
    private Cell cell;
    private ArrayList<Cage> cages;
    private int hardnessLevel; /* Number of columns and rows. */
    private HBox hBox;
    private VBox menuBar;
    private ArrayList<Cell> gridCells; /* Contains every Cell in the grid cell, sorted (column,row) */
    private ArrayList<Button> numberButtons;
    private GridPane pane;
    private Button undo;
    private Button redo;
    private Hint hint;
    private ArrayList<TextField> getRow;
    private ArrayList<TextField> getColumn;
    private ToggleButton hintButton;

    Controller(int hardnessLevel) {
        this.setHardnessLevel(hardnessLevel);

    }

    public static void main(String[] args) {
        launch(args);

    }

    public Button getUndo() {
        return undo;
    }

    public void setUndo(Button undo) {
        this.undo = undo;
    }

    public Button getRedo() {
        return redo;
    }

    public void setRedo(Button redo) {
        this.redo = redo;
    }

    public Hint getHint() {
        return hint;
    }

    public ToggleButton getHintButton() {
        return hintButton;
    }

    public ArrayList<TextField> getColumn() {
        return getColumn;
    }

    public ArrayList<Cage> getCages() {
        return cages;
    }

    public void setCages(ArrayList<Cage> cages) {
        this.cages = cages;
    }

    public GridPane getPane() {
        return pane;
    }

    public void setPane(GridPane pane) {
        this.pane = pane;
    }

    public ArrayList<TextField> getTextFields() {
        return textFields;
    }

    public void setTextFields(ArrayList<TextField> textFields) {
        this.textFields = textFields;
    }

    public Cell getCell(int column, int row) {
        if ((column >= 0 && column <= getHardnessLevel()) && (row >= 0 && row <= getHardnessLevel())) {
            int i = row * hardnessLevel + column;
            return getGridCell(i);
        } else return null;
    }

    public int getHardnessLevel() {
        return hardnessLevel;
    }

    public void setHardnessLevel(int hardnessLevel) {
        this.hardnessLevel = hardnessLevel;
    }

    public HBox getHBox() {
        return hBox;
    }

    public VBox getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(VBox menuBar) {
        this.menuBar = menuBar;
    }

    public ArrayList<Button> getNumberButtons() {
        return numberButtons;
    }

    public void setNumberButtons(ArrayList<Button> numberButtons) {
        this.numberButtons = numberButtons;
    }

    public void start(Stage primaryStage) {

        /* GridPane with visible grids as a base for the MathDoku. */
        pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setGridLinesVisible(true);
        pane.setAlignment(Pos.CENTER);
        pane.setMinSize(450, 450);
        pane.setBackground(Background.EMPTY);

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
                pane.add(cell = new Cell(j, i), j, i);
                getGridCells().add(cell);
                getCell(j, i).setController(this);
                getCell(j, i).setFlow(new FlowPane());
                getCell(j, i).setOperator(new Label());
                getCell(j, i).setResult(new Label());
                getCell(j, i).setTextField(new TextField(""));
                getCell(j, i).getTextField().setAlignment(Pos.BOTTOM_CENTER);
                getTextFields().add(getCell(j, i).getTextField());
                try {
                    getCell(j, i).setTextFieldLimit();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                // restrict text input to size 1 and to numbers only

            }
        }
        /* The HBox contains the available buttons with a number to put in a cell. */
        sethBox(new HBox());
        Button number;
        setNumberButtons(new ArrayList<>());
        for (int i = 1; i < getHardnessLevel() + 1; i++) {
            getHBox().getChildren().add(number = new Button(Integer.toString(i)));
            getNumberButtons().add(number);
            setEffect(number);
        }

        setUndo(new Button("Undo"));
        setRedo(new Button("Redo"));
        setEffect(getUndo(), getRedo());
        getHBox().getChildren().addAll(getUndo(), getRedo());
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
        Button newGame = new Button("New Game");
        newGame.setOnAction(e -> {
            Stage stage = new Stage();
            NewGameGUI game = new NewGameGUI();
            game.start(stage);
            stage.show();
            primaryStage.close();
        });
        hintButton = new ToggleButton("Show Mistakes");
        hintButton.setOnMouseClicked(e -> {
            hint = new Hint(this);
            if (this.getHintButton().isSelected()) {
               hint.showMistakes();
            } else hint.stop();

        });
        getMenuBar().getChildren().addAll(newGame, clearAll, hintButton);
        setEffect(newGame, clearAll);
        getMenuBar().setAlignment(Pos.CENTER);
        getMenuBar().setPadding(new Insets(10, 10, 10, 10));
        getMenuBar().setSpacing(10);


        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(Background.EMPTY);
        borderPane.setCenter(pane);
        borderPane.setBottom(getHBox());
        borderPane.setRight(getMenuBar());


        Scene scene = new Scene(borderPane, 600, 500);
        primaryStage.setMinHeight(550);
        primaryStage.setMinWidth(600);
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
                }
            });
        }

        undo.setFocusTraversable(false);
        undo.setOnAction(e -> {
            Node fo = scene.getFocusOwner().getParent(); // the current Cell
            Cell cell = (Cell) fo;
            cell.getTextField().setText(cell.undo());
            if (cell.getUndoStack().isEmpty() || cell.getUndoStack().peek().equals(""))
                undo.setDisable(true);
            redo.setDisable(false);
        });
        redo.setFocusTraversable(false);
        redo.setOnAction(e -> {
            Node fo = scene.getFocusOwner().getParent(); // the current Cell
            Cell cell = (Cell) fo;
            cell.getTextField().setText(cell.redo());

            if (cell.getRedoStack().isEmpty() || cell.getRedoStack().peek().equals("") || cell.getRedoStack().isEmpty())
                redo.setDisable(true);
            undo.setDisable(false);
        });
    }

    public ArrayList<Cell> getGridCells() {
        return gridCells;
    }

    public void setGridCells(ArrayList<Cell> gridCells) {
        this.gridCells = gridCells;
    }

    public Cell getGridCell(int i) {
        if (i >= 0 && i < getHardnessLevel() * getHardnessLevel())
            return getGridCells().get(i);
        else return null;
    }

    public void clearAll() {
        for (TextField textField : textFields)
            textField.setText("");
        Node node = textField.getParent();
        Cell cell = (Cell) node;
        cell.getUndoStack().clear();
        cell.getRedoStack().clear();

    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void sethBox(HBox hBox) {
        this.hBox = hBox;
    }

    public ArrayList<TextField> iterateRow(int row) {
        if (row >= 0 && row < getHardnessLevel()) {
            ArrayList list = new ArrayList<TextField>();
            int j = getHardnessLevel() * row;
            for (int i = getHardnessLevel() * row; i < j + getHardnessLevel(); i++) {
                if (!getTextFields().get(i).getText().equals(""))
                    list.add(getTextFields().get(i));
            }
            if (list != null)
                return this.getRow = list;
        }
        return null;
    }

    public ArrayList<TextField> getRowCells() {
        return getRow;
    }

    public ArrayList<TextField> iterateColumn(int column) {
        if (column >= 0 && column < getHardnessLevel()) {
            ArrayList list = new ArrayList<TextField>();
            int j = 0;
            for (int i = column; i < getHardnessLevel() * getHardnessLevel(); i = i + getHardnessLevel()) {
                if (!getCell(column, j).getTextField().getText().equals(""))
                    list.add(getCell(column, j).getTextField());
                j++;
            }
            if (list != null)
                return this.getColumn = list;
        }
        return null;
    }

    public void setEffect(Button... all) {
        DropShadow shadow = new DropShadow();
        for (Button button : all) {
            //Adding the shadow when the mouse cursor is on
            button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            button.setEffect(shadow);
                        }
                    });
            //Removing the shadow when the mouse cursor is off
            button.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            button.setEffect(null);

                        }
                    });
        }
    }
}

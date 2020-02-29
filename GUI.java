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


public class GUI extends Application {

    private TextField textField;
    private ArrayList<TextField> textFields;
    private Cell cell;
    private ArrayList<Cage> cages;
    private int hardnessLevel; /* Number of columns and rows. */
    private HBox hBox;
    private VBox menuBar;
    private ArrayList<Cell> gridCells;
    /* Contains every Cell in the grid cell, sorted (column,row) */
    private ArrayList<TextField> rowText;
    private ArrayList<Button> numberButtons;
    private UndoRedo tempNum;
    private Cage cage;
    private GridPane pane;

    public Hint getHint() {
        return hint;
    }

    private Hint hint;
    private ArrayList<TextField> getRow;
    private ArrayList<TextField> getColumn;

    public ToggleButton getHintButton() {
        return hintButton;
    }

    private ToggleButton hintButton;


    GUI(int hardnessLevel) {
        this.setHardnessLevel(hardnessLevel);

    }

    public static void main(String[] args) {
        launch(args);

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
        pane.setMinSize(300, 300);
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
                getCell(j, i).setGui(this);
                getCell(j, i).setFlow(new FlowPane());
                getCell(j, i).setOperator(new Label());
                getCell(j, i).setResult(new Label());
                getCell(j, i).setTextField(new TextField(""));
                getCell(j, i).getTextField().setAlignment(Pos.BOTTOM_CENTER);
                getCell(j, i).getTextField().setPrefHeight(10);
                getTextFields().add(getCell(j, i).getTextField());
                getCell(j, i).setTextFieldLimit();
                // restrict text input to size 1 and to numbers only
                //   getCell(j, i).getOperator().setAlignment(Pos.BASELINE_CENTER);

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

        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        setEffect(undo,redo);
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
        Button newGame = new Button("New Game");
        newGame.setOnAction(e -> {
            Stage stage = new Stage();
            GUI_NewGame game = new GUI_NewGame();
            game.start(stage);
            stage.show();
        });
        hintButton = new ToggleButton("Hint");
        hintButton.setOnMouseClicked(e ->{
                hint = new Hint(this);
        if (this.getHintButton().isSelected()){
            hint.checkAllColumns();
            hint.checkAllRows();
        }else hint.stop();

    });
        getMenuBar().getChildren().addAll(newGame, clearAll, hintButton);
        setEffect(newGame,clearAll);
        getMenuBar().setAlignment(Pos.CENTER);
        getMenuBar().setPadding(new Insets(10, 10, 10, 10));
        getMenuBar().setSpacing(10);


        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(Background.EMPTY);
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
                ((TextInputControl) fo).setText(tempNum.undo());
            }
        });
        redo.setFocusTraversable(false);
        redo.setOnAction(e -> {
            Node fo = scene.getFocusOwner(); // the current textField
            if (fo instanceof TextInputControl) {
                ((TextInputControl) fo).setText(tempNum.redo());
            }
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
            textField.clear();
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void sethBox(HBox hBox) {
        this.hBox = hBox;
    }

    public void iterateRow(int row) {
        if (row >= 0 && row < getHardnessLevel()) {
            ArrayList list = new ArrayList<TextField>();
            int j = getHardnessLevel() * row;
            for (int i = getHardnessLevel() * row ; i < j + getHardnessLevel(); i++) {
                list.add(getTextFields().get(i));
            }
            this.getRow = list;
        }
    }

    public ArrayList<TextField> getRow() {
        return getRow;
    }

    public ArrayList<TextField> iterateColumn(int column) {
        if (column >= 0 && column < getHardnessLevel()) {
            ArrayList list = new ArrayList<TextField>();
            int j = 0;
            for (int i = column ; i < getHardnessLevel()*getHardnessLevel(); i = i + getHardnessLevel()) {
                list.add(getCell(column,j).getTextField());
                j++;
            }
            return this.getColumn = list;
        }
        return null;
    }
    public void setEffect(Button ...all){
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

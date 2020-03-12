import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class GameReader {

    BufferedReader reader;
    int level; //difficulty level (ex. 4x4 game - level is 4)
    ArrayList<String> lines; //lines of text to read from

    public GameReader(TextArea area) throws Exception {
        String string = area.getText();
        lines = new ArrayList<>();
        String[] str = string.split("\n");
        for (String s : str)
            lines.add(s);
        level = countCells();
        read();
    }

    public GameReader(String filepath) throws Exception {
        try {
            reader = new BufferedReader((new FileReader(filepath)));
            lines = new ArrayList<>();
            while (reader.ready())
                lines.add(reader.readLine());
        } catch (IOException e) {
            reader = null;
            System.out.println("File doesn't exist!");
        }
//        assert text != null;
        level = countCells();
//        file = text.split("\\s|,|\n");
        read();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    // determines level from number of cells
    public int countCells() {
        int count = 0;
        for (String string : lines) {
            for (int i = 0; i < string.length(); i++) {
                char ch = string.charAt(i);
                if (ch == ',') count++; // counts cell using ","
            }
        }
        count = lines.size() + count;
        count = (int) Math.sqrt(count);
        return count;
    }


    public void read() throws Exception {
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<String> operators = new ArrayList<>();
        ArrayList<Cage> cages = new ArrayList<>();
        Stage stage = new Stage();
        Controller controller = new Controller(level);
        controller.start(stage);

        for (String string : lines) {
            if (string.contains(" ")) {
                String[] split = string.split(" ", 2);
                String[] split2 = split[1].split(",");
                labels.add(split[0]);
                Cage cage = new Cage();
                cage.setCells();
                cages.add(cage);
                if (split2[0].contains(" "))
                    split2[0] = split2[0].toString().replaceAll("\\s", "");
                for (int i = 0; i < split2.length; i++) {
                    cage.addCellsToCage(controller.getGridCell(Integer.parseInt(split2[i]) - 1));
                }
            }
        }
        boolean cagesFaulty = true;
        for (Cage cage : cages) {
            cagesFaulty = cage.checkCageRight() && cagesFaulty;
        }
            if (!cagesFaulty) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Input text for game creation is wrong!" +
                        "\n Cells in grid are not adjacent!" +
                        "\n Try again, sorry.");
                alert.showAndWait();
                    alert.close();
                    controller.stop();
                    stage.close();

            }

            controller.setCages(cages);
            for (String stringNum : labels) {
                stringNum = stringNum.replaceAll("\\D+", "");
                numbers.add(stringNum);

            }
            // setting operator labels on leading cells
            for (String stringOp : labels) {
                stringOp = stringOp.replaceAll("[0-9]", "");
                operators.add(stringOp);
            }
            for (int i = 0; i < cages.size(); i++) {
                cages.get(i).setLeadingCell(numbers.get(i), operators.get(i));
            }
            for (Cage cage : cages) cage.setBorder();


        }
    }




import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameReader {

    BufferedReader reader;
    String[] file;
    GUI_NewGame newGame;
    int level;
    ArrayList<String> lines;

    public GameReader(String filepath) throws IOException {
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

    public int countCells() {
        int count = 0;
        for (String string:lines) {
            for (int i = 0; i < string.length(); i++) {
              char ch = string.charAt(i);
              if (ch == ',')count++;
            }
        }
        count = lines.size() + count;
        count = (int) Math.sqrt(count);
        return count;
    }



    public void read() {
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<String> operators = new ArrayList<>();
        ArrayList<Cage> cages = new ArrayList<>();
        Stage stage = new Stage();
        GUI gui = new GUI(level);
        gui.start(stage);

        for(String string: lines){
            if (string.contains(" ")){
                String[] split = string.split(" ", 2);
                String[] split2 = split[1].split(",");
                labels.add(split[0]);
                Cage cage = new Cage();
                cage.setCells(new ArrayList<>());
                cages.add(cage);
                if (split2[0].contains(" "))
                    split2[0] = split2[0].toString().replaceAll("\\s","");
                for (int i = 0; i < split2.length; i++) {
                    cage.addCellsToCage(gui.getGridCell(Integer.parseInt(split2[i]) - 1));
                }
            }
        }
        for (String stringNum : labels) {
            stringNum = stringNum.replaceAll("\\D+", "");
            numbers.add(stringNum);

        }
        for (String stringOp : labels) {
            stringOp = stringOp.replaceAll("[0-9]", "");
            operators.add(stringOp);
        }
        for (int i = 0; i < cages.size(); i++) {
            cages.get(i).setLeadingCell(numbers.get(i), operators.get(i));
        }
        for (Cage cage: cages) cage.setBorder();


    }
}



import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameReader {

    BufferedReader reader;
    String text;
    String[] file;
    GUI_NewGame newGame;
    int level;

    public GameReader(String filepath) throws IOException {
        try {
            reader = new BufferedReader((new FileReader(filepath)));
            text = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            reader = null;
            System.out.println("File doesn't exist!");
        }
        assert text != null;
        level = countCells(text);
        file = text.split("\\s|,|\n");
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
    public int countCells(String str){
        String[] number = str.split("|,|\n");
        int count = 0;
        for (String string : number){
            if (string.equals(" ") || string.equals(",")) count++;
        }
        count = (int) Math.sqrt(count);
        return count;
    }


   public void read(){
        ArrayList<String> labels = new ArrayList<>();
       ArrayList<String> numbers = new ArrayList<>();
       ArrayList<String> operators = new ArrayList<>();
       ArrayList<Cage> cages = new ArrayList<>();
        Stage stage = new Stage();
        GUI gui = new GUI(level);
        gui.start(stage);

        for (String str : file) {
            if (isInteger(str)) {
                while (isInteger(str)){
                Cage cage = new Cage();
                cage.setCells(new ArrayList<>());
                cage.addCellsToCage(gui.getGridCell(Integer.parseInt(str) - 1));
                cages.add(cage);}
            } else {
                labels.add(str);
            }
        }
            for (String stringNum : labels){
              stringNum = stringNum.replaceAll("\\D+","");
              numbers.add(stringNum);

            }
            for (String stringOp : labels){
               stringOp= stringOp.replaceAll("[0-9]","");
                operators.add(stringOp);
            }
            for (int i = 0; i<cages.size(); i++){
                cages.get(i).setLeadingCell(new Label(numbers.get(i)), new Label(operators.get(i)));
            }


        }
    }



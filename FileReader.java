import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Collectors;

public class FileReader extends Reader {

    BufferedReader reader;
    String text;
    GUI_NewGame newGame;

           public FileReader(File filepath) throws IOException {
               FileReader fr =
                       new FileReader(filepath);

               int i;
               while ((i=fr.read()) != -1)
                   System.out.print(i);
           }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}


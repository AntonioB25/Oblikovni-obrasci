package Sources;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * @author Antonio Bukovac
 */
public class FileSource implements Source {

    private File inputFile;
    private Scanner scanner;


    public FileSource(File inputFile) throws FileNotFoundException {
       this.inputFile = inputFile;
        scanner = new Scanner(inputFile);
    }


    @Override
    public int getNumber() {
        return scanner.hasNextInt() ? scanner.nextInt() : -1;
    }

}

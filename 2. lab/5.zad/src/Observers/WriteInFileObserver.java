package Observers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class WriteInFileObserver implements Observer {
    File outputFile;

    public WriteInFileObserver(File outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void update(List<Integer> numbers) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try (FileWriter writer = new FileWriter(outputFile,true)) {
            String record = numbers.toString() + " " + dtf.format(now) + "\n";

            writer.write(record);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

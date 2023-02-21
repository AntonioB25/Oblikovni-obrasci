import NumberSequence.NumberSequence;
import Observers.AverageObserver;
import Observers.MeanObserver;
import Observers.Observer;
import Observers.SumObserver;
import Observers.WriteInFileObserver;
import Sources.FileSource;
import Sources.KeyboardSource;
import Sources.Source;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Antonio Bukovac
 */
public class Main {

    public static void main(String[] args) {
        File inputFile = new File("src/Files/inputFile.txt");
        File outputFile = new File("src/Files/outputFile.txt");

        Source source = new KeyboardSource();
        Observer[] observers = {new MeanObserver(), new SumObserver(), new WriteInFileObserver(outputFile) };
        Observer avgObserver = new AverageObserver();

        try{
            source = new FileSource(inputFile);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        NumberSequence ns = new NumberSequence(source, observers);
        ns.subscribe(avgObserver);

        try{
            ns.go();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}

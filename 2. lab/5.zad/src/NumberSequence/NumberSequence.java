package NumberSequence;

import Observers.Observer;
import Sources.Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * @author Antonio Bukovac
 */

public class NumberSequence {

    Source         numbersSource;

    List<Integer> numbers;
    List<Observer> observerList;


    public NumberSequence(Source numbersSource, Observer...observers) {
        this.numbersSource = numbersSource;
        numbers = new ArrayList<>();
        observerList = new ArrayList<>(Arrays.asList(observers));
    }

    public void notifyObservers(){
        for (Observer o: observerList){
            o.update(numbers);
        }
    }

    public void go() throws InterruptedException {

        while(true){
            int inputNumber = numbersSource.getNumber();
            if(inputNumber < 0){
                break;
            }
            numbers.add(inputNumber);
            notifyObservers();

            Thread.sleep(1000);
        }
        System.out.println("No more numbers!");
    }


    public void subscribe(Observer observer){
        observerList.add(observer);
    }

    public void unSubscribe(Observer observer){
        observerList.remove(observer);
    }

    public Source getNumbersSource() {
        return numbersSource;
    }

}

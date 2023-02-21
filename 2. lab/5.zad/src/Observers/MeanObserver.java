package Observers;

import java.util.Collections;
import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class MeanObserver implements Observer{

    @Override
    public void update(List<Integer> numbers) {
        Collections.sort(numbers);
        int size = numbers.size();

        System.out.print("Mean is: ");
        if(size % 2 == 1){
            System.out.println(numbers.get(size/2));
        } else {
            System.out.println(String.format("%.2f", (numbers.get(size/2 - 1) + numbers.get(size/2)) / 2f));
        }
    }

}

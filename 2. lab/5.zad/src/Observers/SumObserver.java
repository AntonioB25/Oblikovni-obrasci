package Observers;

import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class SumObserver implements Observer {

    @Override
    public void update(List<Integer> numbers) {
        int sum = numbers.stream().mapToInt(a -> a).sum();
        System.out.println("Sum is: " + sum);
    }

}

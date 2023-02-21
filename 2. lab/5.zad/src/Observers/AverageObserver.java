package Observers;

import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class AverageObserver implements Observer {

    @Override
    public void update(List<Integer> numbers) {
        double average = numbers.stream().mapToDouble(a->a).average().getAsDouble();
        System.out.println("Average value is: " +String.format("%.2f",average));
    }
}

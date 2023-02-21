package Observers;

import NumberSequence.NumberSequence;

import java.util.List;

/**
 * @author Antonio Bukovac
 */
public interface Observer {

    void update(List<Integer> numbers);

}

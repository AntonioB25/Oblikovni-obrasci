package generators;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class FibonacciGenerator implements IntegerGenerator {

    private int numberOfElements;

    public FibonacciGenerator(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public List<Integer> generateSequence() {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(0);
        sequence.add(1);

        int n1 = 0;
        int n2 = 1;
        int n3;

        for (int i = 2; i < numberOfElements; i++) {
            n3 = n1 + n2;
            sequence.add(n3);

            n1 = n2;
            n2 = n3;
        }
        return sequence;
    }

}

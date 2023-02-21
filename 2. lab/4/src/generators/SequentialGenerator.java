package generators;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class SequentialGenerator implements IntegerGenerator {

    private int upperLimit;
    private int lowerLimit;
    private int step;

    /**
     * @param upperLimit upper limit of your (inclusive)
     * @param lowerLimit lover limit of interval (inclusive)
     * @param step step between numbers
     */
    public SequentialGenerator(int upperLimit, int lowerLimit, int step) {
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.step = step;
    }

    @Override
    public List<Integer> generateSequence() {

        List<Integer> sequence = new ArrayList<>();

        for (int i = lowerLimit; i <= upperLimit; i += step) {
            sequence.add(i);
        }

        return sequence;
    }

}

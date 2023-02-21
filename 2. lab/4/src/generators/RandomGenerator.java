package generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Antonio Bukovac
 */
public class RandomGenerator implements IntegerGenerator {

    private int    numberOfElements;
    private double mean;
    private double standardDeviation;

    public RandomGenerator(int numberOfElements, double mean, double standardDeviation) {
        this.numberOfElements = numberOfElements;
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    @Override
    public List<Integer> generateSequence() {
        List<Integer> sequence = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfElements; i++) {
            int number = (int) (random.nextGaussian() * standardDeviation + mean);
            sequence.add(number);
        }

        return sequence;
    }

}

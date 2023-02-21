import generators.IntegerGenerator;
import percentileCalculators.Percentile;

import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class DistributionTester {

    private IntegerGenerator generator;
    private Percentile       percentile;

    public DistributionTester(IntegerGenerator generator, Percentile percentile) {
        this.generator = generator;
        this.percentile = percentile;
    }

    public void showPercentiles() {
        List<Integer> sequenceOfIntegers = generator.generateSequence();
        System.out.println("List: " + sequenceOfIntegers );

        for (int i = 10; i < 100; i += 10) {
            int ithPercentile = percentile.getPercentile(sequenceOfIntegers, i);
            System.out.println(i + "-th Percentile is: " + ithPercentile);
        }
    }
}

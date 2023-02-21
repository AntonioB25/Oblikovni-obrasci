import generators.FibonacciGenerator;
import generators.RandomGenerator;
import generators.SequentialGenerator;
import percentileCalculators.LinearInterpolationPercentile;
import percentileCalculators.NearestRankPercentile;

import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class Main {

    public static void main(String[] args) {

        List<Integer> lista = List.of(1, 10, 50);

        //generatori
        FibonacciGenerator fibonacciGenerator = new FibonacciGenerator(10);
        RandomGenerator randomGenerator = new RandomGenerator(10,8.75,5.02);
        SequentialGenerator sequentialGenerator = new SequentialGenerator(30,13,3);
        //određivanje percentila
        LinearInterpolationPercentile interpolationPercentile = new LinearInterpolationPercentile();
        NearestRankPercentile nearestRankPercentile = new NearestRankPercentile();

        //primjer iz zadataka
        System.out.println("80-th percentile by interpolation of list [1,10,50]: ");
        System.out.println(interpolationPercentile.getPercentile(lista,80));
        System.out.println();

        DistributionTester distributionTester = new DistributionTester(randomGenerator,interpolationPercentile);
        distributionTester.showPercentiles();

    }

}

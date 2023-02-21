package percentileCalculators;

import java.util.List;

/**
 * @author Antonio Bukovac
 */
public interface Percentile {

    int getPercentile(List<Integer> distributionValues, int percentile);

}

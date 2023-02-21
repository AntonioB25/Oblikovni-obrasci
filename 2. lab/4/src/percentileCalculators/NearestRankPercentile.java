package percentileCalculators;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Antonio Bukovac
 */
public class NearestRankPercentile implements Percentile {

    public NearestRankPercentile() {
    }

    @Override
    public int getPercentile(List<Integer> distributionValues, int percentile) {
        if (distributionValues.isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        List<Integer> sorted = distributionValues.stream().sorted().collect(Collectors.toList());
        int index = (int) Math.ceil(percentile * sorted.size() / 100f);

        return sorted.get(index - 1); // jer indeksi idu od 0
    }

}

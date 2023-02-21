package percentileCalculators;

import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class LinearInterpolationPercentile implements Percentile {

    public LinearInterpolationPercentile() {
    }

    @Override
    public int getPercentile(List<Integer> distributionValues, int percentile) {
        if (distributionValues.isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        int result = 0;
        int N = distributionValues.size();

        double fst = 100 * (0 + 0.5) / N;
        double nth = 100 * (N - 0.5) / N;

        if (percentile < fst) {
            return distributionValues.get(0);
        }
        if (percentile > nth) {
            return distributionValues.get(N - 1);
        }

        for (int i = 0; i < N; i++) {
            double first = 100 * (i + 0.5) / N;
            double second = 100 * (i + 1 + 0.5) / N;

            if (first < percentile && second > percentile) {
                result = (int) (distributionValues.get(i)
                    + N * (percentile - first) * (distributionValues.get(i + 1) - distributionValues.get(i)) / 100);
                break;
            }
        }
        return result;
    }

}

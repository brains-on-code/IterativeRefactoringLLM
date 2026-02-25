package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class BandwidthAllocation {

    private BandwidthAllocation() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the maximum total value achievable given a total bandwidth
     * and arrays of per-user bandwidth requirements and corresponding values.
     * This uses a greedy strategy based on value-per-bandwidth ratio.
     *
     * @param bandwidth total available bandwidth
     * @param users     bandwidth required by each user
     * @param values    value contributed by each user
     * @return maximum achievable total value
     */
    public static int maxValue(int bandwidth, int[] users, int[] values) {
        int n = users.length;

        // ratio[i][0] = original index of user
        // ratio[i][1] = value-per-bandwidth ratio for that user
        double[][] ratio = new double[n][2];

        for (int i = 0; i < n; i++) {
            ratio[i][0] = i;
            ratio[i][1] = (double) values[i] / users[i];
        }

        // Sort users by descending value-per-bandwidth ratio
        Arrays.sort(ratio, (a, b) -> Double.compare(b[1], a[1]));

        int maxValue = 0;

        // Allocate bandwidth greedily based on sorted ratios
        for (int i = 0; i < n && bandwidth > 0; i++) {
            int index = (int) ratio[i][0];
            int requiredBandwidth = users[index];
            int userValue = values[index];
            double valuePerBandwidth = ratio[i][1];

            if (bandwidth >= requiredBandwidth) {
                // Allocate full bandwidth to this user
                maxValue += userValue;
                bandwidth -= requiredBandwidth;
            } else {
                // Allocate remaining bandwidth fractionally
                maxValue += (int) (valuePerBandwidth * bandwidth);
                break;
            }
        }

        return maxValue;
    }
}
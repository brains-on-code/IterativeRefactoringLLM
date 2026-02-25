package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class BandwidthAllocation {

    private BandwidthAllocation() {
        // Prevent instantiation
    }

    /**
     * Computes the maximum total value achievable given a total bandwidth
     * and arrays of per-user bandwidth requirements and corresponding values.
     * Uses a greedy strategy based on value-per-bandwidth ratio.
     *
     * @param bandwidth total available bandwidth
     * @param users     bandwidth required by each user
     * @param values    value contributed by each user
     * @return maximum achievable total value
     */
    public static int maxValue(int bandwidth, int[] users, int[] values) {
        int n = users.length;

        // ratios[i][0] = original index of user
        // ratios[i][1] = value-per-bandwidth ratio for that user
        double[][] ratios = new double[n][2];

        for (int i = 0; i < n; i++) {
            ratios[i][0] = i;
            ratios[i][1] = (double) values[i] / users[i];
        }

        // Sort users by descending value-per-bandwidth ratio
        Arrays.sort(ratios, (a, b) -> Double.compare(b[1], a[1]));

        int maxValue = 0;

        // Greedily allocate bandwidth based on sorted ratios
        for (int i = 0; i < n && bandwidth > 0; i++) {
            int userIndex = (int) ratios[i][0];
            int requiredBandwidth = users[userIndex];
            int userValue = values[userIndex];
            double valuePerBandwidth = ratios[i][1];

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
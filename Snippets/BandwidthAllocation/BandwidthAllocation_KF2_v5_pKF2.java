package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class BandwidthAllocation {

    private BandwidthAllocation() {
        // Utility class; prevent instantiation
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

        // userRatios[i][0] = user index, userRatios[i][1] = value-per-bandwidth ratio
        double[][] userRatios = new double[n][2];

        for (int i = 0; i < n; i++) {
            userRatios[i][0] = i;
            userRatios[i][1] = (double) values[i] / users[i];
        }

        Arrays.sort(userRatios, (a, b) -> Double.compare(b[1], a[1]));

        int totalValue = 0;

        for (int i = 0; i < n && bandwidth > 0; i++) {
            int userIndex = (int) userRatios[i][0];
            int requiredBandwidth = users[userIndex];
            int userValue = values[userIndex];
            double valuePerBandwidth = userRatios[i][1];

            if (bandwidth >= requiredBandwidth) {
                totalValue += userValue;
                bandwidth -= requiredBandwidth;
            } else {
                totalValue += (int) (valuePerBandwidth * bandwidth);
                break;
            }
        }

        return totalValue;
    }
}
package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;


public final class BandwidthAllocation {
    private BandwidthAllocation() {
    }


    public static int maxValue(int bandwidth, int[] users, int[] values) {
        int n = users.length;
        double[][] ratio = new double[n][2];

        for (int i = 0; i < n; i++) {
            ratio[i][0] = i;
            ratio[i][1] = (double) values[i] / users[i];
        }

        Arrays.sort(ratio, (a, b) -> Double.compare(b[1], a[1]));

        int maxValue = 0;
        for (int i = 0; i < n; i++) {
            int index = (int) ratio[i][0];
            if (bandwidth >= users[index]) {
                maxValue += values[index];
                bandwidth -= users[index];
            } else {
                maxValue += (int) (ratio[i][1] * bandwidth);
                break;
            }
        }
        return maxValue;
    }
}

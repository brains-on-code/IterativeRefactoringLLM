package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class BandwidthAllocation {

    private BandwidthAllocation() {
        // Utility class; prevent instantiation
    }

    private static class UserAllocation {
        final int index;
        final double valuePerUnit;

        UserAllocation(int index, double valuePerUnit) {
            this.index = index;
            this.valuePerUnit = valuePerUnit;
        }
    }

    public static int maxValue(int bandwidth, int[] users, int[] values) {
        int n = users.length;
        UserAllocation[] allocations = new UserAllocation[n];

        for (int i = 0; i < n; i++) {
            double ratio = (double) values[i] / users[i];
            allocations[i] = new UserAllocation(i, ratio);
        }

        Arrays.sort(allocations, Comparator.comparingDouble(a -> -a.valuePerUnit));

        int totalValue = 0;

        for (UserAllocation allocation : allocations) {
            int index = allocation.index;

            if (bandwidth >= users[index]) {
                totalValue += values[index];
                bandwidth -= users[index];
            } else {
                totalValue += (int) (allocation.valuePerUnit * bandwidth);
                break;
            }
        }

        return totalValue;
    }
}
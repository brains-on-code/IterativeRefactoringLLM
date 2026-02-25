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

    public static int maxValue(int totalBandwidth, int[] bandwidthRequirements, int[] values) {
        int userCount = bandwidthRequirements.length;
        UserAllocation[] allocations = new UserAllocation[userCount];

        for (int i = 0; i < userCount; i++) {
            double valuePerUnit = (double) values[i] / bandwidthRequirements[i];
            allocations[i] = new UserAllocation(i, valuePerUnit);
        }

        Arrays.sort(allocations, Comparator.comparingDouble(a -> -a.valuePerUnit));

        int totalValue = 0;

        for (UserAllocation allocation : allocations) {
            int userIndex = allocation.index;
            int requiredBandwidth = bandwidthRequirements[userIndex];
            int userValue = values[userIndex];

            if (totalBandwidth >= requiredBandwidth) {
                totalValue += userValue;
                totalBandwidth -= requiredBandwidth;
            } else {
                totalValue += (int) (allocation.valuePerUnit * totalBandwidth);
                break;
            }
        }

        return totalValue;
    }
}
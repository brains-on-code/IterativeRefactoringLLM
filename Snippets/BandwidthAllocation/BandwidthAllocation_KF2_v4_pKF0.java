package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class BandwidthAllocation {

    private BandwidthAllocation() {
        // Utility class; prevent instantiation
    }

    private static class UserAllocation {
        final int userIndex;
        final double valuePerUnitBandwidth;

        UserAllocation(int userIndex, double valuePerUnitBandwidth) {
            this.userIndex = userIndex;
            this.valuePerUnitBandwidth = valuePerUnitBandwidth;
        }
    }

    public static int maxValue(int totalBandwidth, int[] bandwidthRequirements, int[] values) {
        int userCount = bandwidthRequirements.length;
        UserAllocation[] allocations = new UserAllocation[userCount];

        for (int i = 0; i < userCount; i++) {
            double valuePerUnit = (double) values[i] / bandwidthRequirements[i];
            allocations[i] = new UserAllocation(i, valuePerUnit);
        }

        Arrays.sort(allocations, Comparator.comparingDouble(a -> -a.valuePerUnitBandwidth));

        int totalValue = 0;

        for (UserAllocation allocation : allocations) {
            if (totalBandwidth <= 0) {
                break;
            }

            int userIndex = allocation.userIndex;
            int requiredBandwidth = bandwidthRequirements[userIndex];
            int userValue = values[userIndex];

            if (totalBandwidth >= requiredBandwidth) {
                totalValue += userValue;
                totalBandwidth -= requiredBandwidth;
            } else {
                totalValue += (int) (allocation.valuePerUnitBandwidth * totalBandwidth);
                break;
            }
        }

        return totalValue;
    }
}
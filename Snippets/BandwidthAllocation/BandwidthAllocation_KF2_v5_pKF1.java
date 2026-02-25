package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class BandwidthAllocation {

    private BandwidthAllocation() {
    }

    public static int maxValue(int totalBandwidth, int[] bandwidthRequirements, int[] userValues) {
        int userCount = bandwidthRequirements.length;
        double[][] userIndexAndValueDensity = new double[userCount][2];

        for (int userIndex = 0; userIndex < userCount; userIndex++) {
            userIndexAndValueDensity[userIndex][0] = userIndex;
            userIndexAndValueDensity[userIndex][1] =
                (double) userValues[userIndex] / bandwidthRequirements[userIndex];
        }

        Arrays.sort(
            userIndexAndValueDensity,
            (firstUser, secondUser) -> Double.compare(secondUser[1], firstUser[1])
        );

        int totalAllocatedValue = 0;
        int remainingBandwidth = totalBandwidth;

        for (int sortedIndex = 0; sortedIndex < userCount; sortedIndex++) {
            int userIndex = (int) userIndexAndValueDensity[sortedIndex][0];
            int requiredBandwidth = bandwidthRequirements[userIndex];
            int userValue = userValues[userIndex];
            double valueDensity = userIndexAndValueDensity[sortedIndex][1];

            if (remainingBandwidth >= requiredBandwidth) {
                totalAllocatedValue += userValue;
                remainingBandwidth -= requiredBandwidth;
            } else {
                totalAllocatedValue += (int) (valueDensity * remainingBandwidth);
                break;
            }
        }

        return totalAllocatedValue;
    }
}
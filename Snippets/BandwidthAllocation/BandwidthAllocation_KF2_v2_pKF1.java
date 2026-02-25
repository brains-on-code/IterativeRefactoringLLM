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

        Arrays.sort(userIndexAndValueDensity, (firstUser, secondUser) -> Double.compare(secondUser[1], firstUser[1]));

        int totalAllocatedValue = 0;
        for (int sortedIndex = 0; sortedIndex < userCount; sortedIndex++) {
            int originalUserIndex = (int) userIndexAndValueDensity[sortedIndex][0];
            int requiredBandwidth = bandwidthRequirements[originalUserIndex];
            int userValue = userValues[originalUserIndex];
            double valueDensity = userIndexAndValueDensity[sortedIndex][1];

            if (totalBandwidth >= requiredBandwidth) {
                totalAllocatedValue += userValue;
                totalBandwidth -= requiredBandwidth;
            } else {
                totalAllocatedValue += (int) (valueDensity * totalBandwidth);
                break;
            }
        }
        return totalAllocatedValue;
    }
}
package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class BandwidthAllocation {

    private BandwidthAllocation() {
    }

    public static int maxValue(int totalBandwidth, int[] bandwidthRequirements, int[] userValues) {
        int userCount = bandwidthRequirements.length;
        double[][] userIndexAndValuePerBandwidth = new double[userCount][2];

        for (int userIndex = 0; userIndex < userCount; userIndex++) {
            userIndexAndValuePerBandwidth[userIndex][0] = userIndex;
            userIndexAndValuePerBandwidth[userIndex][1] =
                (double) userValues[userIndex] / bandwidthRequirements[userIndex];
        }

        Arrays.sort(
            userIndexAndValuePerBandwidth,
            (firstUser, secondUser) -> Double.compare(secondUser[1], firstUser[1])
        );

        int totalAllocatedValue = 0;
        int remainingBandwidth = totalBandwidth;

        for (int sortedUserIndex = 0; sortedUserIndex < userCount; sortedUserIndex++) {
            int originalUserIndex = (int) userIndexAndValuePerBandwidth[sortedUserIndex][0];
            int requiredBandwidth = bandwidthRequirements[originalUserIndex];
            int userValue = userValues[originalUserIndex];
            double valuePerBandwidth = userIndexAndValuePerBandwidth[sortedUserIndex][1];

            if (remainingBandwidth >= requiredBandwidth) {
                totalAllocatedValue += userValue;
                remainingBandwidth -= requiredBandwidth;
            } else {
                totalAllocatedValue += (int) (valuePerBandwidth * remainingBandwidth);
                break;
            }
        }

        return totalAllocatedValue;
    }
}
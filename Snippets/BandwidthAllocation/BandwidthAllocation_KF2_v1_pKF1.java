package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class BandwidthAllocation {

    private BandwidthAllocation() {
    }

    public static int maxValue(int totalBandwidth, int[] userBandwidthRequirements, int[] userValues) {
        int userCount = userBandwidthRequirements.length;
        double[][] valueDensityByUser = new double[userCount][2];

        for (int userIndex = 0; userIndex < userCount; userIndex++) {
            valueDensityByUser[userIndex][0] = userIndex;
            valueDensityByUser[userIndex][1] =
                (double) userValues[userIndex] / userBandwidthRequirements[userIndex];
        }

        Arrays.sort(valueDensityByUser, (first, second) -> Double.compare(second[1], first[1]));

        int accumulatedValue = 0;
        for (int i = 0; i < userCount; i++) {
            int originalUserIndex = (int) valueDensityByUser[i][0];
            int requiredBandwidth = userBandwidthRequirements[originalUserIndex];
            int userValue = userValues[originalUserIndex];
            double valueDensity = valueDensityByUser[i][1];

            if (totalBandwidth >= requiredBandwidth) {
                accumulatedValue += userValue;
                totalBandwidth -= requiredBandwidth;
            } else {
                accumulatedValue += (int) (valueDensity * totalBandwidth);
                break;
            }
        }
        return accumulatedValue;
    }
}
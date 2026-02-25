package com.thealgorithms.dynamicprogramming;

final class HouseRobber {

    private HouseRobber() {
    }

    public static int maxRobbedAmountWithDPArray(int[] houseValues) {
        if (houseValues.length == 0) {
            return 0;
        }

        int n = houseValues.length;
        int[] maxRobbedUpToIndex = new int[n];

        maxRobbedUpToIndex[0] = houseValues[0];

        for (int i = 1; i < n; i++) {
            int skipCurrent = maxRobbedUpToIndex[i - 1];

            int robCurrent = houseValues[i];
            if (i > 1) {
                robCurrent += maxRobbedUpToIndex[i - 2];
            }

            maxRobbedUpToIndex[i] = Math.max(robCurrent, skipCurrent);
        }

        return maxRobbedUpToIndex[n - 1];
    }

    public static int maxRobbedAmountOptimized(int[] houseValues) {
        if (houseValues.length == 0) {
            return 0;
        }

        int n = houseValues.length;

        int prevMax = houseValues[0];
        int prevPrevMax = 0;

        for (int i = 1; i < n; i++) {
            int skipCurrent = prevMax;

            int robCurrent = houseValues[i];
            if (i > 1) {
                robCurrent += prevPrevMax;
            }

            int currentMax = Math.max(robCurrent, skipCurrent);

            prevPrevMax = prevMax;
            prevMax = currentMax;
        }

        return prevMax;
    }
}
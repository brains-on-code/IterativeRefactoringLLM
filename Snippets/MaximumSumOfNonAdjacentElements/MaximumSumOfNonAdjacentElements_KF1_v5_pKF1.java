package com.thealgorithms.dynamicprogramming;

final class HouseRobber {

    private HouseRobber() {
    }

    public static int maxRobbedAmountWithDPArray(int[] houseValues) {
        if (houseValues.length == 0) {
            return 0;
        }

        int houseCount = houseValues.length;
        int[] maxRobbedAtHouse = new int[houseCount];

        maxRobbedAtHouse[0] = houseValues[0];

        for (int currentHouse = 1; currentHouse < houseCount; currentHouse++) {
            int skipCurrentHouse = maxRobbedAtHouse[currentHouse - 1];

            int robCurrentHouse = houseValues[currentHouse];
            if (currentHouse > 1) {
                robCurrentHouse += maxRobbedAtHouse[currentHouse - 2];
            }

            maxRobbedAtHouse[currentHouse] = Math.max(robCurrentHouse, skipCurrentHouse);
        }

        return maxRobbedAtHouse[houseCount - 1];
    }

    public static int maxRobbedAmountOptimized(int[] houseValues) {
        if (houseValues.length == 0) {
            return 0;
        }

        int houseCount = houseValues.length;

        int maxRobbedPrevious = houseValues[0];
        int maxRobbedBeforePrevious = 0;

        for (int currentHouse = 1; currentHouse < houseCount; currentHouse++) {
            int skipCurrentHouse = maxRobbedPrevious;

            int robCurrentHouse = houseValues[currentHouse];
            if (currentHouse > 1) {
                robCurrentHouse += maxRobbedBeforePrevious;
            }

            int maxRobbedCurrent = Math.max(robCurrentHouse, skipCurrentHouse);

            maxRobbedBeforePrevious = maxRobbedPrevious;
            maxRobbedPrevious = maxRobbedCurrent;
        }

        return maxRobbedPrevious;
    }
}
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

        for (int currentHouseIndex = 1; currentHouseIndex < houseCount; currentHouseIndex++) {
            int maxIfSkipCurrent = maxRobbedAtHouse[currentHouseIndex - 1];

            int maxIfRobCurrent = houseValues[currentHouseIndex];
            if (currentHouseIndex > 1) {
                maxIfRobCurrent += maxRobbedAtHouse[currentHouseIndex - 2];
            }

            maxRobbedAtHouse[currentHouseIndex] = Math.max(maxIfRobCurrent, maxIfSkipCurrent);
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

        for (int currentHouseIndex = 1; currentHouseIndex < houseCount; currentHouseIndex++) {
            int maxIfSkipCurrent = maxRobbedPrevious;

            int maxIfRobCurrent = houseValues[currentHouseIndex];
            if (currentHouseIndex > 1) {
                maxIfRobCurrent += maxRobbedBeforePrevious;
            }

            int maxRobbedCurrent = Math.max(maxIfRobCurrent, maxIfSkipCurrent);

            maxRobbedBeforePrevious = maxRobbedPrevious;
            maxRobbedPrevious = maxRobbedCurrent;
        }

        return maxRobbedPrevious;
    }
}
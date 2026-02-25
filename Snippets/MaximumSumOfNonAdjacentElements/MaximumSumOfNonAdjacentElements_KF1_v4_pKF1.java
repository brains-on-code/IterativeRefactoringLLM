package com.thealgorithms.dynamicprogramming;

final class HouseRobber {

    private HouseRobber() {
    }

    public static int maxRobbedAmountWithDPArray(int[] houseValues) {
        if (houseValues.length == 0) {
            return 0;
        }

        int numberOfHouses = houseValues.length;
        int[] maxRobbedUpToHouse = new int[numberOfHouses];

        maxRobbedUpToHouse[0] = houseValues[0];

        for (int houseIndex = 1; houseIndex < numberOfHouses; houseIndex++) {
            int maxIfCurrentHouseSkipped = maxRobbedUpToHouse[houseIndex - 1];

            int maxIfCurrentHouseRobbed = houseValues[houseIndex];
            if (houseIndex > 1) {
                maxIfCurrentHouseRobbed += maxRobbedUpToHouse[houseIndex - 2];
            }

            maxRobbedUpToHouse[houseIndex] =
                Math.max(maxIfCurrentHouseRobbed, maxIfCurrentHouseSkipped);
        }

        return maxRobbedUpToHouse[numberOfHouses - 1];
    }

    public static int maxRobbedAmountOptimized(int[] houseValues) {
        if (houseValues.length == 0) {
            return 0;
        }

        int numberOfHouses = houseValues.length;

        int maxRobbedUpToPreviousHouse = houseValues[0];
        int maxRobbedUpToHouseBeforePrevious = 0;

        for (int houseIndex = 1; houseIndex < numberOfHouses; houseIndex++) {
            int maxIfCurrentHouseSkipped = maxRobbedUpToPreviousHouse;

            int maxIfCurrentHouseRobbed = houseValues[houseIndex];
            if (houseIndex > 1) {
                maxIfCurrentHouseRobbed += maxRobbedUpToHouseBeforePrevious;
            }

            int maxRobbedUpToCurrentHouse =
                Math.max(maxIfCurrentHouseRobbed, maxIfCurrentHouseSkipped);

            maxRobbedUpToHouseBeforePrevious = maxRobbedUpToPreviousHouse;
            maxRobbedUpToPreviousHouse = maxRobbedUpToCurrentHouse;
        }

        return maxRobbedUpToPreviousHouse;
    }
}
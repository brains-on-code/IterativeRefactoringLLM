package com.thealgorithms.dynamicprogramming;

final class HouseRobber {

    private HouseRobber() {
    }

    public static int maxRobbedAmountWithDPArray(int[] houseValues) {
        if (houseValues.length == 0) {
            return 0;
        }

        int numberOfHouses = houseValues.length;
        int[] maxRobbedAmountAtHouse = new int[numberOfHouses];

        maxRobbedAmountAtHouse[0] = houseValues[0];

        for (int houseIndex = 1; houseIndex < numberOfHouses; houseIndex++) {
            int maxIfSkipCurrentHouse = maxRobbedAmountAtHouse[houseIndex - 1];

            int maxIfRobCurrentHouse = houseValues[houseIndex];
            if (houseIndex > 1) {
                maxIfRobCurrentHouse += maxRobbedAmountAtHouse[houseIndex - 2];
            }

            maxRobbedAmountAtHouse[houseIndex] =
                Math.max(maxIfRobCurrentHouse, maxIfSkipCurrentHouse);
        }

        return maxRobbedAmountAtHouse[numberOfHouses - 1];
    }

    public static int maxRobbedAmountOptimized(int[] houseValues) {
        if (houseValues.length == 0) {
            return 0;
        }

        int numberOfHouses = houseValues.length;

        int maxRobbedUpToPreviousHouse = houseValues[0];
        int maxRobbedUpToHouseBeforePrevious = 0;

        for (int houseIndex = 1; houseIndex < numberOfHouses; houseIndex++) {
            int maxIfSkipCurrentHouse = maxRobbedUpToPreviousHouse;

            int maxIfRobCurrentHouse = houseValues[houseIndex];
            if (houseIndex > 1) {
                maxIfRobCurrentHouse += maxRobbedUpToHouseBeforePrevious;
            }

            int maxRobbedUpToCurrentHouse =
                Math.max(maxIfRobCurrentHouse, maxIfSkipCurrentHouse);

            maxRobbedUpToHouseBeforePrevious = maxRobbedUpToPreviousHouse;
            maxRobbedUpToPreviousHouse = maxRobbedUpToCurrentHouse;
        }

        return maxRobbedUpToPreviousHouse;
    }
}
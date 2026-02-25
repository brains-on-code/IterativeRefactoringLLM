package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Class to solve the Bandwidth Allocation Problem.
 * The goal is to maximize the value gained by allocating bandwidth to users.
 * Example:
 * Bandwidth = 10
 * Users = [3, 5, 7]
 * Values = [10, 20, 30]
 * The maximum value achievable is 40 by allocating 3 units to user 0 and 7 units to user 2.
 *
 * @author Hardvan
 */
public final class BandwidthAllocation {

    private BandwidthAllocation() {
    }

    /**
     * Allocates bandwidth to maximize value.
     * Steps:
     * 1. Calculate the ratio of value/demand for each user.
     * 2. Sort the users in descending order of the ratio.
     * 3. Allocate bandwidth to users in order of the sorted list.
     * 4. If the bandwidth is not enough to allocate the full demand of a user, allocate a fraction of the demand.
     * 5. Return the maximum value achievable.
     *
     * @param totalBandwidth total available bandwidth to allocate
     * @param userDemands    array of user bandwidth demands
     * @param userValues     array of values associated with each user's demand
     * @return the maximum value achievable
     */
    public static int maxValue(int totalBandwidth, int[] userDemands, int[] userValues) {
        int userCount = userDemands.length;
        double[][] userIndexAndValuePerUnit = new double[userCount][2]; // {userIndex, valuePerUnitBandwidth}

        for (int userIndex = 0; userIndex < userCount; userIndex++) {
            userIndexAndValuePerUnit[userIndex][0] = userIndex;
            userIndexAndValuePerUnit[userIndex][1] =
                (double) userValues[userIndex] / userDemands[userIndex];
        }

        Arrays.sort(
            userIndexAndValuePerUnit,
            (firstUser, secondUser) -> Double.compare(secondUser[1], firstUser[1])
        );

        int totalValue = 0;
        for (int sortedUserIndex = 0; sortedUserIndex < userCount; sortedUserIndex++) {
            int originalUserIndex = (int) userIndexAndValuePerUnit[sortedUserIndex][0];
            int userDemand = userDemands[originalUserIndex];
            int userValue = userValues[originalUserIndex];
            double valuePerUnitBandwidth = userIndexAndValuePerUnit[sortedUserIndex][1];

            if (totalBandwidth >= userDemand) {
                totalValue += userValue;
                totalBandwidth -= userDemand;
            } else {
                totalValue += (int) (valuePerUnitBandwidth * totalBandwidth);
                break;
            }
        }
        return totalValue;
    }
}
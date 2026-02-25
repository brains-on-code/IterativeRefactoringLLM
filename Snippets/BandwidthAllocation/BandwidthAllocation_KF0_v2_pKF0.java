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
 * <p>This is analogous to the fractional knapsack problem.
 */
public final class BandwidthAllocation {

    private BandwidthAllocation() {
        // Utility class; prevent instantiation
    }

    private static final class UserRatio {
        final int index;
        final double valuePerUnit;

        UserRatio(int index, double valuePerUnit) {
            this.index = index;
            this.valuePerUnit = valuePerUnit;
        }
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
     * @param bandwidth total available bandwidth to allocate
     * @param users     array of user demands
     * @param values    array of values associated with each user's demand
     * @return the maximum value achievable
     * @throws IllegalArgumentException if input arrays are null, of different lengths,
     *                                  or contain non-positive demands
     */
    public static int maxValue(int bandwidth, int[] users, int[] values) {
        validateInputs(bandwidth, users, values);

        int userCount = users.length;
        UserRatio[] userRatios = buildUserRatios(users, values, userCount);

        Arrays.sort(userRatios, (a, b) -> Double.compare(b.valuePerUnit, a.valuePerUnit));

        return allocateBandwidth(bandwidth, users, values, userRatios);
    }

    private static void validateInputs(int bandwidth, int[] users, int[] values) {
        if (users == null || values == null) {
            throw new IllegalArgumentException("Users and values arrays must not be null.");
        }
        if (users.length != values.length) {
            throw new IllegalArgumentException("Users and values arrays must have the same length.");
        }
        if (bandwidth < 0) {
            throw new IllegalArgumentException("Bandwidth must not be negative.");
        }
    }

    private static UserRatio[] buildUserRatios(int[] users, int[] values, int userCount) {
        UserRatio[] userRatios = new UserRatio[userCount];

        for (int i = 0; i < userCount; i++) {
            if (users[i] <= 0) {
                throw new IllegalArgumentException("User demand must be positive. Invalid at index: " + i);
            }
            double ratio = (double) values[i] / users[i];
            userRatios[i] = new UserRatio(i, ratio);
        }

        return userRatios;
    }

    private static int allocateBandwidth(
        int bandwidth,
        int[] users,
        int[] values,
        UserRatio[] userRatios
    ) {
        if (bandwidth == 0 || users.length == 0) {
            return 0;
        }

        int totalValue = 0;

        for (UserRatio userRatio : userRatios) {
            int index = userRatio.index;
            int demand = users[index];
            int value = values[index];

            if (bandwidth >= demand) {
                totalValue += value;
                bandwidth -= demand;
            } else {
                totalValue += (int) (userRatio.valuePerUnit * bandwidth);
                break;
            }

            if (bandwidth == 0) {
                break;
            }
        }

        return totalValue;
    }
}
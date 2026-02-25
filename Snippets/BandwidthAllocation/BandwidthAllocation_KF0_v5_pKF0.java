package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Class to solve the Bandwidth Allocation Problem.
 * The goal is to maximize the value gained by allocating bandwidth to users.
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

        if (bandwidth == 0 || users.length == 0) {
            return 0;
        }

        UserRatio[] userRatios = buildUserRatios(users, values);
        sortUserRatiosByValuePerUnit(userRatios);

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

    private static UserRatio[] buildUserRatios(int[] users, int[] values) {
        int userCount = users.length;
        UserRatio[] userRatios = new UserRatio[userCount];

        for (int i = 0; i < userCount; i++) {
            int demand = users[i];
            if (demand <= 0) {
                throw new IllegalArgumentException(
                    "User demand must be positive. Invalid at index: " + i
                );
            }
            double ratio = (double) values[i] / demand;
            userRatios[i] = new UserRatio(i, ratio);
        }

        return userRatios;
    }

    private static void sortUserRatiosByValuePerUnit(UserRatio[] userRatios) {
        Arrays.sort(userRatios, (first, second) -> Double.compare(second.valuePerUnit, first.valuePerUnit));
    }

    private static int allocateBandwidth(
        int bandwidth,
        int[] users,
        int[] values,
        UserRatio[] userRatios
    ) {
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
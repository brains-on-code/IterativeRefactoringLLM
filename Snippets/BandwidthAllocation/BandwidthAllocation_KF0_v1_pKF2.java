package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Solves the Bandwidth Allocation Problem using a greedy strategy.
 *
 * <p>Given:
 * <ul>
 *   <li>a total bandwidth capacity,</li>
 *   <li>an array of user bandwidth demands, and</li>
 *   <li>an array of values associated with fully satisfying each user's demand,</li>
 * </ul>
 * this algorithm maximizes the total value by possibly allocating fractional
 * bandwidth to users based on their value-per-unit-bandwidth ratio.
 *
 * <p>Example:
 * <pre>
 * bandwidth = 10
 * users     = [3, 5, 7]
 * values    = [10, 20, 30]
 *
 * Optimal allocation:
 * - 3 units to user 0 (value 10)
 * - 7 units to user 2 (value 30)
 * Total value = 40
 * </pre>
 */
public final class BandwidthAllocation {

    private BandwidthAllocation() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the maximum achievable value by allocating the given bandwidth.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>Compute value-per-unit-bandwidth (ratio) for each user.</li>
     *   <li>Sort users in descending order of this ratio.</li>
     *   <li>Iterate through users in that order:
     *     <ul>
     *       <li>If remaining bandwidth can fully satisfy a user, allocate it.</li>
     *       <li>Otherwise, allocate the remaining bandwidth fractionally and stop.</li>
     *     </ul>
     *   </li>
     * </ol>
     *
     * @param bandwidth total available bandwidth
     * @param users     array of user bandwidth demands
     * @param values    array of values for fully satisfying each user's demand
     * @return maximum total value achievable (integer truncated if fractional)
     * @throws IllegalArgumentException if input arrays are null, have different
     *                                  lengths, or contain non-positive demands
     */
    public static int maxValue(int bandwidth, int[] users, int[] values) {
        validateInputs(bandwidth, users, values);

        int n = users.length;
        // Each entry: [userIndex, valuePerUnitBandwidth]
        double[][] userRatios = new double[n][2];

        for (int i = 0; i < n; i++) {
            userRatios[i][0] = i;
            userRatios[i][1] = (double) values[i] / users[i];
        }

        // Sort by value-per-unit-bandwidth in descending order
        Arrays.sort(userRatios, (a, b) -> Double.compare(b[1], a[1]));

        int totalValue = 0;

        for (int i = 0; i < n && bandwidth > 0; i++) {
            int userIndex = (int) userRatios[i][0];
            int demand = users[userIndex];
            int value = values[userIndex];
            double ratio = userRatios[i][1];

            if (bandwidth >= demand) {
                // Allocate full demand
                totalValue += value;
                bandwidth -= demand;
            } else {
                // Allocate remaining bandwidth fractionally
                totalValue += (int) (ratio * bandwidth);
                break;
            }
        }

        return totalValue;
    }

    private static void validateInputs(int bandwidth, int[] users, int[] values) {
        if (users == null || values == null) {
            throw new IllegalArgumentException("User and value arrays must not be null.");
        }
        if (users.length != values.length) {
            throw new IllegalArgumentException("User and value arrays must have the same length.");
        }
        if (bandwidth < 0) {
            throw new IllegalArgumentException("Bandwidth must be non-negative.");
        }
        for (int demand : users) {
            if (demand <= 0) {
                throw new IllegalArgumentException("All user demands must be positive.");
            }
        }
    }
}
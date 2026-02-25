package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Counts the number of ways to assign items (columns) to groups (rows) such that
 * each group is used at most once and each item is assigned to at most one group.
 *
 * The input is a list of groups, where each group is a list of item indices it can take.
 * Items are identified by integers in the range [1, maxItem].
 */
public final class Class1 {

    /** Maximum item index (inclusive). */
    private final int maxItem;

    /**
     * Memoization table for dynamic programming.
     * dp[usedGroupsMask][item] = number of ways to reach state "usedGroupsMask"
     * starting from "item".
     * usedGroupsMask is a bitmask of used groups.
     */
    private final int[][] dp;

    /**
     * For each item, the list of group indices that can take this item.
     * item indices are in [1, maxItem].
     */
    private final List<List<Integer>> itemToGroups;

    /** Bitmask where all groups are used (all bits set). */
    private final int allGroupsMask;

    /**
     * Constructs the DP solver.
     *
     * @param groups  list of groups; each group is a list of item indices it can take
     * @param maxItem maximum item index (inclusive)
     */
    public Class1(List<List<Integer>> groups, int maxItem) {
        this.maxItem = maxItem;

        int groupCount = groups.size();
        int maskLimit = 1 << groupCount;

        // dp[usedGroupsMask][item], usedGroupsMask ranges over all subsets of groups, item in [0..maxItem]
        this.dp = new int[maskLimit][maxItem + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        // Build reverse mapping: for each item, which groups can take it
        this.itemToGroups = new ArrayList<>(maxItem + 1);
        for (int i = 0; i <= maxItem; i++) {
            this.itemToGroups.add(new ArrayList<>());
        }

        this.allGroupsMask = maskLimit - 1;

        for (int groupIndex = 0; groupIndex < groupCount; groupIndex++) {
            for (int item : groups.get(groupIndex)) {
                this.itemToGroups.get(item).add(groupIndex);
            }
        }
    }

    /**
     * Recursive DP.
     *
     * @param usedGroupsMask bitmask of groups already used
     * @param item           current item index being considered
     * @return number of valid assignments from this state
     */
    private int countWays(int usedGroupsMask, int item) {
        // All groups have been assigned
        if (usedGroupsMask == allGroupsMask) {
            return 1;
        }

        // No more items to assign
        if (item > maxItem) {
            return 0;
        }

        if (dp[usedGroupsMask][item] != -1) {
            return dp[usedGroupsMask][item];
        }

        // Option 1: skip this item
        int ways = countWays(usedGroupsMask, item + 1);

        // Option 2: assign this item to each compatible, unused group
        for (int groupIndex : itemToGroups.get(item)) {
            if ((usedGroupsMask & (1 << groupIndex)) != 0) {
                continue; // group already used
            }
            int nextMask = usedGroupsMask | (1 << groupIndex);
            ways += countWays(nextMask, item + 1);
        }

        dp[usedGroupsMask][item] = ways;
        return ways;
    }

    /**
     * Computes the total number of valid assignments.
     *
     * @return number of ways to assign items to groups
     */
    public int method2() {
        return countWays(0, 1);
    }
}
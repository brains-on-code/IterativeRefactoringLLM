package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Class1 {

    private final int maxValue;
    private final int[][] memo;
    private final List<List<Integer>> valueToGroupIndices;
    private final int allGroupsMask;

    public Class1(List<List<Integer>> groups, int maxValue) {
        this.maxValue = maxValue;
        int groupCount = groups.size();

        this.memo = createMemoTable(groupCount, maxValue);
        this.valueToGroupIndices = mapValuesToGroups(groups, maxValue);
        this.allGroupsMask = (1 << groupCount) - 1;
    }

    private int[][] createMemoTable(int groupCount, int maxValue) {
        int[][] memoTable = new int[1 << groupCount][maxValue + 1];
        for (int[] row : memoTable) {
            Arrays.fill(row, -1);
        }
        return memoTable;
    }

    private List<List<Integer>> mapValuesToGroups(List<List<Integer>> groups, int maxValue) {
        List<List<Integer>> mapping = new ArrayList<>(maxValue + 1);
        for (int value = 0; value <= maxValue; value++) {
            mapping.add(new ArrayList<>());
        }

        for (int groupIndex = 0; groupIndex < groups.size(); groupIndex++) {
            for (int value : groups.get(groupIndex)) {
                if (value >= 0 && value <= maxValue) {
                    mapping.get(value).add(groupIndex);
                }
            }
        }

        return mapping;
    }

    private int countWays(int usedGroupsMask, int currentValue) {
        if (usedGroupsMask == allGroupsMask) {
            return 1;
        }

        if (currentValue > maxValue) {
            return 0;
        }

        if (memo[usedGroupsMask][currentValue] != -1) {
            return memo[usedGroupsMask][currentValue];
        }

        int ways = countWays(usedGroupsMask, currentValue + 1);

        for (int groupIndex : valueToGroupIndices.get(currentValue)) {
            if (isGroupUsed(usedGroupsMask, groupIndex)) {
                continue;
            }
            int nextMask = markGroupUsed(usedGroupsMask, groupIndex);
            ways += countWays(nextMask, currentValue + 1);
        }

        memo[usedGroupsMask][currentValue] = ways;
        return ways;
    }

    private boolean isGroupUsed(int usedGroupsMask, int groupIndex) {
        return (usedGroupsMask & (1 << groupIndex)) != 0;
    }

    private int markGroupUsed(int usedGroupsMask, int groupIndex) {
        return usedGroupsMask | (1 << groupIndex);
    }

    public int method2() {
        return countWays(0, 1);
    }
}
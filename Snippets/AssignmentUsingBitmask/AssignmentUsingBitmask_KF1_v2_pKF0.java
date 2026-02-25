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

        this.memo = new int[1 << groupCount][maxValue + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        this.valueToGroupIndices = new ArrayList<>(maxValue + 1);
        for (int value = 0; value <= maxValue; value++) {
            this.valueToGroupIndices.add(new ArrayList<>());
        }

        this.allGroupsMask = (1 << groupCount) - 1;

        for (int groupIndex = 0; groupIndex < groupCount; groupIndex++) {
            for (int value : groups.get(groupIndex)) {
                this.valueToGroupIndices.get(value).add(groupIndex);
            }
        }
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
            if ((usedGroupsMask & (1 << groupIndex)) != 0) {
                continue;
            }
            int nextMask = usedGroupsMask | (1 << groupIndex);
            ways += countWays(nextMask, currentValue + 1);
        }

        memo[usedGroupsMask][currentValue] = ways;
        return ways;
    }

    public int method2() {
        return countWays(0, 1);
    }
}
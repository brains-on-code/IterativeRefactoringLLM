package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AssignmentUsingBitmask {

    private final int totalTasks;
    private final int[][] dp;
    private final List<List<Integer>> tasksPerIndex;
    private final int finalMask;

    public AssignmentUsingBitmask(List<List<Integer>> taskPerformed, int totalTasks) {
        this.totalTasks = totalTasks;

        int totalPersons = taskPerformed.size();
        this.dp = new int[1 << totalPersons][totalTasks + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        this.tasksPerIndex = new ArrayList<>(totalTasks + 1);
        for (int i = 0; i <= totalTasks; i++) {
            this.tasksPerIndex.add(new ArrayList<>());
        }

        this.finalMask = (1 << totalPersons) - 1;

        for (int person = 0; person < totalPersons; person++) {
            for (int task : taskPerformed.get(person)) {
                this.tasksPerIndex.get(task).add(person);
            }
        }
    }

    private int countWays(int mask, int taskNo) {
        if (mask == finalMask) {
            return 1;
        }
        if (taskNo > totalTasks) {
            return 0;
        }
        if (dp[mask][taskNo] != -1) {
            return dp[mask][taskNo];
        }

        int totalWays = countWays(mask, taskNo + 1);

        for (int person : tasksPerIndex.get(taskNo)) {
            if ((mask & (1 << person)) != 0) {
                continue;
            }
            totalWays += countWays(mask | (1 << person), taskNo + 1);
        }

        dp[mask][taskNo] = totalWays;
        return totalWays;
    }

    public int countNoOfWays() {
        return countWays(0, 1);
    }
}
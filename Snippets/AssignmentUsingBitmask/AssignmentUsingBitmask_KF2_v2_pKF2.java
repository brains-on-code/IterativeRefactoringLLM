package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AssignmentUsingBitmask {

    private final int totalTasks;
    private final int[][] dp;
    private final List<List<Integer>> personsPerTask;
    private final int allPersonsMask;

    public AssignmentUsingBitmask(List<List<Integer>> taskPerformed, int totalTasks) {
        this.totalTasks = totalTasks;

        int totalPersons = taskPerformed.size();
        this.dp = new int[1 << totalPersons][totalTasks + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        this.personsPerTask = new ArrayList<>(totalTasks + 1);
        for (int i = 0; i <= totalTasks; i++) {
            this.personsPerTask.add(new ArrayList<>());
        }

        this.allPersonsMask = (1 << totalPersons) - 1;

        for (int person = 0; person < totalPersons; person++) {
            for (int task : taskPerformed.get(person)) {
                this.personsPerTask.get(task).add(person);
            }
        }
    }

    private int countWays(int assignedPersonsMask, int taskIndex) {
        if (assignedPersonsMask == allPersonsMask) {
            return 1;
        }
        if (taskIndex > totalTasks) {
            return 0;
        }
        if (dp[assignedPersonsMask][taskIndex] != -1) {
            return dp[assignedPersonsMask][taskIndex];
        }

        int totalWays = countWays(assignedPersonsMask, taskIndex + 1);

        for (int person : personsPerTask.get(taskIndex)) {
            if ((assignedPersonsMask & (1 << person)) != 0) {
                continue;
            }
            int newMask = assignedPersonsMask | (1 << person);
            totalWays += countWays(newMask, taskIndex + 1);
        }

        dp[assignedPersonsMask][taskIndex] = totalWays;
        return totalWays;
    }

    public int countNoOfWays() {
        return countWays(0, 1);
    }
}
package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AssignmentUsingBitmask {

    private final int totalTasks;
    private final int[][] memo;
    private final List<List<Integer>> personsPerTask;
    private final int allPersonsMask;

    public AssignmentUsingBitmask(List<List<Integer>> taskPerformed, int totalTasks) {
        this.totalTasks = totalTasks;

        int totalPersons = taskPerformed.size();
        this.memo = new int[1 << totalPersons][totalTasks + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        this.personsPerTask = new ArrayList<>(totalTasks + 1);
        for (int task = 0; task <= totalTasks; task++) {
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

        if (memo[assignedPersonsMask][taskIndex] != -1) {
            return memo[assignedPersonsMask][taskIndex];
        }

        int totalWays = countWays(assignedPersonsMask, taskIndex + 1);

        for (int person : personsPerTask.get(taskIndex)) {
            boolean isPersonAlreadyAssigned = (assignedPersonsMask & (1 << person)) != 0;
            if (isPersonAlreadyAssigned) {
                continue;
            }

            int updatedMask = assignedPersonsMask | (1 << person);
            totalWays += countWays(updatedMask, taskIndex + 1);
        }

        memo[assignedPersonsMask][taskIndex] = totalWays;
        return totalWays;
    }

    public int countNoOfWays() {
        return countWays(0, 1);
    }
}
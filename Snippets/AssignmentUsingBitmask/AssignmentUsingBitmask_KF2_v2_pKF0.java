package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AssignmentUsingBitmask {

    private final int totalTasks;
    private final int[][] memo;
    private final List<List<Integer>> peopleByTask;
    private final int allPeopleMask;

    public AssignmentUsingBitmask(List<List<Integer>> taskPerformed, int totalTasks) {
        this.totalTasks = totalTasks;

        int peopleCount = taskPerformed.size();
        this.memo = new int[1 << peopleCount][totalTasks + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        this.peopleByTask = new ArrayList<>(totalTasks + 1);
        for (int task = 0; task <= totalTasks; task++) {
            this.peopleByTask.add(new ArrayList<>());
        }

        this.allPeopleMask = (1 << peopleCount) - 1;

        for (int person = 0; person < peopleCount; person++) {
            for (int task : taskPerformed.get(person)) {
                this.peopleByTask.get(task).add(person);
            }
        }
    }

    private int countWays(int assignedMask, int currentTask) {
        if (assignedMask == allPeopleMask) {
            return 1;
        }
        if (currentTask > totalTasks) {
            return 0;
        }
        if (memo[assignedMask][currentTask] != -1) {
            return memo[assignedMask][currentTask];
        }

        int totalWays = countWays(assignedMask, currentTask + 1);

        for (int person : peopleByTask.get(currentTask)) {
            boolean isPersonUnassigned = (assignedMask & (1 << person)) == 0;
            if (isPersonUnassigned) {
                int newMask = assignedMask | (1 << person);
                totalWays += countWays(newMask, currentTask + 1);
            }
        }

        memo[assignedMask][currentTask] = totalWays;
        return totalWays;
    }

    public int countNoOfWays() {
        return countWays(0, 1);
    }
}
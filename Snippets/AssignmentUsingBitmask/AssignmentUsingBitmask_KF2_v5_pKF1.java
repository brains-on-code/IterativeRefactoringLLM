package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AssignmentUsingBitmask {

    private final int totalTasks;
    private final int[][] memoizedWays;
    private final List<List<Integer>> taskToEligiblePersons;
    private final int fullPersonMask;

    public AssignmentUsingBitmask(List<List<Integer>> personToTasks, int totalTasks) {
        this.totalTasks = totalTasks;
        int totalPersons = personToTasks.size();

        this.memoizedWays = new int[1 << totalPersons][totalTasks + 1];
        for (int[] memoRow : memoizedWays) {
            Arrays.fill(memoRow, -1);
        }

        this.taskToEligiblePersons = new ArrayList<>(totalTasks + 1);
        for (int taskIndex = 0; taskIndex <= totalTasks; taskIndex++) {
            this.taskToEligiblePersons.add(new ArrayList<>());
        }

        this.fullPersonMask = (1 << totalPersons) - 1;

        for (int personIndex = 0; personIndex < totalPersons; personIndex++) {
            for (int taskId : personToTasks.get(personIndex)) {
                this.taskToEligiblePersons.get(taskId).add(personIndex);
            }
        }
    }

    private int countWaysToAssignTasks(int assignedPersonMask, int currentTaskId) {
        if (assignedPersonMask == fullPersonMask) {
            return 1;
        }
        if (currentTaskId > totalTasks) {
            return 0;
        }
        if (memoizedWays[assignedPersonMask][currentTaskId] != -1) {
            return memoizedWays[assignedPersonMask][currentTaskId];
        }

        int totalWays = countWaysToAssignTasks(assignedPersonMask, currentTaskId + 1);

        for (int personIndex : taskToEligiblePersons.get(currentTaskId)) {
            if ((assignedPersonMask & (1 << personIndex)) != 0) {
                continue;
            }
            totalWays += countWaysToAssignTasks(
                assignedPersonMask | (1 << personIndex),
                currentTaskId + 1
            );
        }

        memoizedWays[assignedPersonMask][currentTaskId] = totalWays;
        return totalWays;
    }

    public int countNumberOfWays() {
        return countWaysToAssignTasks(0, 1);
    }
}
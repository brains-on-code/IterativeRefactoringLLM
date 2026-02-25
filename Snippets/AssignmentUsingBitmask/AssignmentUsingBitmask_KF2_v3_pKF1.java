package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AssignmentUsingBitmask {

    private final int totalTasks;
    private final int[][] memoizedWays;
    private final List<List<Integer>> taskToEligiblePersons;
    private final int allPersonsAssignedMask;

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

        this.allPersonsAssignedMask = (1 << totalPersons) - 1;

        for (int personIndex = 0; personIndex < totalPersons; personIndex++) {
            for (int taskIndex : personToTasks.get(personIndex)) {
                this.taskToEligiblePersons.get(taskIndex).add(personIndex);
            }
        }
    }

    private int countAssignmentWays(int assignedPersonsMask, int currentTaskIndex) {
        if (assignedPersonsMask == allPersonsAssignedMask) {
            return 1;
        }
        if (currentTaskIndex > totalTasks) {
            return 0;
        }
        if (memoizedWays[assignedPersonsMask][currentTaskIndex] != -1) {
            return memoizedWays[assignedPersonsMask][currentTaskIndex];
        }

        int totalWays = countAssignmentWays(assignedPersonsMask, currentTaskIndex + 1);

        for (int personIndex : taskToEligiblePersons.get(currentTaskIndex)) {
            if ((assignedPersonsMask & (1 << personIndex)) != 0) {
                continue;
            }
            totalWays += countAssignmentWays(
                assignedPersonsMask | (1 << personIndex),
                currentTaskIndex + 1
            );
        }

        memoizedWays[assignedPersonsMask][currentTaskIndex] = totalWays;
        return totalWays;
    }

    public int countNumberOfWays() {
        return countAssignmentWays(0, 1);
    }
}
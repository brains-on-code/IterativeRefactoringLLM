package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AssignmentUsingBitmask {

    private final int totalTasks;
    private final int[][] memoizationTable;
    private final List<List<Integer>> taskToPeopleMap;
    private final int allPeopleAssignedMask;

    public AssignmentUsingBitmask(List<List<Integer>> peopleTasks, int totalTasks) {
        this.totalTasks = totalTasks;
        int totalPeople = peopleTasks.size();

        this.memoizationTable = new int[1 << totalPeople][totalTasks + 1];
        for (int[] row : memoizationTable) {
            Arrays.fill(row, -1);
        }

        this.taskToPeopleMap = new ArrayList<>(totalTasks + 1);
        for (int taskId = 0; taskId <= totalTasks; taskId++) {
            this.taskToPeopleMap.add(new ArrayList<>());
        }

        this.allPeopleAssignedMask = (1 << totalPeople) - 1;

        for (int personId = 0; personId < totalPeople; personId++) {
            for (int taskId : peopleTasks.get(personId)) {
                this.taskToPeopleMap.get(taskId).add(personId);
            }
        }
    }

    private int countAssignmentWays(int assignedPeopleMask, int currentTaskId) {
        if (assignedPeopleMask == allPeopleAssignedMask) {
            return 1;
        }
        if (currentTaskId > totalTasks) {
            return 0;
        }
        if (memoizationTable[assignedPeopleMask][currentTaskId] != -1) {
            return memoizationTable[assignedPeopleMask][currentTaskId];
        }

        int totalWays = countAssignmentWays(assignedPeopleMask, currentTaskId + 1);

        for (int personId : taskToPeopleMap.get(currentTaskId)) {
            if ((assignedPeopleMask & (1 << personId)) != 0) {
                continue;
            }
            totalWays += countAssignmentWays(assignedPeopleMask | (1 << personId), currentTaskId + 1);
        }

        memoizationTable[assignedPeopleMask][currentTaskId] = totalWays;
        return totalWays;
    }

    public int countNumberOfWays() {
        return countAssignmentWays(0, 1);
    }
}
package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AssignmentUsingBitmask {

    private final int numberOfTasks;
    private final int[][] dpCache;
    private final List<List<Integer>> taskToEligiblePeople;
    private final int allPeopleAssignedMask;

    public AssignmentUsingBitmask(List<List<Integer>> peopleToTasks, int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
        int numberOfPeople = peopleToTasks.size();

        this.dpCache = new int[1 << numberOfPeople][numberOfTasks + 1];
        for (int[] cacheRow : dpCache) {
            Arrays.fill(cacheRow, -1);
        }

        this.taskToEligiblePeople = new ArrayList<>(numberOfTasks + 1);
        for (int taskId = 0; taskId <= numberOfTasks; taskId++) {
            this.taskToEligiblePeople.add(new ArrayList<>());
        }

        this.allPeopleAssignedMask = (1 << numberOfPeople) - 1;

        for (int personId = 0; personId < numberOfPeople; personId++) {
            for (int taskId : peopleToTasks.get(personId)) {
                this.taskToEligiblePeople.get(taskId).add(personId);
            }
        }
    }

    private int countAssignmentWays(int assignedPeopleMask, int currentTaskId) {
        if (assignedPeopleMask == allPeopleAssignedMask) {
            return 1;
        }
        if (currentTaskId > numberOfTasks) {
            return 0;
        }
        if (dpCache[assignedPeopleMask][currentTaskId] != -1) {
            return dpCache[assignedPeopleMask][currentTaskId];
        }

        int totalWays = countAssignmentWays(assignedPeopleMask, currentTaskId + 1);

        for (int personId : taskToEligiblePeople.get(currentTaskId)) {
            if ((assignedPeopleMask & (1 << personId)) != 0) {
                continue;
            }
            totalWays += countAssignmentWays(assignedPeopleMask | (1 << personId), currentTaskId + 1);
        }

        dpCache[assignedPeopleMask][currentTaskId] = totalWays;
        return totalWays;
    }

    public int countNumberOfWays() {
        return countAssignmentWays(0, 1);
    }
}
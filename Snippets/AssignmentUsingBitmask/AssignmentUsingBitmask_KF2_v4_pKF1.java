package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AssignmentUsingBitmask {

    private final int numberOfTasks;
    private final int[][] dpCache;
    private final List<List<Integer>> taskToPersonsMap;
    private final int allPersonsMask;

    public AssignmentUsingBitmask(List<List<Integer>> personToTasksMap, int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
        int numberOfPersons = personToTasksMap.size();

        this.dpCache = new int[1 << numberOfPersons][numberOfTasks + 1];
        for (int[] cacheRow : dpCache) {
            Arrays.fill(cacheRow, -1);
        }

        this.taskToPersonsMap = new ArrayList<>(numberOfTasks + 1);
        for (int taskId = 0; taskId <= numberOfTasks; taskId++) {
            this.taskToPersonsMap.add(new ArrayList<>());
        }

        this.allPersonsMask = (1 << numberOfPersons) - 1;

        for (int personId = 0; personId < numberOfPersons; personId++) {
            for (int taskId : personToTasksMap.get(personId)) {
                this.taskToPersonsMap.get(taskId).add(personId);
            }
        }
    }

    private int countWaysToAssignTasks(int assignedPersonsMask, int taskId) {
        if (assignedPersonsMask == allPersonsMask) {
            return 1;
        }
        if (taskId > numberOfTasks) {
            return 0;
        }
        if (dpCache[assignedPersonsMask][taskId] != -1) {
            return dpCache[assignedPersonsMask][taskId];
        }

        int totalWays = countWaysToAssignTasks(assignedPersonsMask, taskId + 1);

        for (int personId : taskToPersonsMap.get(taskId)) {
            if ((assignedPersonsMask & (1 << personId)) != 0) {
                continue;
            }
            totalWays += countWaysToAssignTasks(
                assignedPersonsMask | (1 << personId),
                taskId + 1
            );
        }

        dpCache[assignedPersonsMask][taskId] = totalWays;
        return totalWays;
    }

    public int countNumberOfWays() {
        return countWaysToAssignTasks(0, 1);
    }
}
package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The AssignmentUsingBitmask class is used to calculate the total number of ways
 * tasks can be distributed among people, given specific constraints on who can perform which tasks.
 * The approach uses bitmasking and dynamic programming to efficiently solve the problem.
 *
 * @author Hardvan
 */
public final class AssignmentUsingBitmask {

    private final int totalTasks;
    private final int peopleCount;
    private final int finalMask;
    private final int[][] dp;
    private final List<List<Integer>> peoplePerTaskId;

    /**
     * Constructor for the AssignmentUsingBitmask class.
     *
     * @param taskPerformed a list of lists, where each inner list contains the tasks that a person can perform.
     * @param totalTasks    the total number of tasks.
     */
    public AssignmentUsingBitmask(List<List<Integer>> taskPerformed, int totalTasks) {
        this.totalTasks = totalTasks;
        this.peopleCount = taskPerformed.size();
        this.finalMask = (1 << peopleCount) - 1;

        this.dp = initDpTable();
        this.peoplePerTaskId = buildPeoplePerTaskMapping(taskPerformed);
    }

    private int[][] initDpTable() {
        int[][] table = new int[1 << peopleCount][totalTasks + 1];
        for (int[] row : table) {
            Arrays.fill(row, -1);
        }
        return table;
    }

    private List<List<Integer>> buildPeoplePerTaskMapping(List<List<Integer>> taskPerformed) {
        List<List<Integer>> mapping = new ArrayList<>(totalTasks + 1);
        for (int taskId = 0; taskId <= totalTasks; taskId++) {
            mapping.add(new ArrayList<>());
        }

        for (int personId = 0; personId < peopleCount; personId++) {
            for (int taskId : taskPerformed.get(personId)) {
                if (isValidTaskId(taskId)) {
                    mapping.get(taskId).add(personId);
                }
            }
        }
        return mapping;
    }

    private boolean isValidTaskId(int taskId) {
        return taskId >= 0 && taskId <= totalTasks;
    }

    /**
     * Counts the ways to assign tasks up to the given taskId with the specified mask.
     *
     * @param mask   the bitmask representing the current state of assignments.
     * @param taskId the current task number being processed.
     * @return the number of ways to assign tasks.
     */
    private int countWays(int mask, int taskId) {
        if (mask == finalMask) {
            return 1;
        }
        if (taskId > totalTasks) {
            return 0;
        }

        int cachedResult = dp[mask][taskId];
        if (cachedResult != -1) {
            return cachedResult;
        }

        int totalWays = skipCurrentTask(mask, taskId);
        totalWays += assignCurrentTaskToEligiblePeople(mask, taskId);

        dp[mask][taskId] = totalWays;
        return totalWays;
    }

    private int skipCurrentTask(int mask, int taskId) {
        return countWays(mask, taskId + 1);
    }

    private int assignCurrentTaskToEligiblePeople(int mask, int taskId) {
        int totalWays = 0;
        for (int personId : peoplePerTaskId.get(taskId)) {
            if (isPersonUnassigned(mask, personId)) {
                int updatedMask = assignPerson(mask, personId);
                totalWays += countWays(updatedMask, taskId + 1);
            }
        }
        return totalWays;
    }

    private boolean isPersonUnassigned(int mask, int personId) {
        return (mask & (1 << personId)) == 0;
    }

    private int assignPerson(int mask, int personId) {
        return mask | (1 << personId);
    }

    /**
     * Counts the total number of ways to distribute tasks among persons.
     *
     * @return the total number of ways to distribute tasks.
     */
    public int countNoOfWays() {
        return countWays(0, 1);
    }
}
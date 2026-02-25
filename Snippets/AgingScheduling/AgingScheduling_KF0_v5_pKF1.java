package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * AgingScheduling is an algorithm designed to prevent starvation
 * by gradually increasing the priority of waiting tasks.
 * The longer a process waits, the higher its priority becomes.
 *
 * Use Case: Useful in systems with mixed workloads to avoid
 * lower-priority tasks being starved by higher-priority tasks.
 *
 * author Hardvan
 */
public final class AgingScheduling {

    static class Task {
        String taskName;
        int accumulatedWaitingTime;
        int currentPriority;

        Task(String taskName, int initialPriority) {
            this.taskName = taskName;
            this.currentPriority = initialPriority;
            this.accumulatedWaitingTime = 0;
        }
    }

    private final Queue<Task> pendingTasks;

    public AgingScheduling() {
        this.pendingTasks = new LinkedList<>();
    }

    /**
     * Adds a task to the scheduler with a given priority.
     *
     * @param taskName name of the task
     * @param initialPriority initial priority of the task
     */
    public void addTask(String taskName, int initialPriority) {
        pendingTasks.offer(new Task(taskName, initialPriority));
    }

    /**
     * Schedules the next task based on the priority and waiting time.
     * The priority of a task increases with the time it spends waiting.
     *
     * @return name of the next task to be executed
     */
    public String scheduleNext() {
        if (pendingTasks.isEmpty()) {
            return null;
        }

        Task nextTask = pendingTasks.poll();
        nextTask.accumulatedWaitingTime++;
        nextTask.currentPriority += nextTask.accumulatedWaitingTime;
        pendingTasks.offer(nextTask);

        return nextTask.taskName;
    }
}
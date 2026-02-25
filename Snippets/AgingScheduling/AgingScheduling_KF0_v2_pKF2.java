package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Aging-based scheduling algorithm to reduce starvation.
 *
 * <p>Each time a task waits, its priority is increased based on its wait time.
 * This helps ensure that long-waiting, lower-priority tasks eventually get CPU time.
 */
public final class AgingScheduling {

    /**
     * Represents a schedulable task.
     */
    static class Task {
        final String name;
        int waitTime;
        int priority;

        Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
            this.waitTime = 0;
        }
    }

    private final Queue<Task> taskQueue = new LinkedList<>();

    /**
     * Adds a new task to the scheduler.
     *
     * @param name     the task name
     * @param priority the initial priority of the task
     */
    public void addTask(String name, int priority) {
        taskQueue.offer(new Task(name, priority));
    }

    /**
     * Returns the name of the next task to be executed and updates its aging state.
     *
     * <p>The selected task's wait time is incremented, and its priority is increased
     * by its updated wait time. The task is then reinserted into the queue.
     *
     * @return the name of the next task to execute, or {@code null} if no tasks exist
     */
    public String scheduleNext() {
        Task nextTask = taskQueue.poll();
        if (nextTask == null) {
            return null;
        }

        nextTask.waitTime++;
        nextTask.priority += nextTask.waitTime;
        taskQueue.offer(nextTask);

        return nextTask.name;
    }
}
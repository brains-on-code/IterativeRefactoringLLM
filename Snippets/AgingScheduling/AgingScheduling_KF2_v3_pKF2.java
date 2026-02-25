package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Aging-based scheduling algorithm.
 *
 * <p>Each time a task is scheduled, its wait time increases and its priority
 * is boosted by that wait time. Tasks that wait longer gradually become
 * higher priority.</p>
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

    private final Queue<Task> taskQueue;

    /**
     * Creates an aging scheduler with an empty task queue.
     */
    public AgingScheduling() {
        this.taskQueue = new LinkedList<>();
    }

    /**
     * Adds a new task to the queue.
     *
     * @param name     task name
     * @param priority initial priority
     */
    public void addTask(String name, int priority) {
        taskQueue.offer(new Task(name, priority));
    }

    /**
     * Schedules the next task and applies aging.
     *
     * <p>If the queue is not empty:</p>
     * <ol>
     *   <li>Remove the task at the head of the queue</li>
     *   <li>Increment its wait time</li>
     *   <li>Increase its priority by the updated wait time</li>
     *   <li>Reinsert the task at the end of the queue</li>
     *   <li>Return the task's name</li>
     * </ol>
     *
     * @return the scheduled task's name, or {@code null} if the queue is empty
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
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

    private final Queue<Task> taskQueue = new LinkedList<>();

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
     * <p>When the queue is not empty:</p>
     * <ol>
     *   <li>Remove the task at the head of the queue</li>
     *   <li>Apply aging (increment wait time and boost priority)</li>
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

        applyAging(nextTask);
        taskQueue.offer(nextTask);

        return nextTask.name;
    }

    /**
     * Increases a task's wait time and boosts its priority accordingly.
     *
     * @param task the task to age
     */
    private void applyAging(Task task) {
        task.waitTime++;
        task.priority += task.waitTime;
    }
}
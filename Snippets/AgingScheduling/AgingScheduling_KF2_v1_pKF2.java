package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A simple aging-based scheduling algorithm.
 *
 * <p>Each time a task is scheduled, its wait time is incremented and its
 * priority is increased by its current wait time. This models "aging":
 * tasks that have waited longer become higher priority over time.</p>
 */
public final class AgingScheduling {

    /**
     * Represents a schedulable task with a name, wait time, and priority.
     */
    static class Task {
        String name;
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
     * Creates a new aging scheduler with an empty task queue.
     */
    public AgingScheduling() {
        this.taskQueue = new LinkedList<>();
    }

    /**
     * Adds a new task to the scheduling queue.
     *
     * @param name     the task name
     * @param priority the initial priority of the task
     */
    public void addTask(String name, int priority) {
        taskQueue.offer(new Task(name, priority));
    }

    /**
     * Schedules the next task using aging.
     *
     * <p>If the queue is empty, this returns {@code null}. Otherwise, it:
     * <ol>
     *   <li>Removes the task at the head of the queue</li>
     *   <li>Increments its wait time</li>
     *   <li>Increases its priority by its updated wait time</li>
     *   <li>Reinserts the task at the end of the queue</li>
     *   <li>Returns the task's name</li>
     * </ol>
     *
     * @return the name of the scheduled task, or {@code null} if no tasks exist
     */
    public String scheduleNext() {
        if (taskQueue.isEmpty()) {
            return null;
        }

        Task nextTask = taskQueue.poll();
        nextTask.waitTime++;
        nextTask.priority += nextTask.waitTime;
        taskQueue.offer(nextTask);

        return nextTask.name;
    }
}
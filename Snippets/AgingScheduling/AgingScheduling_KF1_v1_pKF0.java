package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A simple scheduler that cycles through tasks, increasing their priority
 * each time they are retrieved.
 */
public final class Class1 {

    /**
     * Represents a scheduled task with a name, base priority, and access count.
     */
    static class ScheduledTask {
        String name;
        int priority;
        int accessCount;

        ScheduledTask(String name, int priority) {
            this.name = name;
            this.priority = priority;
            this.accessCount = 0;
        }
    }

    private final Queue<ScheduledTask> taskQueue;

    public Class1() {
        this.taskQueue = new LinkedList<>();
    }

    /**
     * Adds a new task to the scheduler.
     *
     * @param name     the name of the task
     * @param priority the initial priority of the task
     */
    public void addTask(String name, int priority) {
        taskQueue.offer(new ScheduledTask(name, priority));
    }

    /**
     * Retrieves the next task from the scheduler, updates its access count and
     * priority, and re-queues it.
     *
     * @return the name of the next task, or {@code null} if no tasks are queued
     */
    public String getNextTask() {
        if (taskQueue.isEmpty()) {
            return null;
        }

        ScheduledTask task = taskQueue.poll();
        task.accessCount++;
        task.priority += task.accessCount;
        taskQueue.offer(task);

        return task.name;
    }
}
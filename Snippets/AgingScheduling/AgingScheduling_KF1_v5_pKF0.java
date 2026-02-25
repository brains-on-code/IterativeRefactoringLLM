package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A simple scheduler that cycles through tasks, increasing their priority
 * each time they are retrieved.
 */
public final class TaskScheduler {

    /**
     * Represents a scheduled task with a name, base priority, and access count.
     */
    private static final class ScheduledTask {
        private final String name;
        private int priority;
        private int accessCount;

        ScheduledTask(String name, int initialPriority) {
            this.name = name;
            this.priority = initialPriority;
            this.accessCount = 0;
        }

        void incrementPriority() {
            accessCount++;
            priority += accessCount;
        }

        String getName() {
            return name;
        }
    }

    private final Queue<ScheduledTask> taskQueue = new LinkedList<>();

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
        ScheduledTask nextTask = taskQueue.poll();
        if (nextTask == null) {
            return null;
        }

        nextTask.incrementPriority();
        taskQueue.offer(nextTask);
        return nextTask.getName();
    }
}
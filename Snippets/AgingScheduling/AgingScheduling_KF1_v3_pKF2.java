package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A simple round-robin scheduler that cycles through tasks.
 * Each time a task is processed, its internal value is increased
 * based on how many times it has been processed so far.
 */
public final class RoundRobinScheduler {

    /**
     * A task with:
     * - a name (identifier),
     * - a mutable value,
     * - a counter of how many times it has been processed.
     */
    static class Task {
        private final String name;
        private int value;
        private int processedCount;

        Task(String name, int value) {
            this.name = name;
            this.value = value;
            this.processedCount = 0;
        }

        /**
         * Marks this task as processed once more and updates its value.
         * The value is increased by the current processed count.
         */
        void process() {
            processedCount++;
            value += processedCount;
        }

        String getName() {
            return name;
        }
    }

    private final Queue<Task> taskQueue = new LinkedList<>();

    /**
     * Adds a new task to the queue.
     *
     * @param name  task identifier
     * @param value initial value of the task
     */
    public void addTask(String name, int value) {
        taskQueue.offer(new Task(name, value));
    }

    /**
     * Processes the next task in round-robin order.
     * The task is removed from the head of the queue, processed,
     * and then added back to the tail.
     *
     * @return the name of the processed task, or {@code null} if no tasks are queued
     */
    public String processNextTask() {
        Task task = taskQueue.poll();
        if (task == null) {
            return null;
        }
        task.process();
        taskQueue.offer(task);
        return task.getName();
    }
}
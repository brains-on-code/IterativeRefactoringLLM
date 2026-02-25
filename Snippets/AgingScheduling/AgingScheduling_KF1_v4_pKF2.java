package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Round-robin scheduler that cycles through tasks.
 * Each time a task is processed, its value increases
 * by the number of times it has been processed so far.
 */
public final class RoundRobinScheduler {

    /**
     * Represents a schedulable task.
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
         * Processes this task once and updates its value.
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
     *
     * @return the name of the processed task, or {@code null} if the queue is empty
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
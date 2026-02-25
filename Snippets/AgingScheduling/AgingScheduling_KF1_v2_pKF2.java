package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A simple round-robin style scheduler that cycles through tasks,
 * increasing their priority (or weight) each time they are processed.
 */
public final class RoundRobinScheduler {

    /**
     * Represents a scheduled task with a name, a base value, and
     * a counter tracking how many times it has been processed.
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

        void process() {
            processedCount++;
            value += processedCount;
        }

        String getName() {
            return name;
        }
    }

    private final Queue<Task> taskQueue;

    public RoundRobinScheduler() {
        this.taskQueue = new LinkedList<>();
    }

    /**
     * Adds a new task to the scheduling queue.
     *
     * @param name  the name or identifier of the task
     * @param value the initial value or weight of the task
     */
    public void addTask(String name, int value) {
        taskQueue.offer(new Task(name, value));
    }

    /**
     * Processes the next task in the queue in a round-robin fashion.
     * Each time a task is processed, its processed count is incremented
     * and its value is increased by the new processed count, then it is
     * placed back at the end of the queue.
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
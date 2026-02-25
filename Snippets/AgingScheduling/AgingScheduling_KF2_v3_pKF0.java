package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

public final class AgingScheduling {

    private static final class Task {
        private final String name;
        private int waitTime;
        private int priority;

        private Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
            this.waitTime = 0;
        }

        private void age() {
            waitTime++;
            priority += waitTime;
        }

        private String getName() {
            return name;
        }
    }

    private final Queue<Task> tasks = new LinkedList<>();

    public void addTask(String name, int priority) {
        tasks.offer(new Task(name, priority));
    }

    public String scheduleNext() {
        Task nextTask = tasks.poll();
        if (nextTask == null) {
            return null;
        }

        nextTask.age();
        tasks.offer(nextTask);

        return nextTask.getName();
    }
}
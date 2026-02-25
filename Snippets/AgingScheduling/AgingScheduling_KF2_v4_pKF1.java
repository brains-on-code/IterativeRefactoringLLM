package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

public final class AgingScheduling {

    static class Task {
        String name;
        int waitingTime;
        int priority;

        Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
            this.waitingTime = 0;
        }
    }

    private final Queue<Task> tasks;

    public AgingScheduling() {
        this.tasks = new LinkedList<>();
    }

    public void addTask(String name, int priority) {
        tasks.offer(new Task(name, priority));
    }

    public String scheduleNextTask() {
        if (tasks.isEmpty()) {
            return null;
        }

        Task nextTask = tasks.poll();
        nextTask.waitingTime++;
        nextTask.priority += nextTask.waitingTime;
        tasks.offer(nextTask);

        return nextTask.name;
    }
}
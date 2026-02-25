package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;


public final class AgingScheduling {

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

    public AgingScheduling() {
        taskQueue = new LinkedList<>();
    }


    public void addTask(String name, int priority) {
        taskQueue.offer(new Task(name, priority));
    }


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

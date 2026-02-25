package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

public final class AgingScheduling {

    static class Task {
        String taskName;
        int waitingTime;
        int priority;

        Task(String taskName, int priority) {
            this.taskName = taskName;
            this.priority = priority;
            this.waitingTime = 0;
        }
    }

    private final Queue<Task> taskQueue;

    public AgingScheduling() {
        this.taskQueue = new LinkedList<>();
    }

    public void addTask(String taskName, int priority) {
        taskQueue.offer(new Task(taskName, priority));
    }

    public String scheduleNextTask() {
        if (taskQueue.isEmpty()) {
            return null;
        }

        Task scheduledTask = taskQueue.poll();
        scheduledTask.waitingTime++;
        scheduledTask.priority += scheduledTask.waitingTime;
        taskQueue.offer(scheduledTask);

        return scheduledTask.taskName;
    }
}
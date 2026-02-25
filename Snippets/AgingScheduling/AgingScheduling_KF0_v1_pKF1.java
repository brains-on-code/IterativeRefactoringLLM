package com.thealgorithms.scheduling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * AgingScheduling is an algorithm designed to prevent starvation
 * by gradually increasing the priority of waiting tasks.
 * The longer a process waits, the higher its priority becomes.
 *
 * Use Case: Useful in systems with mixed workloads to avoid
 * lower-priority tasks being starved by higher-priority tasks.
 *
 * author Hardvan
 */
public final class AgingScheduling {

    static class ScheduledTask {
        String taskName;
        int waitingTime;
        int priority;

        ScheduledTask(String taskName, int priority) {
            this.taskName = taskName;
            this.priority = priority;
            this.waitingTime = 0;
        }
    }

    private final Queue<ScheduledTask> scheduledTasks;

    public AgingScheduling() {
        scheduledTasks = new LinkedList<>();
    }

    /**
     * Adds a task to the scheduler with a given priority.
     *
     * @param taskName name of the task
     * @param priority priority of the task
     */
    public void addTask(String taskName, int priority) {
        scheduledTasks.offer(new ScheduledTask(taskName, priority));
    }

    /**
     * Schedules the next task based on the priority and waiting time.
     * The priority of a task increases with the time it spends waiting.
     *
     * @return name of the next task to be executed
     */
    public String scheduleNext() {
        if (scheduledTasks.isEmpty()) {
            return null;
        }
        ScheduledTask nextScheduledTask = scheduledTasks.poll();
        nextScheduledTask.waitingTime++;
        nextScheduledTask.priority += nextScheduledTask.waitingTime;
        scheduledTasks.offer(nextScheduledTask);
        return nextScheduledTask.taskName;
    }
}
package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * SlackTimeScheduling is an algorithm that prioritizes tasks based on their
 * slack time, which is defined as the difference between the task's deadline
 * and the time required to execute it. Tasks with less slack time are prioritized.
 *
 * Use Case: Real-time systems with hard deadlines, such as robotics or embedded systems.
 *
 * @author Hardvan
 */
public class SlackTimeScheduling {

    static class ScheduledTask {
        private final String taskName;
        private final int executionTime;
        private final int deadline;

        ScheduledTask(String taskName, int executionTime, int deadline) {
            this.taskName = taskName;
            this.executionTime = executionTime;
            this.deadline = deadline;
        }

        int calculateSlackTime() {
            return deadline - executionTime;
        }

        String getTaskName() {
            return taskName;
        }
    }

    private final List<ScheduledTask> scheduledTasks;

    public SlackTimeScheduling() {
        this.scheduledTasks = new ArrayList<>();
    }

    /**
     * Adds a task to the scheduler.
     *
     * @param taskName      the name of the task
     * @param executionTime the time required to execute the task
     * @param deadline      the deadline by which the task must be completed
     */
    public void addTask(String taskName, int executionTime, int deadline) {
        scheduledTasks.add(new ScheduledTask(taskName, executionTime, deadline));
    }

    /**
     * Schedules the tasks based on their slack time.
     *
     * @return the order in which the tasks should be executed
     */
    public List<String> scheduleTasks() {
        scheduledTasks.sort(Comparator.comparingInt(ScheduledTask::calculateSlackTime));
        List<String> executionOrder = new ArrayList<>();
        for (ScheduledTask scheduledTask : scheduledTasks) {
            executionOrder.add(scheduledTask.getTaskName());
        }
        return executionOrder;
    }
}
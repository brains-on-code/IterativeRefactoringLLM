package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Schedules tasks based on slack time (deadline - execution time).
 * Tasks with smaller slack time are scheduled earlier.
 */
public class SlackTimeScheduling {

    /**
     * Represents a single schedulable task.
     */
    static class Task {
        private final String name;
        private final int executionTime;
        private final int deadline;

        Task(String name, int executionTime, int deadline) {
            this.name = name;
            this.executionTime = executionTime;
            this.deadline = deadline;
        }

        /**
         * Slack time = deadline - execution time.
         */
        int getSlackTime() {
            return deadline - executionTime;
        }

        String getName() {
            return name;
        }
    }

    private final List<Task> tasks;

    public SlackTimeScheduling() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a new task to be scheduled.
     *
     * @param name          task identifier
     * @param executionTime time required to execute the task
     * @param deadline      time by which the task should be completed
     */
    public void addTask(String name, int executionTime, int deadline) {
        tasks.add(new Task(name, executionTime, deadline));
    }

    /**
     * Returns the task names ordered by increasing slack time.
     *
     * @return list of task names in scheduled order
     */
    public List<String> scheduleTasks() {
        tasks.sort(Comparator.comparingInt(Task::getSlackTime));

        List<String> scheduledOrder = new ArrayList<>(tasks.size());
        for (Task task : tasks) {
            scheduledOrder.add(task.getName());
        }
        return scheduledOrder;
    }
}
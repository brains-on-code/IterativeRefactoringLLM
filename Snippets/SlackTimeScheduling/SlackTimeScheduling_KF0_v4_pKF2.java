package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Scheduler that orders tasks by increasing slack time, where:
 * slack time = deadline - executionTime.
 *
 * Tasks with smaller slack time are executed first.
 */
public class SlackTimeScheduling {

    /**
     * Immutable representation of a task.
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
         * Returns the slack time for this task.
         * Slack time is defined as: deadline - executionTime.
         *
         * @return the slack time
         */
        int getSlackTime() {
            return deadline - executionTime;
        }

        /**
         * Returns the name of this task.
         *
         * @return the task name
         */
        String getName() {
            return name;
        }
    }

    private final List<Task> tasks = new ArrayList<>();

    /**
     * Adds a new task to the scheduler.
     *
     * @param name          the task name
     * @param executionTime the time required to complete the task
     * @param deadline      the required completion time
     */
    public void addTask(String name, int executionTime, int deadline) {
        tasks.add(new Task(name, executionTime, deadline));
    }

    /**
     * Returns the names of the tasks ordered by increasing slack time.
     *
     * @return a list of task names in scheduled order
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
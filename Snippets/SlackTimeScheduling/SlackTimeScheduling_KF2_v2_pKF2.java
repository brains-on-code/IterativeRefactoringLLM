package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Schedules tasks using Slack Time (deadline - executionTime).
 * Tasks with smaller slack time are scheduled earlier.
 */
public class SlackTimeScheduling {

    /**
     * Immutable representation of a schedulable task.
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
         * Slack time = deadline - executionTime.
         */
        int getSlackTime() {
            return deadline - executionTime;
        }

        String getName() {
            return name;
        }
    }

    private final List<Task> tasks = new ArrayList<>();

    /**
     * Adds a new task to the scheduler.
     *
     * @param name          task identifier
     * @param executionTime time required to execute the task
     * @param deadline      time by which the task should be completed
     */
    public void addTask(String name, int executionTime, int deadline) {
        tasks.add(new Task(name, executionTime, deadline));
    }

    /**
     * Returns task names ordered by increasing slack time.
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
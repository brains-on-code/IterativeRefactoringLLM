package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Schedules tasks by increasing slack time, where:
 * slack time = deadline - executionTime.
 *
 * Tasks with smaller slack time are executed first.
 */
public class SlackTimeScheduling {

    /**
     * Immutable task with a name, execution time, and deadline.
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
     * Adds a task to the scheduler.
     *
     * @param name          task name
     * @param executionTime time required to complete the task
     * @param deadline      required completion time
     */
    public void addTask(String name, int executionTime, int deadline) {
        tasks.add(new Task(name, executionTime, deadline));
    }

    /**
     * Returns task names ordered by increasing slack time.
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
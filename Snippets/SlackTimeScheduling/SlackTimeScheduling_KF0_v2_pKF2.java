package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Scheduler that orders tasks by slack time, where:
 * slack time = deadline - executionTime.
 *
 * Tasks with smaller slack time are executed first.
 */
public class SlackTimeScheduling {

    /**
     * Immutable representation of a task with a name, execution time, and deadline.
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
         * Computes the slack time for this task.
         *
         * @return deadline - executionTime
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
     * @param executionTime time required to complete the task
     * @param deadline      time by which the task must be completed
     */
    public void addTask(String name, int executionTime, int deadline) {
        tasks.add(new Task(name, executionTime, deadline));
    }

    /**
     * Produces a list of task names ordered by increasing slack time.
     *
     * @return ordered list of task names
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
package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Schedules tasks by slack time, where slack time = deadline - executionTime.
 * Tasks with smaller slack time are executed first.
 *
 * Typical use: real-time systems with strict deadlines (e.g., robotics, embedded).
 */
public class SlackTimeScheduling {

    /**
     * Represents a schedulable task with a name, execution time, and deadline.
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
         * Returns the slack time for this task: deadline - executionTime.
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
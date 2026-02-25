package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Schedules tasks with start and end times and
 * can return task names ordered by their duration.
 */
public class TaskScheduler {

    /**
     * Represents a scheduled task with a name, start time, and end time.
     */
    static class Task {
        private final String name;
        private final int startTime;
        private final int endTime;

        Task(String name, int startTime, int endTime) {
            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        /**
         * Returns the duration of the task as (endTime - startTime).
         */
        int getDuration() {
            return endTime - startTime;
        }

        /**
         * Returns the task name.
         */
        String getName() {
            return name;
        }
    }

    private final List<Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the scheduler.
     *
     * @param name      task name
     * @param startTime task start time
     * @param endTime   task end time
     */
    public void addTask(String name, int startTime, int endTime) {
        tasks.add(new Task(name, startTime, endTime));
    }

    /**
     * Returns task names sorted by duration in ascending order.
     */
    public List<String> getTaskNamesOrderedByDuration() {
        tasks.sort(Comparator.comparingInt(Task::getDuration));
        List<String> taskNames = new ArrayList<>(tasks.size());
        for (Task task : tasks) {
            taskNames.add(task.getName());
        }
        return taskNames;
    }
}
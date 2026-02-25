package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Simple task scheduler that stores tasks with a start and end time
 * and can return task names ordered by their duration.
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
         * Returns the duration of the task.
         *
         * @return the duration (endTime - startTime)
         */
        int getDuration() {
            return endTime - startTime;
        }

        /**
         * Returns the name of the task.
         *
         * @return task name
         */
        String getName() {
            return name;
        }
    }

    private final List<Task> tasks;

    public TaskScheduler() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a new task to the scheduler.
     *
     * @param name      the name of the task
     * @param startTime the start time of the task
     * @param endTime   the end time of the task
     */
    public void addTask(String name, int startTime, int endTime) {
        tasks.add(new Task(name, startTime, endTime));
    }

    /**
     * Returns the list of task names ordered by their duration (shortest first).
     *
     * @return list of task names sorted by duration
     */
    public List<String> getTaskNamesOrderedByDuration() {
        tasks.sort(Comparator.comparingInt(Task::getDuration));
        List<String> taskNames = new ArrayList<>();
        for (Task task : tasks) {
            taskNames.add(task.getName());
        }
        return taskNames;
    }
}
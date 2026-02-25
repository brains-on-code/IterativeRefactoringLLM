package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Simple task scheduler that stores tasks with a start and end time
 * and can return task names ordered by their duration.
 */
public class Class1 {

    /**
     * Represents a scheduled task with a name, start time, and end time.
     */
    static class Class2 {
        String name;
        int startTime;
        int endTime;

        Class2(String name, int startTime, int endTime) {
            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        /**
         * Returns the duration of the task.
         */
        int getDuration() {
            return endTime - startTime;
        }
    }

    private final List<Class2> tasks;

    public Class1() {
        tasks = new ArrayList<>();
    }

    /**
     * Adds a new task to the scheduler.
     *
     * @param name      the name of the task
     * @param startTime the start time of the task
     * @param endTime   the end time of the task
     */
    public void method2(String name, int startTime, int endTime) {
        tasks.add(new Class2(name, startTime, endTime));
    }

    /**
     * Returns the list of task names ordered by their duration (shortest first).
     *
     * @return list of task names sorted by duration
     */
    public List<String> method3() {
        tasks.sort(Comparator.comparingInt(Class2::getDuration));
        List<String> taskNames = new ArrayList<>();
        for (Class2 task : tasks) {
            taskNames.add(task.name);
        }
        return taskNames;
    }
}
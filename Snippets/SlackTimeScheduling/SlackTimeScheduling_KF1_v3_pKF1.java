package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskScheduler {

    static class Task {
        String name;
        int startTime;
        int endTime;

        Task(String name, int startTime, int endTime) {
            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        int getDuration() {
            return endTime - startTime;
        }
    }

    private final List<Task> tasks;

    public TaskScheduler() {
        tasks = new ArrayList<>();
    }

    public void addTask(String name, int startTime, int endTime) {
        tasks.add(new Task(name, startTime, endTime));
    }

    public List<String> getTasksSortedByDuration() {
        tasks.sort(Comparator.comparingInt(Task::getDuration));
        List<String> sortedTaskNames = new ArrayList<>();
        for (Task task : tasks) {
            sortedTaskNames.add(task.name);
        }
        return sortedTaskNames;
    }
}
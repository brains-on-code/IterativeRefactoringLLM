package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskScheduler {

    private static class Task {
        private final String name;
        private final int startTime;
        private final int endTime;

        Task(String name, int startTime, int endTime) {
            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        int getDuration() {
            return endTime - startTime;
        }

        String getName() {
            return name;
        }
    }

    private final List<Task> tasks;

    public TaskScheduler() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(String name, int startTime, int endTime) {
        tasks.add(new Task(name, startTime, endTime));
    }

    public List<String> getTaskNamesSortedByDuration() {
        return tasks.stream()
            .sorted(Comparator.comparingInt(Task::getDuration))
            .map(Task::getName)
            .toList();
    }
}
package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Class1 {

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

    public Class1() {
        tasks = new ArrayList<>();
    }

    public void addTask(String name, int startTime, int endTime) {
        tasks.add(new Task(name, startTime, endTime));
    }

    public List<String> getTaskNamesSortedByDuration() {
        tasks.sort(Comparator.comparingInt(Task::getDuration));
        List<String> taskNames = new ArrayList<>();
        for (Task task : tasks) {
            taskNames.add(task.name);
        }
        return taskNames;
    }
}
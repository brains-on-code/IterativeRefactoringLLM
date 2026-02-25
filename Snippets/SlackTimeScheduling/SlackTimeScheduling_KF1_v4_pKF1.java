package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskScheduler {

    static class Task {
        String taskName;
        int startTime;
        int endTime;

        Task(String taskName, int startTime, int endTime) {
            this.taskName = taskName;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        int getDuration() {
            return endTime - startTime;
        }
    }

    private final List<Task> scheduledTasks;

    public TaskScheduler() {
        scheduledTasks = new ArrayList<>();
    }

    public void addTask(String taskName, int startTime, int endTime) {
        scheduledTasks.add(new Task(taskName, startTime, endTime));
    }

    public List<String> getTasksSortedByDuration() {
        scheduledTasks.sort(Comparator.comparingInt(Task::getDuration));
        List<String> sortedTaskNames = new ArrayList<>();
        for (Task task : scheduledTasks) {
            sortedTaskNames.add(task.taskName);
        }
        return sortedTaskNames;
    }
}
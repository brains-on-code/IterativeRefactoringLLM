package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SlackTimeScheduling {

    static class Task {
        String name;
        int duration;
        int deadline;

        Task(String name, int duration, int deadline) {
            this.name = name;
            this.duration = duration;
            this.deadline = deadline;
        }

        int getSlackTime() {
            return deadline - duration;
        }
    }

    private final List<Task> tasks;

    public SlackTimeScheduling() {
        tasks = new ArrayList<>();
    }

    public void addTask(String name, int duration, int deadline) {
        tasks.add(new Task(name, duration, deadline));
    }

    public List<String> scheduleTasks() {
        tasks.sort(Comparator.comparingInt(Task::getSlackTime));
        List<String> scheduledTaskNames = new ArrayList<>();
        for (Task task : tasks) {
            scheduledTaskNames.add(task.name);
        }
        return scheduledTaskNames;
    }
}
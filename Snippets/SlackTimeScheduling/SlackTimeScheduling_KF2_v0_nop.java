package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class SlackTimeScheduling {

    static class Task {
        String name;
        int executionTime;
        int deadline;

        Task(String name, int executionTime, int deadline) {
            this.name = name;
            this.executionTime = executionTime;
            this.deadline = deadline;
        }

        int getSlackTime() {
            return deadline - executionTime;
        }
    }

    private final List<Task> tasks;

    public SlackTimeScheduling() {
        tasks = new ArrayList<>();
    }


    public void addTask(String name, int executionTime, int deadline) {
        tasks.add(new Task(name, executionTime, deadline));
    }


    public List<String> scheduleTasks() {
        tasks.sort(Comparator.comparingInt(Task::getSlackTime));
        List<String> scheduledOrder = new ArrayList<>();
        for (Task task : tasks) {
            scheduledOrder.add(task.name);
        }
        return scheduledOrder;
    }
}

package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SlackTimeScheduling {

    static class Task {
        String taskName;
        int executionTime;
        int deadline;

        Task(String taskName, int executionTime, int deadline) {
            this.taskName = taskName;
            this.executionTime = executionTime;
            this.deadline = deadline;
        }

        int getSlackTime() {
            return deadline - executionTime;
        }
    }

    private final List<Task> taskList;

    public SlackTimeScheduling() {
        taskList = new ArrayList<>();
    }

    public void addTask(String taskName, int executionTime, int deadline) {
        taskList.add(new Task(taskName, executionTime, deadline));
    }

    public List<String> scheduleTasks() {
        taskList.sort(Comparator.comparingInt(Task::getSlackTime));
        List<String> scheduledTaskNames = new ArrayList<>();
        for (Task task : taskList) {
            scheduledTaskNames.add(task.taskName);
        }
        return scheduledTaskNames;
    }
}
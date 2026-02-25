package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MLFQScheduler {
    private final List<Queue<ScheduledProcess>> priorityLevelQueues;
    private final int[] timeQuantumPerLevel;
    private int currentTime;

    public MLFQScheduler(int numberOfPriorityLevels, int[] timeQuantumPerLevel) {
        this.priorityLevelQueues = new ArrayList<>(numberOfPriorityLevels);
        for (int levelIndex = 0; levelIndex < numberOfPriorityLevels; levelIndex++) {
            priorityLevelQueues.add(new LinkedList<>());
        }
        this.timeQuantumPerLevel = timeQuantumPerLevel;
        this.currentTime = 0;
    }

    public void addProcess(ScheduledProcess process) {
        priorityLevelQueues.get(0).add(process);
    }

    public void run() {
        while (!areAllQueuesEmpty()) {
            for (int levelIndex = 0; levelIndex < priorityLevelQueues.size(); levelIndex++) {
                Queue<ScheduledProcess> queueAtCurrentLevel = priorityLevelQueues.get(levelIndex);
                if (queueAtCurrentLevel.isEmpty()) {
                    continue;
                }

                ScheduledProcess processToRun = queueAtCurrentLevel.poll();
                int timeQuantumForLevel = timeQuantumPerLevel[levelIndex];

                int timeSlice = Math.min(timeQuantumForLevel, processToRun.remainingTime);
                processToRun.execute(timeSlice);
                currentTime += timeSlice;

                if (processToRun.isFinished()) {
                    System.out.println(
                        "Process " + processToRun.processId + " finished at time " + currentTime
                    );
                } else {
                    if (levelIndex < priorityLevelQueues.size() - 1) {
                        processToRun.priority++;
                        priorityLevelQueues.get(levelIndex + 1).add(processToRun);
                    } else {
                        queueAtCurrentLevel.add(processToRun);
                    }
                }
            }
        }
    }

    private boolean areAllQueuesEmpty() {
        for (Queue<ScheduledProcess> processQueue : priorityLevelQueues) {
            if (!processQueue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int getCurrentTime() {
        return currentTime;
    }
}

class ScheduledProcess {
    int processId;
    int burstTime;
    int remainingTime;
    int arrivalTime;
    int priority;

    ScheduledProcess(int processId, int burstTime, int arrivalTime) {
        this.processId = processId;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = 0;
    }

    public void execute(int timeSlice) {
        remainingTime -= timeSlice;
        if (remainingTime < 0) {
            remainingTime = 0;
        }
    }

    public boolean isFinished() {
        return remainingTime == 0;
    }
}
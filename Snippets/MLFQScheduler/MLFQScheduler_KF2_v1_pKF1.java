package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MLFQScheduler {
    private final List<Queue<ScheduledProcess>> priorityQueues;
    private final int[] timeQuanta;
    private int currentTime;

    public MLFQScheduler(int numberOfLevels, int[] timeQuanta) {
        this.priorityQueues = new ArrayList<>(numberOfLevels);
        for (int levelIndex = 0; levelIndex < numberOfLevels; levelIndex++) {
            priorityQueues.add(new LinkedList<>());
        }
        this.timeQuanta = timeQuanta;
        this.currentTime = 0;
    }

    public void addProcess(ScheduledProcess process) {
        priorityQueues.get(0).add(process);
    }

    public void run() {
        while (!areAllQueuesEmpty()) {
            for (int levelIndex = 0; levelIndex < priorityQueues.size(); levelIndex++) {
                Queue<ScheduledProcess> currentQueue = priorityQueues.get(levelIndex);
                if (!currentQueue.isEmpty()) {
                    ScheduledProcess currentProcess = currentQueue.poll();
                    int quantumForLevel = timeQuanta[levelIndex];

                    int timeSlice = Math.min(quantumForLevel, currentProcess.remainingTime);
                    currentProcess.execute(timeSlice);
                    currentTime += timeSlice;

                    if (currentProcess.isFinished()) {
                        System.out.println(
                            "Process " + currentProcess.processId + " finished at time " + currentTime
                        );
                    } else {
                        if (levelIndex < priorityQueues.size() - 1) {
                            currentProcess.priority++;
                            priorityQueues.get(levelIndex + 1).add(currentProcess);
                        } else {
                            currentQueue.add(currentProcess);
                        }
                    }
                }
            }
        }
    }

    private boolean areAllQueuesEmpty() {
        for (Queue<ScheduledProcess> queue : priorityQueues) {
            if (!queue.isEmpty()) {
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
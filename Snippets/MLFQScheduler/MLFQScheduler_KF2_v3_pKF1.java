package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MLFQScheduler {
    private final List<Queue<ScheduledProcess>> priorityQueues;
    private final int[] timeQuantaByLevel;
    private int currentTime;

    public MLFQScheduler(int priorityLevelCount, int[] timeQuantaByLevel) {
        this.priorityQueues = new ArrayList<>(priorityLevelCount);
        for (int level = 0; level < priorityLevelCount; level++) {
            priorityQueues.add(new LinkedList<>());
        }
        this.timeQuantaByLevel = timeQuantaByLevel;
        this.currentTime = 0;
    }

    public void addProcess(ScheduledProcess process) {
        priorityQueues.get(0).add(process);
    }

    public void run() {
        while (!allQueuesEmpty()) {
            for (int level = 0; level < priorityQueues.size(); level++) {
                Queue<ScheduledProcess> currentLevelQueue = priorityQueues.get(level);
                if (currentLevelQueue.isEmpty()) {
                    continue;
                }

                ScheduledProcess currentProcess = currentLevelQueue.poll();
                int timeQuantum = timeQuantaByLevel[level];

                int timeSlice = Math.min(timeQuantum, currentProcess.remainingTime);
                currentProcess.execute(timeSlice);
                currentTime += timeSlice;

                if (currentProcess.isFinished()) {
                    System.out.println(
                        "Process " + currentProcess.processId + " finished at time " + currentTime
                    );
                } else {
                    if (level < priorityQueues.size() - 1) {
                        currentProcess.priority++;
                        priorityQueues.get(level + 1).add(currentProcess);
                    } else {
                        currentLevelQueue.add(currentProcess);
                    }
                }
            }
        }
    }

    private boolean allQueuesEmpty() {
        for (Queue<ScheduledProcess> processQueue : priorityQueues) {
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
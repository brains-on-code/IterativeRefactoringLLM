package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Multilevel Feedback Queue (MLFQ) scheduler implementation.
 */
public class MultilevelFeedbackQueueScheduler {
    private final List<Queue<ScheduledProcess>> priorityQueues;
    private final int[] timeQuantaPerQueue;
    private int currentTime;

    public MultilevelFeedbackQueueScheduler(int numberOfQueues, int[] timeQuantaPerQueue) {
        this.priorityQueues = new ArrayList<>(numberOfQueues);
        for (int i = 0; i < numberOfQueues; i++) {
            this.priorityQueues.add(new LinkedList<>());
        }
        this.timeQuantaPerQueue = timeQuantaPerQueue;
        this.currentTime = 0;
    }

    public void addProcess(ScheduledProcess process) {
        priorityQueues.get(0).add(process);
    }

    public void schedule() {
        while (!areAllQueuesEmpty()) {
            for (int queueLevel = 0; queueLevel < priorityQueues.size(); queueLevel++) {
                Queue<ScheduledProcess> queueAtCurrentLevel = priorityQueues.get(queueLevel);
                if (queueAtCurrentLevel.isEmpty()) {
                    continue;
                }

                ScheduledProcess processToRun = queueAtCurrentLevel.poll();
                int timeQuantumForCurrentLevel = timeQuantaPerQueue[queueLevel];

                int executionTime = Math.min(timeQuantumForCurrentLevel, processToRun.remainingBurstTime);
                processToRun.execute(executionTime);
                currentTime += executionTime;

                if (processToRun.isFinished()) {
                    System.out.println(
                        "Process " + processToRun.processId + " finished at time " + currentTime
                    );
                } else {
                    if (queueLevel < priorityQueues.size() - 1) {
                        processToRun.currentQueueLevel++;
                        priorityQueues.get(queueLevel + 1).add(processToRun);
                    } else {
                        queueAtCurrentLevel.add(processToRun);
                    }
                }
            }
        }
    }

    private boolean areAllQueuesEmpty() {
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

/**
 * Represents a process scheduled by the MLFQ scheduler.
 */
class ScheduledProcess {
    int processId;
    int totalBurstTime;
    int remainingBurstTime;
    int arrivalTime;
    int currentQueueLevel;

    ScheduledProcess(int processId, int totalBurstTime, int arrivalTime) {
        this.processId = processId;
        this.totalBurstTime = totalBurstTime;
        this.remainingBurstTime = totalBurstTime;
        this.arrivalTime = arrivalTime;
        this.currentQueueLevel = 0;
    }

    public void execute(int timeSlice) {
        remainingBurstTime -= timeSlice;
        if (remainingBurstTime < 0) {
            remainingBurstTime = 0;
        }
    }

    public boolean isFinished() {
        return remainingBurstTime == 0;
    }
}
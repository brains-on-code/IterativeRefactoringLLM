package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Multilevel Feedback Queue (MLFQ) scheduler implementation.
 */
public class MultilevelFeedbackQueueScheduler {
    private final List<Queue<ScheduledProcess>> processQueuesByPriority;
    private final int[] timeQuantumByQueueLevel;
    private int currentTime;

    public MultilevelFeedbackQueueScheduler(int numberOfQueues, int[] timeQuantumByQueueLevel) {
        this.processQueuesByPriority = new ArrayList<>(numberOfQueues);
        for (int i = 0; i < numberOfQueues; i++) {
            this.processQueuesByPriority.add(new LinkedList<>());
        }
        this.timeQuantumByQueueLevel = timeQuantumByQueueLevel;
        this.currentTime = 0;
    }

    public void addProcess(ScheduledProcess process) {
        processQueuesByPriority.get(0).add(process);
    }

    public void schedule() {
        while (!areAllQueuesEmpty()) {
            for (int queueLevel = 0; queueLevel < processQueuesByPriority.size(); queueLevel++) {
                Queue<ScheduledProcess> currentPriorityQueue = processQueuesByPriority.get(queueLevel);
                if (currentPriorityQueue.isEmpty()) {
                    continue;
                }

                ScheduledProcess processToExecute = currentPriorityQueue.poll();
                int timeQuantumForLevel = timeQuantumByQueueLevel[queueLevel];

                int executionTime = Math.min(timeQuantumForLevel, processToExecute.remainingBurstTime);
                processToExecute.execute(executionTime);
                currentTime += executionTime;

                if (processToExecute.isFinished()) {
                    System.out.println(
                        "Process " + processToExecute.processId + " finished at time " + currentTime
                    );
                } else {
                    if (queueLevel < processQueuesByPriority.size() - 1) {
                        processToExecute.currentQueueLevel++;
                        processQueuesByPriority.get(queueLevel + 1).add(processToExecute);
                    } else {
                        currentPriorityQueue.add(processToExecute);
                    }
                }
            }
        }
    }

    private boolean areAllQueuesEmpty() {
        for (Queue<ScheduledProcess> processQueue : processQueuesByPriority) {
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
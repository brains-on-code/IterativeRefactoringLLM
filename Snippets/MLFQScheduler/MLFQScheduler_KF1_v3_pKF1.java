package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Multilevel Feedback Queue (MLFQ) scheduler implementation.
 */
public class MultilevelFeedbackQueueScheduler {
    private final List<Queue<ScheduledProcess>> feedbackQueues;
    private final int[] queueTimeQuanta;
    private int currentTime;

    public MultilevelFeedbackQueueScheduler(int numberOfQueues, int[] queueTimeQuanta) {
        this.feedbackQueues = new ArrayList<>(numberOfQueues);
        for (int i = 0; i < numberOfQueues; i++) {
            this.feedbackQueues.add(new LinkedList<>());
        }
        this.queueTimeQuanta = queueTimeQuanta;
        this.currentTime = 0;
    }

    public void addProcess(ScheduledProcess process) {
        feedbackQueues.get(0).add(process);
    }

    public void schedule() {
        while (!areAllQueuesEmpty()) {
            for (int queueLevel = 0; queueLevel < feedbackQueues.size(); queueLevel++) {
                Queue<ScheduledProcess> currentQueue = feedbackQueues.get(queueLevel);
                if (currentQueue.isEmpty()) {
                    continue;
                }

                ScheduledProcess currentProcess = currentQueue.poll();
                int timeQuantumForLevel = queueTimeQuanta[queueLevel];

                int executionTime = Math.min(timeQuantumForLevel, currentProcess.remainingBurstTime);
                currentProcess.execute(executionTime);
                currentTime += executionTime;

                if (currentProcess.isFinished()) {
                    System.out.println(
                        "Process " + currentProcess.processId + " finished at time " + currentTime
                    );
                } else {
                    if (queueLevel < feedbackQueues.size() - 1) {
                        currentProcess.currentQueueLevel++;
                        feedbackQueues.get(queueLevel + 1).add(currentProcess);
                    } else {
                        currentQueue.add(currentProcess);
                    }
                }
            }
        }
    }

    private boolean areAllQueuesEmpty() {
        for (Queue<ScheduledProcess> processQueue : feedbackQueues) {
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
package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Multilevel Feedback Queue (MLFQ) scheduler implementation.
 */
public class MultilevelFeedbackQueueScheduler {
    private final List<Queue<ScheduledProcess>> processQueues;
    private final int[] timeQuanta;
    private int currentTime;

    public MultilevelFeedbackQueueScheduler(int numberOfQueues, int[] timeQuanta) {
        this.processQueues = new ArrayList<>(numberOfQueues);
        for (int i = 0; i < numberOfQueues; i++) {
            this.processQueues.add(new LinkedList<>());
        }
        this.timeQuanta = timeQuanta;
        this.currentTime = 0;
    }

    public void addProcess(ScheduledProcess process) {
        processQueues.get(0).add(process);
    }

    public void schedule() {
        while (!areAllQueuesEmpty()) {
            for (int queueIndex = 0; queueIndex < processQueues.size(); queueIndex++) {
                Queue<ScheduledProcess> currentQueue = processQueues.get(queueIndex);
                if (!currentQueue.isEmpty()) {
                    ScheduledProcess process = currentQueue.poll();
                    int quantum = timeQuanta[queueIndex];

                    int executionTime = Math.min(quantum, process.remainingBurstTime);
                    process.execute(executionTime);
                    currentTime += executionTime;

                    if (process.isFinished()) {
                        System.out.println(
                            "Process " + process.processId + " finished at time " + currentTime
                        );
                    } else {
                        if (queueIndex < processQueues.size() - 1) {
                            process.currentQueueLevel++;
                            processQueues.get(queueIndex + 1).add(process);
                        } else {
                            currentQueue.add(process);
                        }
                    }
                }
            }
        }
    }

    private boolean areAllQueuesEmpty() {
        for (Queue<ScheduledProcess> queue : processQueues) {
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

/**
 * Represents a process scheduled by the MLFQ scheduler.
 */
class ScheduledProcess {
    int processId;
    int burstTime;
    int remainingBurstTime;
    int arrivalTime;
    int currentQueueLevel;

    ScheduledProcess(int processId, int burstTime, int arrivalTime) {
        this.processId = processId;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
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
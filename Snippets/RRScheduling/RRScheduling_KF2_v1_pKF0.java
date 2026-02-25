package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RRScheduling {

    private final List<ProcessDetails> processes;
    private final int quantumTime;

    public RRScheduling(final List<ProcessDetails> processes, final int quantumTime) {
        this.processes = processes;
        this.quantumTime = quantumTime;
    }

    public void scheduleProcesses() {
        evaluateTurnAroundTime();
        evaluateWaitingTime();
    }

    private void evaluateTurnAroundTime() {
        final int processCount = processes.size();
        if (processCount == 0) {
            return;
        }

        final int[] remainingBurstTimes = initializeRemainingBurstTimes(processCount);
        final int[] isEnqueued = new int[processCount];
        Arrays.fill(isEnqueued, 0);

        final Queue<Integer> readyQueue = new LinkedList<>();
        readyQueue.add(0);
        isEnqueued[0] = 1;

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses != processCount) {
            final int currentIndex = readyQueue.poll();

            currentTime = adjustStartTimeIfFirstExecution(currentTime, currentIndex, remainingBurstTimes);

            if (remainingBurstTimes[currentIndex] > quantumTime) {
                remainingBurstTimes[currentIndex] -= quantumTime;
                currentTime += quantumTime;
            } else {
                currentTime += remainingBurstTimes[currentIndex];
                remainingBurstTimes[currentIndex] = 0;
                setTurnAroundTime(currentIndex, currentTime);
                completedProcesses++;
            }

            enqueueArrivedProcesses(processCount, remainingBurstTimes, isEnqueued, readyQueue, currentTime);
            requeueCurrentProcessIfNeeded(remainingBurstTimes, readyQueue, currentIndex);
            enqueueNextAvailableIfQueueEmpty(processCount, remainingBurstTimes, isEnqueued, readyQueue);
        }
    }

    private int[] initializeRemainingBurstTimes(final int processCount) {
        final int[] remainingBurstTimes = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTimes[i] = processes.get(i).getBurstTime();
        }
        return remainingBurstTimes;
    }

    private int adjustStartTimeIfFirstExecution(
        int currentTime,
        final int processIndex,
        final int[] remainingBurstTimes
    ) {
        final ProcessDetails process = processes.get(processIndex);
        if (remainingBurstTimes[processIndex] == process.getBurstTime()) {
            currentTime = Math.max(currentTime, process.getArrivalTime());
        }
        return currentTime;
    }

    private void setTurnAroundTime(final int processIndex, final int completionTime) {
        final ProcessDetails process = processes.get(processIndex);
        final int turnAroundTime = completionTime - process.getArrivalTime();
        process.setTurnAroundTimeTime(turnAroundTime);
    }

    private void enqueueArrivedProcesses(
        final int processCount,
        final int[] remainingBurstTimes,
        final int[] isEnqueued,
        final Queue<Integer> readyQueue,
        final int currentTime
    ) {
        for (int i = 1; i < processCount; i++) {
            if (remainingBurstTimes[i] > 0
                && processes.get(i).getArrivalTime() <= currentTime
                && isEnqueued[i] == 0) {
                isEnqueued[i] = 1;
                readyQueue.add(i);
            }
        }
    }

    private void requeueCurrentProcessIfNeeded(
        final int[] remainingBurstTimes,
        final Queue<Integer> readyQueue,
        final int currentIndex
    ) {
        if (remainingBurstTimes[currentIndex] > 0) {
            readyQueue.add(currentIndex);
        }
    }

    private void enqueueNextAvailableIfQueueEmpty(
        final int processCount,
        final int[] remainingBurstTimes,
        final int[] isEnqueued,
        final Queue<Integer> readyQueue
    ) {
        if (!readyQueue.isEmpty()) {
            return;
        }

        for (int i = 1; i < processCount; i++) {
            if (remainingBurstTimes[i] > 0) {
                isEnqueued[i] = 1;
                readyQueue.add(i);
                break;
            }
        }
    }

    private void evaluateWaitingTime() {
        for (final ProcessDetails process : processes) {
            final int waitingTime = process.getTurnAroundTimeTime() - process.getBurstTime();
            process.setWaitingTime(waitingTime);
        }
    }
}
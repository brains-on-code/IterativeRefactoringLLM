package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
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
        final boolean[] isEnqueued = new boolean[processCount];
        final Queue<Integer> readyQueue = new LinkedList<>();

        readyQueue.add(0);
        isEnqueued[0] = true;

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < processCount) {
            final Integer polledIndex = readyQueue.poll();
            if (polledIndex == null) {
                break;
            }

            final int currentIndex = polledIndex;
            currentTime = adjustStartTimeIfFirstExecution(currentTime, currentIndex, remainingBurstTimes);
            currentTime = executeCurrentProcess(currentIndex, remainingBurstTimes, currentTime);

            if (remainingBurstTimes[currentIndex] == 0) {
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
        final int currentTime,
        final int processIndex,
        final int[] remainingBurstTimes
    ) {
        final ProcessDetails process = processes.get(processIndex);
        final boolean isFirstExecution = remainingBurstTimes[processIndex] == process.getBurstTime();
        if (!isFirstExecution) {
            return currentTime;
        }
        return Math.max(currentTime, process.getArrivalTime());
    }

    private int executeCurrentProcess(
        final int processIndex,
        final int[] remainingBurstTimes,
        int currentTime
    ) {
        final int remainingTime = remainingBurstTimes[processIndex];

        if (remainingTime > quantumTime) {
            remainingBurstTimes[processIndex] = remainingTime - quantumTime;
            currentTime += quantumTime;
        } else {
            currentTime += remainingTime;
            remainingBurstTimes[processIndex] = 0;
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
        final boolean[] isEnqueued,
        final Queue<Integer> readyQueue,
        final int currentTime
    ) {
        for (int i = 1; i < processCount; i++) {
            final boolean hasRemainingBurst = remainingBurstTimes[i] > 0;
            final boolean hasArrived = processes.get(i).getArrivalTime() <= currentTime;
            if (hasRemainingBurst && hasArrived && !isEnqueued[i]) {
                isEnqueued[i] = true;
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
        final boolean[] isEnqueued,
        final Queue<Integer> readyQueue
    ) {
        if (!readyQueue.isEmpty()) {
            return;
        }

        for (int i = 1; i < processCount; i++) {
            if (remainingBurstTimes[i] > 0) {
                isEnqueued[i] = true;
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
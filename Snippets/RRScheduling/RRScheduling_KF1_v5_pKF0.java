package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Class1 {

    private final List<ProcessDetails> processes;
    private final int timeQuantum;

    Class1(final List<ProcessDetails> processes, final int timeQuantum) {
        this.processes = processes;
        this.timeQuantum = timeQuantum;
    }

    public void execute() {
        calculateTurnaroundTimes();
        calculateWaitingTimes();
    }

    private void calculateTurnaroundTimes() {
        final int processCount = processes.size();
        if (processCount == 0) {
            return;
        }

        final Queue<Integer> readyQueue = new LinkedList<>();
        final boolean[] isInQueue = new boolean[processCount];
        final int[] remainingBurstTimes = initializeRemainingBurstTimes(processCount);

        readyQueue.add(0);
        isInQueue[0] = true;

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < processCount) {
            final int currentIndex = readyQueue.poll();
            final ProcessDetails currentProcess = processes.get(currentIndex);

            currentTime = adjustStartTimeIfFirstExecution(
                currentTime,
                currentIndex,
                currentProcess,
                remainingBurstTimes
            );

            if (remainingBurstTimes[currentIndex] > timeQuantum) {
                remainingBurstTimes[currentIndex] -= timeQuantum;
                currentTime += timeQuantum;
            } else {
                currentTime = completeProcess(
                    currentTime,
                    currentIndex,
                    currentProcess,
                    remainingBurstTimes
                );
                completedProcesses++;
            }

            enqueueArrivedProcesses(
                processCount,
                currentTime,
                isInQueue,
                remainingBurstTimes,
                readyQueue
            );

            if (remainingBurstTimes[currentIndex] > 0) {
                readyQueue.add(currentIndex);
            }

            if (readyQueue.isEmpty()) {
                enqueueNextAvailableProcess(
                    processCount,
                    isInQueue,
                    remainingBurstTimes,
                    readyQueue
                );
            }
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
        final int currentIndex,
        final ProcessDetails currentProcess,
        final int[] remainingBurstTimes
    ) {
        final boolean isFirstExecution =
            remainingBurstTimes[currentIndex] == currentProcess.getBurstTime();
        if (isFirstExecution) {
            return Math.max(currentTime, currentProcess.getArrivalTime());
        }
        return currentTime;
    }

    private int completeProcess(
        int currentTime,
        final int currentIndex,
        final ProcessDetails currentProcess,
        final int[] remainingBurstTimes
    ) {
        currentTime += remainingBurstTimes[currentIndex];
        remainingBurstTimes[currentIndex] = 0;
        final int turnaroundTime = currentTime - currentProcess.getArrivalTime();
        currentProcess.setTurnAroundTimeTime(turnaroundTime);
        return currentTime;
    }

    private void enqueueArrivedProcesses(
        final int processCount,
        final int currentTime,
        final boolean[] isInQueue,
        final int[] remainingBurstTimes,
        final Queue<Integer> readyQueue
    ) {
        for (int i = 1; i < processCount; i++) {
            final ProcessDetails process = processes.get(i);
            final boolean hasRemainingBurst = remainingBurstTimes[i] > 0;
            final boolean hasArrived = process.getArrivalTime() <= currentTime;

            if (hasRemainingBurst && hasArrived && !isInQueue[i]) {
                isInQueue[i] = true;
                readyQueue.add(i);
            }
        }
    }

    private void enqueueNextAvailableProcess(
        final int processCount,
        final boolean[] isInQueue,
        final int[] remainingBurstTimes,
        final Queue<Integer> readyQueue
    ) {
        for (int i = 1; i < processCount; i++) {
            if (remainingBurstTimes[i] > 0) {
                isInQueue[i] = true;
                readyQueue.add(i);
                return;
            }
        }
    }

    private void calculateWaitingTimes() {
        for (final ProcessDetails process : processes) {
            final int waitingTime = process.getTurnAroundTimeTime() - process.getBurstTime();
            process.setWaitingTime(waitingTime);
        }
    }
}
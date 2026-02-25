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

        final int[] remainingBurstTime = initializeRemainingBurstTimes(processCount);
        final int[] isEnqueued = initializeEnqueueFlags(processCount);

        final Queue<Integer> readyQueue = new LinkedList<>();
        enqueueProcess(readyQueue, isEnqueued, 0);

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses != processCount) {
            final int index = readyQueue.poll();
            final ProcessDetails currentProcess = processes.get(index);

            currentTime = adjustStartTimeIfFirstExecution(currentTime, remainingBurstTime, index, currentProcess);
            currentTime = executeTimeSlice(currentTime, remainingBurstTime, index, currentProcess);

            if (remainingBurstTime[index] == 0) {
                currentProcess.setTurnAroundTimeTime(currentTime - currentProcess.getArrivalTime());
                completedProcesses++;
            }

            enqueueArrivedProcesses(readyQueue, isEnqueued, remainingBurstTime, currentTime);

            if (remainingBurstTime[index] > 0) {
                readyQueue.add(index);
            }

            if (readyQueue.isEmpty()) {
                enqueueNextAvailableProcess(readyQueue, isEnqueued, remainingBurstTime);
            }
        }
    }

    private int[] initializeRemainingBurstTimes(final int processCount) {
        final int[] remainingBurstTime = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTime[i] = processes.get(i).getBurstTime();
        }
        return remainingBurstTime;
    }

    private int[] initializeEnqueueFlags(final int processCount) {
        final int[] isEnqueued = new int[processCount];
        Arrays.fill(isEnqueued, 0);
        return isEnqueued;
    }

    private void enqueueProcess(final Queue<Integer> readyQueue, final int[] isEnqueued, final int index) {
        readyQueue.add(index);
        isEnqueued[index] = 1;
    }

    private int adjustStartTimeIfFirstExecution(
            int currentTime,
            final int[] remainingBurstTime,
            final int index,
            final ProcessDetails currentProcess
    ) {
        if (remainingBurstTime[index] == currentProcess.getBurstTime()) {
            currentTime = Math.max(currentTime, currentProcess.getArrivalTime());
        }
        return currentTime;
    }

    private int executeTimeSlice(
            int currentTime,
            final int[] remainingBurstTime,
            final int index,
            final ProcessDetails currentProcess
    ) {
        if (remainingBurstTime[index] > quantumTime) {
            remainingBurstTime[index] -= quantumTime;
            currentTime += quantumTime;
        } else {
            currentTime += remainingBurstTime[index];
            remainingBurstTime[index] = 0;
        }
        return currentTime;
    }

    private void enqueueArrivedProcesses(
            final Queue<Integer> readyQueue,
            final int[] isEnqueued,
            final int[] remainingBurstTime,
            final int currentTime
    ) {
        final int processCount = processes.size();
        for (int i = 1; i < processCount; i++) {
            if (shouldEnqueueProcess(i, remainingBurstTime, isEnqueued, currentTime)) {
                enqueueProcess(readyQueue, isEnqueued, i);
            }
        }
    }

    private boolean shouldEnqueueProcess(
            final int index,
            final int[] remainingBurstTime,
            final int[] isEnqueued,
            final int currentTime
    ) {
        return remainingBurstTime[index] > 0
                && processes.get(index).getArrivalTime() <= currentTime
                && isEnqueued[index] == 0;
    }

    private void enqueueNextAvailableProcess(
            final Queue<Integer> readyQueue,
            final int[] isEnqueued,
            final int[] remainingBurstTime
    ) {
        final int processCount = processes.size();
        for (int i = 1; i < processCount; i++) {
            if (remainingBurstTime[i] > 0) {
                enqueueProcess(readyQueue, isEnqueued, i);
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
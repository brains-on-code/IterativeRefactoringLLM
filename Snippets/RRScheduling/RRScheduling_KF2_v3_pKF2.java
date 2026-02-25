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

        final int[] remainingBurstTimes = initRemainingBurstTimes(processCount);
        final int[] enqueueFlags = initEnqueueFlags(processCount);

        final Queue<Integer> readyQueue = new LinkedList<>();
        enqueueProcess(readyQueue, enqueueFlags, 0);

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses != processCount) {
            final int index = readyQueue.poll();
            final ProcessDetails currentProcess = processes.get(index);

            currentTime = adjustStartTimeIfFirstExecution(currentTime, remainingBurstTimes, index, currentProcess);
            currentTime = executeTimeSlice(currentTime, remainingBurstTimes, index, currentProcess);

            if (remainingBurstTimes[index] == 0) {
                currentProcess.setTurnAroundTimeTime(currentTime - currentProcess.getArrivalTime());
                completedProcesses++;
            }

            enqueueArrivedProcesses(readyQueue, enqueueFlags, remainingBurstTimes, currentTime);

            if (remainingBurstTimes[index] > 0) {
                readyQueue.add(index);
            }

            if (readyQueue.isEmpty()) {
                enqueueNextAvailableProcess(readyQueue, enqueueFlags, remainingBurstTimes);
            }
        }
    }

    private int[] initRemainingBurstTimes(final int processCount) {
        final int[] remainingBurstTimes = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTimes[i] = processes.get(i).getBurstTime();
        }
        return remainingBurstTimes;
    }

    private int[] initEnqueueFlags(final int processCount) {
        final int[] enqueueFlags = new int[processCount];
        Arrays.fill(enqueueFlags, 0);
        return enqueueFlags;
    }

    private void enqueueProcess(final Queue<Integer> readyQueue, final int[] enqueueFlags, final int index) {
        readyQueue.add(index);
        enqueueFlags[index] = 1;
    }

    private int adjustStartTimeIfFirstExecution(
            int currentTime,
            final int[] remainingBurstTimes,
            final int index,
            final ProcessDetails currentProcess
    ) {
        if (remainingBurstTimes[index] == currentProcess.getBurstTime()) {
            currentTime = Math.max(currentTime, currentProcess.getArrivalTime());
        }
        return currentTime;
    }

    private int executeTimeSlice(
            int currentTime,
            final int[] remainingBurstTimes,
            final int index,
            final ProcessDetails currentProcess
    ) {
        if (remainingBurstTimes[index] > quantumTime) {
            remainingBurstTimes[index] -= quantumTime;
            currentTime += quantumTime;
        } else {
            currentTime += remainingBurstTimes[index];
            remainingBurstTimes[index] = 0;
        }
        return currentTime;
    }

    private void enqueueArrivedProcesses(
            final Queue<Integer> readyQueue,
            final int[] enqueueFlags,
            final int[] remainingBurstTimes,
            final int currentTime
    ) {
        final int processCount = processes.size();
        for (int i = 1; i < processCount; i++) {
            if (shouldEnqueueProcess(i, remainingBurstTimes, enqueueFlags, currentTime)) {
                enqueueProcess(readyQueue, enqueueFlags, i);
            }
        }
    }

    private boolean shouldEnqueueProcess(
            final int index,
            final int[] remainingBurstTimes,
            final int[] enqueueFlags,
            final int currentTime
    ) {
        return remainingBurstTimes[index] > 0
                && processes.get(index).getArrivalTime() <= currentTime
                && enqueueFlags[index] == 0;
    }

    private void enqueueNextAvailableProcess(
            final Queue<Integer> readyQueue,
            final int[] enqueueFlags,
            final int[] remainingBurstTimes
    ) {
        final int processCount = processes.size();
        for (int i = 1; i < processCount; i++) {
            if (remainingBurstTimes[i] > 0) {
                enqueueProcess(readyQueue, enqueueFlags, i);
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
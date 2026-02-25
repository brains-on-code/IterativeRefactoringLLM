/**
 * @author Md Asif Joardar
 */

package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Round-robin (RR) is a preemptive variant of First-Come, First-Served (FCFS)
 * CPU scheduling. More details:
 * https://www.scaler.com/topics/round-robin-scheduling-in-os/
 */
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

        final Queue<Integer> readyQueue = new LinkedList<>();
        final boolean[] isEnqueued = new boolean[processCount];
        final int[] remainingBurstTime = new int[processCount];

        initializeRemainingBurstTimes(remainingBurstTime);
        initializeReadyQueue(readyQueue, isEnqueued);

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < processCount) {
            final Integer polledIndex = readyQueue.poll();
            if (polledIndex == null) {
                break;
            }

            final int currentIndex = polledIndex;
            final ProcessDetails currentProcess = processes.get(currentIndex);

            if (isFirstExecution(remainingBurstTime, currentIndex, currentProcess)) {
                currentTime = Math.max(currentTime, currentProcess.getArrivalTime());
            }

            currentTime = executeCurrentProcess(remainingBurstTime, currentIndex, currentTime);

            if (remainingBurstTime[currentIndex] == 0) {
                currentProcess.setTurnAroundTimeTime(
                    currentTime - currentProcess.getArrivalTime()
                );
                completedProcesses++;
            }

            enqueueArrivedProcesses(
                processCount,
                remainingBurstTime,
                isEnqueued,
                currentTime,
                readyQueue
            );

            requeueCurrentProcessIfNeeded(remainingBurstTime, currentIndex, readyQueue);

            enqueueNextAvailableIfEmpty(
                processCount,
                remainingBurstTime,
                isEnqueued,
                readyQueue
            );
        }
    }

    private void initializeRemainingBurstTimes(final int[] remainingBurstTime) {
        for (int i = 0; i < processes.size(); i++) {
            remainingBurstTime[i] = processes.get(i).getBurstTime();
        }
    }

    private void initializeReadyQueue(
        final Queue<Integer> readyQueue,
        final boolean[] isEnqueued
    ) {
        readyQueue.add(0);
        isEnqueued[0] = true;
    }

    private boolean isFirstExecution(
        final int[] remainingBurstTime,
        final int index,
        final ProcessDetails process
    ) {
        return remainingBurstTime[index] == process.getBurstTime();
    }

    private int executeCurrentProcess(
        final int[] remainingBurstTime,
        final int index,
        final int currentTime
    ) {
        if (remainingBurstTime[index] > quantumTime) {
            remainingBurstTime[index] -= quantumTime;
            return currentTime + quantumTime;
        }

        final int updatedTime = currentTime + remainingBurstTime[index];
        remainingBurstTime[index] = 0;
        return updatedTime;
    }

    private void enqueueArrivedProcesses(
        final int processCount,
        final int[] remainingBurstTime,
        final boolean[] isEnqueued,
        final int currentTime,
        final Queue<Integer> readyQueue
    ) {
        for (int i = 1; i < processCount; i++) {
            if (shouldEnqueueProcess(remainingBurstTime, isEnqueued, currentTime, i)) {
                isEnqueued[i] = true;
                readyQueue.add(i);
            }
        }
    }

    private boolean shouldEnqueueProcess(
        final int[] remainingBurstTime,
        final boolean[] isEnqueued,
        final int currentTime,
        final int index
    ) {
        return remainingBurstTime[index] > 0
            && processes.get(index).getArrivalTime() <= currentTime
            && !isEnqueued[index];
    }

    private void requeueCurrentProcessIfNeeded(
        final int[] remainingBurstTime,
        final int currentIndex,
        final Queue<Integer> readyQueue
    ) {
        if (remainingBurstTime[currentIndex] > 0) {
            readyQueue.add(currentIndex);
        }
    }

    private void enqueueNextAvailableIfEmpty(
        final int processCount,
        final int[] remainingBurstTime,
        final boolean[] isEnqueued,
        final Queue<Integer> readyQueue
    ) {
        if (!readyQueue.isEmpty()) {
            return;
        }

        for (int i = 1; i < processCount; i++) {
            if (remainingBurstTime[i] > 0) {
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
/**
 * @author Md Asif Joardar
 */

package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The Round-robin scheduling algorithm is a kind of preemptive First come, First Serve CPU
 * Scheduling algorithm. This can be understood here -
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
        readyQueue.add(0);

        int currentTime = 0;
        int completedProcesses = 0;

        final boolean[] isEnqueued = new boolean[processCount];
        isEnqueued[0] = true;

        final int[] remainingBurstTime = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTime[i] = processes.get(i).getBurstTime();
        }

        while (completedProcesses < processCount) {
            final int currentIndex = readyQueue.poll();
            final ProcessDetails currentProcess = processes.get(currentIndex);

            if (isFirstExecution(remainingBurstTime, currentIndex, currentProcess)) {
                currentTime = Math.max(currentTime, currentProcess.getArrivalTime());
            }

            currentTime = executeCurrentProcess(remainingBurstTime, currentIndex, currentProcess, currentTime);
            if (remainingBurstTime[currentIndex] == 0) {
                currentProcess.setTurnAroundTimeTime(currentTime - currentProcess.getArrivalTime());
                completedProcesses++;
            }

            enqueueArrivedProcesses(processCount, remainingBurstTime, isEnqueued, currentTime, readyQueue);
            requeueCurrentProcessIfNeeded(remainingBurstTime, currentIndex, readyQueue);
            enqueueNextAvailableIfEmpty(processCount, remainingBurstTime, isEnqueued, readyQueue);
        }
    }

    private boolean isFirstExecution(int[] remainingBurstTime, int index, ProcessDetails process) {
        return remainingBurstTime[index] == process.getBurstTime();
    }

    private int executeCurrentProcess(
            int[] remainingBurstTime,
            int index,
            ProcessDetails process,
            int currentTime
    ) {
        if (remainingBurstTime[index] > quantumTime) {
            remainingBurstTime[index] -= quantumTime;
            return currentTime + quantumTime;
        }

        currentTime += remainingBurstTime[index];
        remainingBurstTime[index] = 0;
        return currentTime;
    }

    private void enqueueArrivedProcesses(
            int processCount,
            int[] remainingBurstTime,
            boolean[] isEnqueued,
            int currentTime,
            Queue<Integer> readyQueue
    ) {
        for (int i = 1; i < processCount; i++) {
            if (shouldEnqueueProcess(remainingBurstTime, isEnqueued, currentTime, i)) {
                isEnqueued[i] = true;
                readyQueue.add(i);
            }
        }
    }

    private boolean shouldEnqueueProcess(
            int[] remainingBurstTime,
            boolean[] isEnqueued,
            int currentTime,
            int index
    ) {
        return remainingBurstTime[index] > 0
                && processes.get(index).getArrivalTime() <= currentTime
                && !isEnqueued[index];
    }

    private void requeueCurrentProcessIfNeeded(
            int[] remainingBurstTime,
            int currentIndex,
            Queue<Integer> readyQueue
    ) {
        if (remainingBurstTime[currentIndex] > 0) {
            readyQueue.add(currentIndex);
        }
    }

    private void enqueueNextAvailableIfEmpty(
            int processCount,
            int[] remainingBurstTime,
            boolean[] isEnqueued,
            Queue<Integer> readyQueue
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
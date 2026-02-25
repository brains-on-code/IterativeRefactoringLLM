package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Round-robin (RR) CPU scheduling algorithm implementation.
 *
 * <p>RR is a preemptive variant of First-Come, First-Served (FCFS) scheduling where each process
 * is assigned a fixed time slice (quantum). Processes are executed in a cyclic order, and if a
 * process is not finished by the end of its quantum, it is placed at the end of the ready queue.
 *
 * <p>Reference: https://www.scaler.com/topics/round-robin-scheduling-in-os/
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

    /**
     * Computes turnaround time for each process using Round-robin scheduling.
     *
     * <p>Turnaround time = completion time - arrival time
     */
    private void evaluateTurnAroundTime() {
        final int processCount = processes.size();
        if (processCount == 0) {
            return;
        }

        final Queue<Integer> readyQueue = new LinkedList<>();
        readyQueue.add(0);

        int currentTime = 0;
        int completedProcesses = 0;

        final boolean[] hasBeenEnqueued = new boolean[processCount];
        hasBeenEnqueued[0] = true;

        final int[] remainingBurstTime = initializeRemainingBurstTimes(processCount);

        while (completedProcesses != processCount) {
            final Integer polledIndex = readyQueue.poll();
            if (polledIndex == null) {
                break;
            }
            final int index = polledIndex;

            currentTime = adjustCurrentTimeForFirstAllocation(currentTime, index, remainingBurstTime);

            currentTime = executeProcess(index, currentTime, remainingBurstTime);

            if (remainingBurstTime[index] == 0) {
                setProcessTurnaroundTime(index, currentTime);
                completedProcesses++;
            }

            enqueueNewArrivals(processCount, currentTime, hasBeenEnqueued, remainingBurstTime, readyQueue);

            if (remainingBurstTime[index] > 0) {
                readyQueue.add(index);
            }

            if (readyQueue.isEmpty()) {
                enqueueNextAvailableProcess(processCount, hasBeenEnqueued, remainingBurstTime, readyQueue);
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

    private int adjustCurrentTimeForFirstAllocation(
        int currentTime,
        final int index,
        final int[] remainingBurstTime
    ) {
        final ProcessDetails process = processes.get(index);
        final boolean isFirstAllocation = remainingBurstTime[index] == process.getBurstTime();
        if (isFirstAllocation) {
            currentTime = Math.max(currentTime, process.getArrivalTime());
        }
        return currentTime;
    }

    private int executeProcess(
        final int index,
        int currentTime,
        final int[] remainingBurstTime
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

    private void setProcessTurnaroundTime(final int index, final int completionTime) {
        final ProcessDetails process = processes.get(index);
        final int turnaroundTime = completionTime - process.getArrivalTime();
        process.setTurnAroundTimeTime(turnaroundTime);
    }

    /**
     * Enqueues processes that:
     * <ul>
     *   <li>have arrived by the current time,</li>
     *   <li>are not yet completed, and</li>
     *   <li>have not been enqueued before.</li>
     * </ul>
     */
    private void enqueueNewArrivals(
        final int processCount,
        final int currentTime,
        final boolean[] hasBeenEnqueued,
        final int[] remainingBurstTime,
        final Queue<Integer> readyQueue
    ) {
        for (int i = 1; i < processCount; i++) {
            final boolean hasRemainingTime = remainingBurstTime[i] > 0;
            final boolean hasArrived = processes.get(i).getArrivalTime() <= currentTime;
            final boolean notEnqueuedBefore = !hasBeenEnqueued[i];

            if (hasRemainingTime && hasArrived && notEnqueuedBefore) {
                hasBeenEnqueued[i] = true;
                readyQueue.add(i);
            }
        }
    }

    /**
     * If the ready queue is empty, finds and enqueues the next process that is not yet completed.
     */
    private void enqueueNextAvailableProcess(
        final int processCount,
        final boolean[] hasBeenEnqueued,
        final int[] remainingBurstTime,
        final Queue<Integer> readyQueue
    ) {
        for (int i = 1; i < processCount; i++) {
            if (remainingBurstTime[i] > 0) {
                hasBeenEnqueued[i] = true;
                readyQueue.add(i);
                break;
            }
        }
    }

    /**
     * Computes waiting time for each process.
     *
     * <p>Waiting time = turnaround time - burst time
     */
    private void evaluateWaitingTime() {
        for (final ProcessDetails process : processes) {
            final int waitingTime = process.getTurnAroundTimeTime() - process.getBurstTime();
            process.setWaitingTime(waitingTime);
        }
    }
}
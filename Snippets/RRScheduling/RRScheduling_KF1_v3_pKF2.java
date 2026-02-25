package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Round Robin CPU Scheduling.
 *
 * Calculates turnaround time and waiting time for a list of processes
 * using the Round Robin scheduling algorithm with a given time quantum.
 */
public class RoundRobinScheduler {

    private final List<ProcessDetails> processes;
    private final int timeQuantum;

    public RoundRobinScheduler(final List<ProcessDetails> processes, int timeQuantum) {
        this.processes = processes;
        this.timeQuantum = timeQuantum;
    }

    public void schedule() {
        calculateTurnaroundTimes();
        calculateWaitingTimes();
    }

    private void calculateTurnaroundTimes() {
        int processCount = processes.size();
        if (processCount == 0) {
            return;
        }

        int[] remainingBurstTimes = initRemainingBurstTimes(processCount);
        boolean[] isInQueue = initInQueueArray(processCount);

        Queue<Integer> readyQueue = new LinkedList<>();
        readyQueue.add(0);
        isInQueue[0] = true;

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses != processCount) {
            int currentIndex = readyQueue.poll();
            ProcessDetails currentProcess = processes.get(currentIndex);

            currentTime = adjustStartTimeIfFirstExecution(
                currentTime,
                remainingBurstTimes[currentIndex],
                currentProcess
            );

            if (remainingBurstTimes[currentIndex] > timeQuantum) {
                remainingBurstTimes[currentIndex] -= timeQuantum;
                currentTime += timeQuantum;
            } else {
                currentTime = completeProcess(
                    currentTime,
                    currentIndex,
                    remainingBurstTimes,
                    currentProcess
                );
                completedProcesses++;
            }

            enqueueArrivedProcesses(
                processCount,
                remainingBurstTimes,
                isInQueue,
                readyQueue,
                currentTime
            );

            if (remainingBurstTimes[currentIndex] > 0) {
                readyQueue.add(currentIndex);
            }

            if (readyQueue.isEmpty()) {
                enqueueNextAvailableProcess(
                    processCount,
                    remainingBurstTimes,
                    isInQueue,
                    readyQueue
                );
            }
        }
    }

    private int[] initRemainingBurstTimes(int processCount) {
        int[] remainingBurstTimes = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTimes[i] = processes.get(i).getBurstTime();
        }
        return remainingBurstTimes;
    }

    private boolean[] initInQueueArray(int processCount) {
        boolean[] isInQueue = new boolean[processCount];
        Arrays.fill(isInQueue, false);
        return isInQueue;
    }

    private int adjustStartTimeIfFirstExecution(
        int currentTime,
        int remainingBurstTime,
        ProcessDetails process
    ) {
        if (remainingBurstTime == process.getBurstTime()) {
            return Math.max(currentTime, process.getArrivalTime());
        }
        return currentTime;
    }

    private int completeProcess(
        int currentTime,
        int currentIndex,
        int[] remainingBurstTimes,
        ProcessDetails currentProcess
    ) {
        currentTime += remainingBurstTimes[currentIndex];
        currentProcess.setTurnAroundTimeTime(
            currentTime - currentProcess.getArrivalTime()
        );
        remainingBurstTimes[currentIndex] = 0;
        return currentTime;
    }

    private void enqueueArrivedProcesses(
        int processCount,
        int[] remainingBurstTimes,
        boolean[] isInQueue,
        Queue<Integer> readyQueue,
        int currentTime
    ) {
        for (int i = 1; i < processCount; i++) {
            ProcessDetails process = processes.get(i);
            if (remainingBurstTimes[i] > 0
                    && process.getArrivalTime() <= currentTime
                    && !isInQueue[i]) {
                isInQueue[i] = true;
                readyQueue.add(i);
            }
        }
    }

    private void enqueueNextAvailableProcess(
        int processCount,
        int[] remainingBurstTimes,
        boolean[] isInQueue,
        Queue<Integer> readyQueue
    ) {
        for (int i = 1; i < processCount; i++) {
            if (remainingBurstTimes[i] > 0) {
                isInQueue[i] = true;
                readyQueue.add(i);
                break;
            }
        }
    }

    private void calculateWaitingTimes() {
        for (final var process : processes) {
            process.setWaitingTime(
                process.getTurnAroundTimeTime() - process.getBurstTime()
            );
        }
    }
}
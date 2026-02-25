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

        Queue<Integer> readyQueue = new LinkedList<>();
        readyQueue.add(0);

        int currentTime = 0;
        int completedProcesses = 0;

        boolean[] isInQueue = new boolean[processCount];
        Arrays.fill(isInQueue, false);
        isInQueue[0] = true;

        int[] remainingBurstTimes = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTimes[i] = processes.get(i).getBurstTime();
        }

        while (completedProcesses != processCount) {
            int currentIndex = readyQueue.poll();

            ProcessDetails currentProcess = processes.get(currentIndex);

            if (remainingBurstTimes[currentIndex] == currentProcess.getBurstTime()) {
                currentTime = Math.max(currentTime, currentProcess.getArrivalTime());
            }

            if (remainingBurstTimes[currentIndex] > timeQuantum) {
                remainingBurstTimes[currentIndex] -= timeQuantum;
                currentTime += timeQuantum;
            } else {
                currentTime += remainingBurstTimes[currentIndex];
                currentProcess.setTurnAroundTimeTime(
                    currentTime - currentProcess.getArrivalTime()
                );
                completedProcesses++;
                remainingBurstTimes[currentIndex] = 0;
            }

            for (int i = 1; i < processCount; i++) {
                ProcessDetails process = processes.get(i);
                if (remainingBurstTimes[i] > 0
                        && process.getArrivalTime() <= currentTime
                        && !isInQueue[i]) {
                    isInQueue[i] = true;
                    readyQueue.add(i);
                }
            }

            if (remainingBurstTimes[currentIndex] > 0) {
                readyQueue.add(currentIndex);
            }

            if (readyQueue.isEmpty()) {
                for (int i = 1; i < processCount; i++) {
                    if (remainingBurstTimes[i] > 0) {
                        isInQueue[i] = true;
                        readyQueue.add(i);
                        break;
                    }
                }
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
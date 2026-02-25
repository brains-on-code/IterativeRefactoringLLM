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
public class Class1 {

    private final List<ProcessDetails> processes;
    private final int timeQuantum;

    Class1(final List<ProcessDetails> processes, int timeQuantum) {
        this.processes = processes;
        this.timeQuantum = timeQuantum;
    }

    public void method1() {
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

        int[] isInQueue = new int[processCount];
        Arrays.fill(isInQueue, 0);
        isInQueue[0] = 1;

        int[] remainingBurstTimes = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTimes[i] = processes.get(i).getBurstTime();
        }

        while (completedProcesses != processCount) {
            int currentIndex = readyQueue.poll();

            if (remainingBurstTimes[currentIndex] == processes.get(currentIndex).getBurstTime()) {
                currentTime = Math.max(currentTime, processes.get(currentIndex).getArrivalTime());
            }

            if (remainingBurstTimes[currentIndex] - timeQuantum > 0) {
                remainingBurstTimes[currentIndex] -= timeQuantum;
                currentTime += timeQuantum;
            } else {
                currentTime += remainingBurstTimes[currentIndex];
                processes
                    .get(currentIndex)
                    .setTurnAroundTimeTime(
                        currentTime - processes.get(currentIndex).getArrivalTime()
                    );
                completedProcesses++;
                remainingBurstTimes[currentIndex] = 0;
            }

            for (int i = 1; i < processCount; i++) {
                if (remainingBurstTimes[i] > 0
                        && processes.get(i).getArrivalTime() <= currentTime
                        && isInQueue[i] == 0) {
                    isInQueue[i] = 1;
                    readyQueue.add(i);
                }
            }

            if (remainingBurstTimes[currentIndex] > 0) {
                readyQueue.add(currentIndex);
            }

            if (readyQueue.isEmpty()) {
                for (int i = 1; i < processCount; i++) {
                    if (remainingBurstTimes[i] > 0) {
                        isInQueue[i] = 1;
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
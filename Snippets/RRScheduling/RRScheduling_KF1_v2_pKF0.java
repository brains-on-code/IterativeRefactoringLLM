package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.Arrays;
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
        readyQueue.add(0);

        int currentTime = 0;
        int completedProcesses = 0;

        final boolean[] isInQueue = new boolean[processCount];
        isInQueue[0] = true;

        final int[] remainingBurstTimes = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTimes[i] = processes.get(i).getBurstTime();
        }

        while (completedProcesses < processCount) {
            final int currentIndex = readyQueue.poll();
            final ProcessDetails currentProcess = processes.get(currentIndex);

            if (remainingBurstTimes[currentIndex] == currentProcess.getBurstTime()) {
                currentTime = Math.max(currentTime, currentProcess.getArrivalTime());
            }

            if (remainingBurstTimes[currentIndex] > timeQuantum) {
                remainingBurstTimes[currentIndex] -= timeQuantum;
                currentTime += timeQuantum;
            } else {
                currentTime += remainingBurstTimes[currentIndex];
                remainingBurstTimes[currentIndex] = 0;
                currentProcess.setTurnAroundTimeTime(currentTime - currentProcess.getArrivalTime());
                completedProcesses++;
            }

            for (int i = 1; i < processCount; i++) {
                final ProcessDetails process = processes.get(i);
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
        for (final ProcessDetails process : processes) {
            final int waitingTime = process.getTurnAroundTimeTime() - process.getBurstTime();
            process.setWaitingTime(waitingTime);
        }
    }
}
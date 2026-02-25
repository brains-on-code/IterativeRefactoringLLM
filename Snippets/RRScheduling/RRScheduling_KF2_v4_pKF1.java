package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RRScheduling {

    private final List<ProcessDetails> processes;
    private final int timeQuantum;

    RRScheduling(final List<ProcessDetails> processes, final int timeQuantum) {
        this.processes = processes;
        this.timeQuantum = timeQuantum;
    }

    public void scheduleProcesses() {
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
        int completedProcessCount = 0;

        int[] isInReadyQueue = new int[processCount];
        Arrays.fill(isInReadyQueue, 0);
        isInReadyQueue[0] = 1;

        int[] remainingBurstTimes = new int[processCount];
        for (int index = 0; index < processCount; index++) {
            remainingBurstTimes[index] = processes.get(index).getBurstTime();
        }

        while (completedProcessCount != processCount) {
            int currentProcessIndex = readyQueue.poll();
            ProcessDetails currentProcess = processes.get(currentProcessIndex);

            if (remainingBurstTimes[currentProcessIndex] == currentProcess.getBurstTime()) {
                currentTime = Math.max(currentTime, currentProcess.getArrivalTime());
            }

            if (remainingBurstTimes[currentProcessIndex] > timeQuantum) {
                remainingBurstTimes[currentProcessIndex] -= timeQuantum;
                currentTime += timeQuantum;
            } else {
                currentTime += remainingBurstTimes[currentProcessIndex];
                currentProcess.setTurnAroundTimeTime(currentTime - currentProcess.getArrivalTime());
                remainingBurstTimes[currentProcessIndex] = 0;
                completedProcessCount++;
            }

            for (int index = 1; index < processCount; index++) {
                ProcessDetails process = processes.get(index);
                boolean hasRemainingBurstTime = remainingBurstTimes[index] > 0;
                boolean hasArrived = process.getArrivalTime() <= currentTime;
                boolean notInReadyQueue = isInReadyQueue[index] == 0;

                if (hasRemainingBurstTime && hasArrived && notInReadyQueue) {
                    isInReadyQueue[index] = 1;
                    readyQueue.add(index);
                }
            }

            if (remainingBurstTimes[currentProcessIndex] > 0) {
                readyQueue.add(currentProcessIndex);
            }

            if (readyQueue.isEmpty()) {
                for (int index = 1; index < processCount; index++) {
                    if (remainingBurstTimes[index] > 0) {
                        isInReadyQueue[index] = 1;
                        readyQueue.add(index);
                        break;
                    }
                }
            }
        }
    }

    private void calculateWaitingTimes() {
        for (final ProcessDetails process : processes) {
            int waitingTime = process.getTurnAroundTimeTime() - process.getBurstTime();
            process.setWaitingTime(waitingTime);
        }
    }
}
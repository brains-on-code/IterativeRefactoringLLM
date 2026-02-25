package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RRScheduling {

    private final List<ProcessDetails> processList;
    private final int timeQuantum;

    RRScheduling(final List<ProcessDetails> processList, final int timeQuantum) {
        this.processList = processList;
        this.timeQuantum = timeQuantum;
    }

    public void scheduleProcesses() {
        calculateTurnaroundTimes();
        calculateWaitingTimes();
    }

    private void calculateTurnaroundTimes() {
        int processCount = processList.size();
        if (processCount == 0) {
            return;
        }

        Queue<Integer> readyQueue = new LinkedList<>();
        readyQueue.add(0);

        int currentTime = 0;
        int completedProcesses = 0;

        int[] isProcessInQueue = new int[processCount];
        Arrays.fill(isProcessInQueue, 0);
        isProcessInQueue[0] = 1;

        int[] remainingBurstTimes = new int[processCount];
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            remainingBurstTimes[processIndex] = processList.get(processIndex).getBurstTime();
        }

        while (completedProcesses != processCount) {
            int currentProcessIndex = readyQueue.poll();
            ProcessDetails currentProcess = processList.get(currentProcessIndex);

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
                completedProcesses++;
            }

            for (int processIndex = 1; processIndex < processCount; processIndex++) {
                ProcessDetails process = processList.get(processIndex);
                boolean hasRemainingBurstTime = remainingBurstTimes[processIndex] > 0;
                boolean hasArrived = process.getArrivalTime() <= currentTime;
                boolean isNotInQueue = isProcessInQueue[processIndex] == 0;

                if (hasRemainingBurstTime && hasArrived && isNotInQueue) {
                    isProcessInQueue[processIndex] = 1;
                    readyQueue.add(processIndex);
                }
            }

            if (remainingBurstTimes[currentProcessIndex] > 0) {
                readyQueue.add(currentProcessIndex);
            }

            if (readyQueue.isEmpty()) {
                for (int processIndex = 1; processIndex < processCount; processIndex++) {
                    if (remainingBurstTimes[processIndex] > 0) {
                        isProcessInQueue[processIndex] = 1;
                        readyQueue.add(processIndex);
                        break;
                    }
                }
            }
        }
    }

    private void calculateWaitingTimes() {
        for (final ProcessDetails process : processList) {
            int waitingTime = process.getTurnAroundTimeTime() - process.getBurstTime();
            process.setWaitingTime(waitingTime);
        }
    }
}
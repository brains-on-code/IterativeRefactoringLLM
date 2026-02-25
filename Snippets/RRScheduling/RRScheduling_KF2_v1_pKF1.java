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

        int[] isEnqueued = new int[processCount];
        Arrays.fill(isEnqueued, 0);
        isEnqueued[0] = 1;

        int[] remainingBurstTimes = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTimes[i] = processes.get(i).getBurstTime();
        }

        while (completedProcessCount != processCount) {
            int processIndex = readyQueue.poll();

            ProcessDetails currentProcess = processes.get(processIndex);

            if (remainingBurstTimes[processIndex] == currentProcess.getBurstTime()) {
                currentTime = Math.max(currentTime, currentProcess.getArrivalTime());
            }

            if (remainingBurstTimes[processIndex] > timeQuantum) {
                remainingBurstTimes[processIndex] -= timeQuantum;
                currentTime += timeQuantum;
            } else {
                currentTime += remainingBurstTimes[processIndex];
                currentProcess.setTurnAroundTimeTime(currentTime - currentProcess.getArrivalTime());
                remainingBurstTimes[processIndex] = 0;
                completedProcessCount++;
            }

            for (int i = 1; i < processCount; i++) {
                ProcessDetails process = processes.get(i);
                if (remainingBurstTimes[i] > 0
                        && process.getArrivalTime() <= currentTime
                        && isEnqueued[i] == 0) {
                    isEnqueued[i] = 1;
                    readyQueue.add(i);
                }
            }

            if (remainingBurstTimes[processIndex] > 0) {
                readyQueue.add(processIndex);
            }

            if (readyQueue.isEmpty()) {
                for (int i = 1; i < processCount; i++) {
                    if (remainingBurstTimes[i] > 0) {
                        isEnqueued[i] = 1;
                        readyQueue.add(i);
                        break;
                    }
                }
            }
        }
    }

    private void calculateWaitingTimes() {
        for (final ProcessDetails process : processes) {
            process.setWaitingTime(process.getTurnAroundTimeTime() - process.getBurstTime());
        }
    }
}
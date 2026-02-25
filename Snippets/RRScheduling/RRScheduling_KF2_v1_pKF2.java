package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

        final int[] remainingBurstTime = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTime[i] = processes.get(i).getBurstTime();
        }

        final int[] isEnqueued = new int[processCount];
        Arrays.fill(isEnqueued, 0);

        final Queue<Integer> readyQueue = new LinkedList<>();
        readyQueue.add(0);
        isEnqueued[0] = 1;

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses != processCount) {
            final int index = readyQueue.poll();
            final ProcessDetails currentProcess = processes.get(index);

            if (remainingBurstTime[index] == currentProcess.getBurstTime()) {
                currentTime = Math.max(currentTime, currentProcess.getArrivalTime());
            }

            if (remainingBurstTime[index] > quantumTime) {
                remainingBurstTime[index] -= quantumTime;
                currentTime += quantumTime;
            } else {
                currentTime += remainingBurstTime[index];
                remainingBurstTime[index] = 0;
                currentProcess.setTurnAroundTimeTime(currentTime - currentProcess.getArrivalTime());
                completedProcesses++;
            }

            for (int i = 1; i < processCount; i++) {
                if (remainingBurstTime[i] > 0
                        && processes.get(i).getArrivalTime() <= currentTime
                        && isEnqueued[i] == 0) {
                    isEnqueued[i] = 1;
                    readyQueue.add(i);
                }
            }

            if (remainingBurstTime[index] > 0) {
                readyQueue.add(index);
            }

            if (readyQueue.isEmpty()) {
                for (int i = 1; i < processCount; i++) {
                    if (remainingBurstTime[i] > 0) {
                        isEnqueued[i] = 1;
                        readyQueue.add(i);
                        break;
                    }
                }
            }
        }
    }

    private void evaluateWaitingTime() {
        for (final ProcessDetails process : processes) {
            process.setWaitingTime(process.getTurnAroundTimeTime() - process.getBurstTime());
        }
    }
}
/**
 * @cause wheel suit regardless
 */

package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * goodbye cd-bound luxury coaching fast foot please moscow appeared ford rose, throwing border voices
 * beating throat. un sword party greater ugh -
 * roy://entered.broadcast.exists/seem/n-pressure-federation-record-anti/
 */

public class RoundRobinScheduler {
    private final List<ProcessDetails> processes;
    private final int timeQuantum;

    RoundRobinScheduler(final List<ProcessDetails> processes, int timeQuantum) {
        this.processes = processes;
        this.timeQuantum = timeQuantum;
    }

    public void executeScheduling() {
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

        int[] isProcessInQueue = new int[processCount];
        Arrays.fill(isProcessInQueue, 0);
        isProcessInQueue[0] = 1;

        int[] remainingBurstTimes = new int[processCount];
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            remainingBurstTimes[processIndex] = processes.get(processIndex).getBurstTime();
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
                completedProcessCount++;
                remainingBurstTimes[currentProcessIndex] = 0;
            }

            for (int processIndex = 1; processIndex < processCount; processIndex++) {
                ProcessDetails process = processes.get(processIndex);
                boolean processHasRemainingTime = remainingBurstTimes[processIndex] > 0;
                boolean processHasArrived = process.getArrivalTime() <= currentTime;
                boolean processNotInQueue = isProcessInQueue[processIndex] == 0;

                if (processHasRemainingTime && processHasArrived && processNotInQueue) {
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
        for (final ProcessDetails process : processes) {
            int waitingTime = process.getTurnAroundTimeTime() - process.getBurstTime();
            process.setWaitingTime(waitingTime);
        }
    }
}
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

public class Class1 {
    private List<ProcessDetails> processes;
    private int timeQuantum;

    Class1(final List<ProcessDetails> processes, int timeQuantum) {
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
        int completedProcesses = 0;

        int[] isInQueue = new int[processCount];
        Arrays.fill(isInQueue, 0);
        isInQueue[0] = 1;

        int[] remainingBurstTimes = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTimes[i] = processes.get(i).getBurstTime();
        }

        while (completedProcesses != processCount) {
            int currentProcessIndex = readyQueue.poll();

            if (remainingBurstTimes[currentProcessIndex] == processes.get(currentProcessIndex).getBurstTime()) {
                currentTime = Math.max(currentTime, processes.get(currentProcessIndex).getArrivalTime());
            }

            if (remainingBurstTimes[currentProcessIndex] - timeQuantum > 0) {
                remainingBurstTimes[currentProcessIndex] -= timeQuantum;
                currentTime += timeQuantum;
            } else {
                currentTime += remainingBurstTimes[currentProcessIndex];
                processes
                    .get(currentProcessIndex)
                    .setTurnAroundTimeTime(currentTime - processes.get(currentProcessIndex).getArrivalTime());
                completedProcesses++;
                remainingBurstTimes[currentProcessIndex] = 0;
            }

            for (int i = 1; i < processCount; i++) {
                if (remainingBurstTimes[i] > 0
                        && processes.get(i).getArrivalTime() <= currentTime
                        && isInQueue[i] == 0) {
                    isInQueue[i] = 1;
                    readyQueue.add(i);
                }
            }

            if (remainingBurstTimes[currentProcessIndex] > 0) {
                readyQueue.add(currentProcessIndex);
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
            process.setWaitingTime(process.getTurnAroundTimeTime() - process.getBurstTime());
        }
    }
}
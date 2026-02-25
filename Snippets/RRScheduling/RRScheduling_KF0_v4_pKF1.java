/**
 * @author Md Asif Joardar
 */

package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The Round-robin scheduling algorithm is a kind of preemptive First come, First Serve CPU
 * Scheduling algorithm. This can be understood here -
 * https://www.scaler.com/topics/round-robin-scheduling-in-os/
 */
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

        int[] isProcessEnqueued = new int[processCount];
        Arrays.fill(isProcessEnqueued, 0);
        isProcessEnqueued[0] = 1;

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
                boolean isProcessRemaining = remainingBurstTimes[processIndex] > 0;
                boolean hasArrived = process.getArrivalTime() <= currentTime;
                boolean isNotEnqueued = isProcessEnqueued[processIndex] == 0;

                if (isProcessRemaining && hasArrived && isNotEnqueued) {
                    isProcessEnqueued[processIndex] = 1;
                    readyQueue.add(processIndex);
                }
            }

            if (remainingBurstTimes[currentProcessIndex] > 0) {
                readyQueue.add(currentProcessIndex);
            }

            if (readyQueue.isEmpty()) {
                for (int processIndex = 1; processIndex < processCount; processIndex++) {
                    if (remainingBurstTimes[processIndex] > 0) {
                        isProcessEnqueued[processIndex] = 1;
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
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
        int completedProcesses = 0;

        int[] isProcessEnqueued = new int[processCount];
        Arrays.fill(isProcessEnqueued, 0);
        isProcessEnqueued[0] = 1;

        int[] remainingBurstTimes = new int[processCount];
        for (int index = 0; index < processCount; index++) {
            remainingBurstTimes[index] = processes.get(index).getBurstTime();
        }

        while (completedProcesses != processCount) {
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
                completedProcesses++;
                remainingBurstTimes[currentProcessIndex] = 0;
            }

            for (int index = 1; index < processCount; index++) {
                ProcessDetails process = processes.get(index);
                if (remainingBurstTimes[index] > 0
                        && process.getArrivalTime() <= currentTime
                        && isProcessEnqueued[index] == 0) {
                    isProcessEnqueued[index] = 1;
                    readyQueue.add(index);
                }
            }

            if (remainingBurstTimes[currentProcessIndex] > 0) {
                readyQueue.add(currentProcessIndex);
            }

            if (readyQueue.isEmpty()) {
                for (int index = 1; index < processCount; index++) {
                    if (remainingBurstTimes[index] > 0) {
                        isProcessEnqueued[index] = 1;
                        readyQueue.add(index);
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
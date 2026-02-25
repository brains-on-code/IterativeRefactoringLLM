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

    private final List<ProcessDetails> processList;
    private final int timeQuantum;

    RRScheduling(final List<ProcessDetails> processList, final int timeQuantum) {
        this.processList = processList;
        this.timeQuantum = timeQuantum;
    }

    public void scheduleProcesses() {
        evaluateTurnAroundTimes();
        evaluateWaitingTimes();
    }

    private void evaluateTurnAroundTimes() {
        int processCount = processList.size();

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
            remainingBurstTimes[i] = processList.get(i).getBurstTime();
        }

        while (completedProcessCount != processCount) {
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
                completedProcessCount++;
                remainingBurstTimes[currentProcessIndex] = 0;
            }

            for (int i = 1; i < processCount; i++) {
                ProcessDetails process = processList.get(i);
                if (remainingBurstTimes[i] > 0
                        && process.getArrivalTime() <= currentTime
                        && isEnqueued[i] == 0) {
                    isEnqueued[i] = 1;
                    readyQueue.add(i);
                }
            }

            if (remainingBurstTimes[currentProcessIndex] > 0) {
                readyQueue.add(currentProcessIndex);
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

    private void evaluateWaitingTimes() {
        for (final ProcessDetails process : processList) {
            process.setWaitingTime(process.getTurnAroundTimeTime() - process.getBurstTime());
        }
    }
}
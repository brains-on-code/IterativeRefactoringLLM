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

        final Queue<Integer> readyQueue = new LinkedList<>();
        readyQueue.add(0);

        int currentTime = 0;
        int completedProcesses = 0;

        final int[] isEnqueued = new int[processCount];
        Arrays.fill(isEnqueued, 0);
        isEnqueued[0] = 1;

        final int[] remainingBurstTime = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTime[i] = processes.get(i).getBurstTime();
        }

        while (completedProcesses != processCount) {
            final int currentIndex = readyQueue.poll();

            final ProcessDetails currentProcess = processes.get(currentIndex);
            final int originalBurstTime = currentProcess.getBurstTime();

            if (remainingBurstTime[currentIndex] == originalBurstTime) {
                currentTime = Math.max(currentTime, currentProcess.getArrivalTime());
            }

            if (remainingBurstTime[currentIndex] > quantumTime) {
                remainingBurstTime[currentIndex] -= quantumTime;
                currentTime += quantumTime;
            } else {
                currentTime += remainingBurstTime[currentIndex];
                remainingBurstTime[currentIndex] = 0;
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

            if (remainingBurstTime[currentIndex] > 0) {
                readyQueue.add(currentIndex);
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
            final int waitingTime = process.getTurnAroundTimeTime() - process.getBurstTime();
            process.setWaitingTime(waitingTime);
        }
    }
}
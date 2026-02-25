package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Round-robin (RR) CPU scheduling algorithm implementation.
 *
 * <p>RR is a preemptive variant of First-Come, First-Served (FCFS) scheduling where each process
 * is assigned a fixed time slice (quantum). Processes are executed in a cyclic order, and if a
 * process is not finished by the end of its quantum, it is placed at the end of the ready queue.
 *
 * <p>Reference: https://www.scaler.com/topics/round-robin-scheduling-in-os/
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

    /**
     * Computes turnaround time for each process using Round-robin scheduling.
     *
     * <p>Turnaround time = completion time - arrival time
     */
    private void evaluateTurnAroundTime() {
        final int processCount = processes.size();
        if (processCount == 0) {
            return;
        }

        final Queue<Integer> readyQueue = new LinkedList<>();
        readyQueue.add(0);

        int currentTime = 0;
        int completedProcesses = 0;

        // Marks whether a process has been added to the ready queue at least once.
        final int[] isEnqueued = new int[processCount];
        Arrays.fill(isEnqueued, 0);
        isEnqueued[0] = 1;

        // Remaining burst time for each process.
        final int[] remainingBurstTime = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            remainingBurstTime[i] = processes.get(i).getBurstTime();
        }

        while (completedProcesses != processCount) {
            final int index = readyQueue.poll();

            // If this is the first time the process is getting CPU, ensure currentTime
            // is at least its arrival time.
            if (remainingBurstTime[index] == processes.get(index).getBurstTime()) {
                currentTime = Math.max(currentTime, processes.get(index).getArrivalTime());
            }

            // Execute the process for a quantum or until completion.
            if (remainingBurstTime[index] > quantumTime) {
                remainingBurstTime[index] -= quantumTime;
                currentTime += quantumTime;
            } else {
                currentTime += remainingBurstTime[index];
                remainingBurstTime[index] = 0;
                processes
                    .get(index)
                    .setTurnAroundTimeTime(currentTime - processes.get(index).getArrivalTime());
                completedProcesses++;
            }

            // Enqueue newly arrived processes that are not yet completed and not yet enqueued.
            for (int i = 1; i < processCount; i++) {
                if (remainingBurstTime[i] > 0
                        && processes.get(i).getArrivalTime() <= currentTime
                        && isEnqueued[i] == 0) {
                    isEnqueued[i] = 1;
                    readyQueue.add(i);
                }
            }

            // If the current process still has remaining burst time, re-enqueue it.
            if (remainingBurstTime[index] > 0) {
                readyQueue.add(index);
            }

            // If the ready queue is empty, find the next process that is not yet completed.
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

    /**
     * Computes waiting time for each process.
     *
     * <p>Waiting time = turnaround time - burst time
     */
    private void evaluateWaitingTime() {
        for (final ProcessDetails process : processes) {
            process.setWaitingTime(
                process.getTurnAroundTimeTime() - process.getBurstTime()
            );
        }
    }
}
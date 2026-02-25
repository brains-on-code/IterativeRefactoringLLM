package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Shortest Job First Algorithm: The algorithm allows the waiting process with the
 * minimal burst time to be executed first. see more here:
 * https://www.guru99.com/shortest-job-first-sjf-scheduling.html
 */
public class SJFScheduling {
    protected ArrayList<ProcessDetails> processes;
    protected ArrayList<String> executionOrder;

    private static void sortProcessesByArrivalTime(List<ProcessDetails> processes) {
        int processCount = processes.size();
        for (int i = 0; i < processCount; i++) {
            for (int j = i + 1; j < processCount - 1; j++) {
                if (processes.get(j).getArrivalTime() > processes.get(j + 1).getArrivalTime()) {
                    final ProcessDetails tempProcess = processes.get(j);
                    processes.set(j, processes.get(j + 1));
                    processes.set(j + 1, tempProcess);
                }
            }
        }
    }

    /**
     * Simple constructor.
     *
     * @param processes a list of processes the user wants to schedule.
     *                  It also sorts the processes based on the time of their arrival.
     */
    SJFScheduling(final ArrayList<ProcessDetails> processes) {
        this.processes = processes;
        this.executionOrder = new ArrayList<>();
        sortProcessesByArrivalTime(this.processes);
    }

    protected void sortByArrivalTime() {
        sortProcessesByArrivalTime(processes);
    }

    /**
     * Computes the order of execution of the processes using SJF scheduling.
     */
    public void scheduleProcesses() {
        ArrayList<ProcessDetails> readyQueue = new ArrayList<>();

        int totalProcessCount = processes.size();
        int currentTime = 0;
        int completedProcessCount = 0;
        int nextArrivalIndex = 0;

        if (totalProcessCount == 0) {
            return;
        }

        while (completedProcessCount < totalProcessCount) {
            // Add all processes that have arrived by currentTime to the ready queue
            while (nextArrivalIndex < totalProcessCount
                    && processes.get(nextArrivalIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(nextArrivalIndex));
                nextArrivalIndex++;
            }

            ProcessDetails shortestJob = findShortestJob(readyQueue);
            if (shortestJob == null) {
                currentTime++;
            } else {
                int burstTime = shortestJob.getBurstTime();
                currentTime += burstTime;
                executionOrder.add(shortestJob.getProcessId());
                readyQueue.remove(shortestJob);
                completedProcessCount++;
            }
        }
    }

    /**
     * Evaluates the shortest job of all the ready processes (based on a process burst time).
     *
     * @param readyQueue a list of ready processes
     * @return the process with the shortest burst time OR null if there are no ready processes
     */
    private ProcessDetails findShortestJob(List<ProcessDetails> readyQueue) {
        if (readyQueue.isEmpty()) {
            return null;
        }

        int readyQueueSize = readyQueue.size();
        int shortestBurstTime = readyQueue.get(0).getBurstTime();
        int shortestJobIndex = 0;

        for (int i = 1; i < readyQueueSize; i++) {
            int currentBurstTime = readyQueue.get(i).getBurstTime();
            if (shortestBurstTime > currentBurstTime) {
                shortestBurstTime = currentBurstTime;
                shortestJobIndex = i;
            }
        }

        return readyQueue.get(shortestJobIndex);
    }
}
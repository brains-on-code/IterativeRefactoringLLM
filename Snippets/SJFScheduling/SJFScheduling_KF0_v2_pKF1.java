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
        for (int currentIndex = 0; currentIndex < processCount; currentIndex++) {
            for (int nextIndex = currentIndex + 1; nextIndex < processCount - 1; nextIndex++) {
                if (processes.get(nextIndex).getArrivalTime() > processes.get(nextIndex + 1).getArrivalTime()) {
                    final ProcessDetails tempProcess = processes.get(nextIndex);
                    processes.set(nextIndex, processes.get(nextIndex + 1));
                    processes.set(nextIndex + 1, tempProcess);
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
        int nextProcessIndex = 0;

        if (totalProcessCount == 0) {
            return;
        }

        while (completedProcessCount < totalProcessCount) {
            // Add all processes that have arrived by currentTime to the ready queue
            while (nextProcessIndex < totalProcessCount
                    && processes.get(nextProcessIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(nextProcessIndex));
                nextProcessIndex++;
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
     * @param readyProcesses a list of ready processes
     * @return the process with the shortest burst time OR null if there are no ready processes
     */
    private ProcessDetails findShortestJob(List<ProcessDetails> readyProcesses) {
        if (readyProcesses.isEmpty()) {
            return null;
        }

        int readyProcessCount = readyProcesses.size();
        int shortestBurstTime = readyProcesses.get(0).getBurstTime();
        int shortestJobIndex = 0;

        for (int currentIndex = 1; currentIndex < readyProcessCount; currentIndex++) {
            int currentBurstTime = readyProcesses.get(currentIndex).getBurstTime();
            if (shortestBurstTime > currentBurstTime) {
                shortestBurstTime = currentBurstTime;
                shortestJobIndex = currentIndex;
            }
        }

        return readyProcesses.get(shortestJobIndex);
    }
}
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

    private static void sortProcessesByArrivalTime(List<ProcessDetails> processList) {
        int numberOfProcesses = processList.size();
        for (int currentIndex = 0; currentIndex < numberOfProcesses; currentIndex++) {
            for (int comparisonIndex = currentIndex + 1; comparisonIndex < numberOfProcesses - 1; comparisonIndex++) {
                ProcessDetails currentProcess = processList.get(comparisonIndex);
                ProcessDetails nextProcess = processList.get(comparisonIndex + 1);

                if (currentProcess.getArrivalTime() > nextProcess.getArrivalTime()) {
                    processList.set(comparisonIndex, nextProcess);
                    processList.set(comparisonIndex + 1, currentProcess);
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

        int totalNumberOfProcesses = processes.size();
        int currentTime = 0;
        int completedProcessCount = 0;
        int nextArrivalProcessIndex = 0;

        if (totalNumberOfProcesses == 0) {
            return;
        }

        while (completedProcessCount < totalNumberOfProcesses) {
            // Add all processes that have arrived by currentTime to the ready queue
            while (nextArrivalProcessIndex < totalNumberOfProcesses
                    && processes.get(nextArrivalProcessIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(nextArrivalProcessIndex));
                nextArrivalProcessIndex++;
            }

            ProcessDetails shortestJobProcess = findShortestJob(readyQueue);
            if (shortestJobProcess == null) {
                currentTime++;
            } else {
                int burstTime = shortestJobProcess.getBurstTime();
                currentTime += burstTime;
                executionOrder.add(shortestJobProcess.getProcessId());
                readyQueue.remove(shortestJobProcess);
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

        for (int index = 1; index < readyQueueSize; index++) {
            int currentBurstTime = readyQueue.get(index).getBurstTime();
            if (shortestBurstTime > currentBurstTime) {
                shortestBurstTime = currentBurstTime;
                shortestJobIndex = index;
            }
        }

        return readyQueue.get(shortestJobIndex);
    }
}
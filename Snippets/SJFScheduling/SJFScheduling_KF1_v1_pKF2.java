package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Job First (non-preemptive) scheduling implementation.
 *
 * Given a list of processes with arrival and burst times, this class
 * computes the execution order according to the SJF policy.
 */
public class Class1 {

    /** List of processes to be scheduled. */
    protected ArrayList<ProcessDetails> processes;

    /** Execution order of process IDs after scheduling. */
    protected ArrayList<String> executionOrder;

    /**
     * Sorts the given list of processes in ascending order of arrival time
     * using a simple bubble sort.
     *
     * @param processes list of processes to sort
     */
    private static void sortByArrivalTime(List<ProcessDetails> processes) {
        for (int i = 0; i < processes.size(); i++) {
            for (int j = i + 1; j < processes.size() - 1; j++) {
                if (processes.get(j).getArrivalTime() > processes.get(j + 1).getArrivalTime()) {
                    final ProcessDetails temp = processes.get(j);
                    processes.set(j, processes.get(j + 1));
                    processes.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Creates a scheduler with the given list of processes.
     *
     * @param processes list of processes to schedule
     */
    Class1(final ArrayList<ProcessDetails> processes) {
        this.processes = processes;
        this.executionOrder = new ArrayList<>();
        sortByArrivalTime(this.processes);
    }

    /**
     * Re-sorts the internal process list by arrival time.
     */
    protected void resortByArrivalTime() {
        sortByArrivalTime(processes);
    }

    /**
     * Runs the Shortest Job First (non-preemptive) scheduling algorithm and
     * populates {@link #executionOrder} with the order of process IDs.
     */
    public void schedule() {
        ArrayList<ProcessDetails> readyQueue = new ArrayList<>();

        int totalProcesses = processes.size();
        int currentTime = 0;
        int completedProcesses = 0;
        int nextProcessIndex = 0;

        if (totalProcesses == 0) {
            return;
        }

        while (completedProcesses < totalProcesses) {
            // Add all processes that have arrived by currentTime to the ready queue
            while (nextProcessIndex < totalProcesses
                    && processes.get(nextProcessIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(nextProcessIndex));
                nextProcessIndex++;
            }

            ProcessDetails shortestJob = selectShortestJob(readyQueue);
            if (shortestJob == null) {
                // No process is ready; advance time
                currentTime++;
            } else {
                int burstTime = shortestJob.getBurstTime();
                currentTime += burstTime;

                executionOrder.add(shortestJob.getProcessId());
                readyQueue.remove(shortestJob);
                completedProcesses++;
            }
        }
    }

    /**
     * Selects the process with the smallest burst time from the given list.
     *
     * @param readyQueue list of ready processes
     * @return process with the minimum burst time, or {@code null} if the list is empty
     */
    private ProcessDetails selectShortestJob(List<ProcessDetails> readyQueue) {
        if (readyQueue.isEmpty()) {
            return null;
        }

        int size = readyQueue.size();
        int minBurstTime = readyQueue.get(0).getBurstTime();
        int minIndex = 0;

        for (int i = 1; i < size; i++) {
            int burstTime = readyQueue.get(i).getBurstTime();
            if (minBurstTime > burstTime) {
                minBurstTime = burstTime;
                minIndex = i;
            }
        }

        return readyQueue.get(minIndex);
    }
}
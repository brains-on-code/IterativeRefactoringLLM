package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Job First (non-preemptive) scheduling implementation.
 *
 * <p>Given a list of processes with arrival and burst times, this class
 * computes the execution order that always selects the ready process
 * with the smallest burst time.</p>
 */
public class SJFScheduling {

    /** Input processes (copied on construction and sorted by arrival time). */
    protected final List<ProcessDetails> processes;

    /** Resulting schedule as an ordered list of process IDs. */
    protected final List<String> schedule;

    public SJFScheduling(final List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.schedule = new ArrayList<>();
        sortByArrivalTime();
    }

    /**
     * Sorts the given list of processes in-place by their arrival time
     * using a simple bubble sort.
     */
    private static void sortProcessesByArrivalTime(List<ProcessDetails> processes) {
        int n = processes.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                if (processes.get(j).getArrivalTime() > processes.get(j + 1).getArrivalTime()) {
                    ProcessDetails temp = processes.get(j);
                    processes.set(j, processes.get(j + 1));
                    processes.set(j + 1, temp);
                }
            }
        }
    }

    /** Sorts this instance's process list by arrival time. */
    protected void sortByArrivalTime() {
        sortProcessesByArrivalTime(processes);
    }

    /**
     * Builds the SJF schedule.
     *
     * <p>Algorithm outline:
     * <ol>
     *   <li>Maintain a current time and a ready queue.</li>
     *   <li>At each step, add all processes that have arrived to the ready queue.</li>
     *   <li>Select the ready process with the smallest burst time and run it to completion.</li>
     *   <li>If no process is ready, advance time by one unit.</li>
     * </ol>
     * The resulting execution order is stored in {@link #schedule}.
     */
    public void scheduleProcesses() {
        if (processes.isEmpty()) {
            return;
        }

        List<ProcessDetails> readyQueue = new ArrayList<>();
        int totalProcesses = processes.size();
        int currentTime = 0;
        int completedProcesses = 0;
        int nextProcessIndex = 0;

        while (completedProcesses < totalProcesses) {
            // Move all processes that have arrived by currentTime into the ready queue.
            while (nextProcessIndex < totalProcesses
                    && processes.get(nextProcessIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(nextProcessIndex));
                nextProcessIndex++;
            }

            ProcessDetails shortestJob = findShortestJob(readyQueue);

            // If no process is ready, advance time.
            if (shortestJob == null) {
                currentTime++;
                continue;
            }

            int burstTime = shortestJob.getBurstTime();
            currentTime += burstTime;

            schedule.add(shortestJob.getProcessId());
            readyQueue.remove(shortestJob);
            completedProcesses++;
        }
    }

    /**
     * Returns the process with the smallest burst time from the given list,
     * or {@code null} if the list is empty.
     */
    private ProcessDetails findShortestJob(List<ProcessDetails> readyProcesses) {
        if (readyProcesses.isEmpty()) {
            return null;
        }

        ProcessDetails shortestJob = readyProcesses.get(0);
        int minBurstTime = shortestJob.getBurstTime();

        for (int i = 1; i < readyProcesses.size(); i++) {
            ProcessDetails candidate = readyProcesses.get(i);
            int candidateBurstTime = candidate.getBurstTime();
            if (candidateBurstTime < minBurstTime) {
                minBurstTime = candidateBurstTime;
                shortestJob = candidate;
            }
        }

        return shortestJob;
    }
}
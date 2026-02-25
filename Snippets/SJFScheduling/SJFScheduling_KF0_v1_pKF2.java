package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Job First (SJF) Scheduling Algorithm.
 *
 * <p>The algorithm always selects, from the set of processes that have arrived and are ready,
 * the one with the smallest burst time to execute next.
 *
 * <p>More details: https://www.guru99.com/shortest-job-first-sjf-scheduling.html
 */
public class SJFScheduling {

    /** List of processes to be scheduled. */
    protected ArrayList<ProcessDetails> processes;

    /** Execution order of process IDs after scheduling. */
    protected ArrayList<String> schedule;

    /**
     * Sorts the given list of processes in non-decreasing order of arrival time.
     *
     * @param processes list of processes to sort
     */
    private static void sortProcessesByArrivalTime(final List<ProcessDetails> processes) {
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
     * Creates an SJF scheduler for the given processes.
     *
     * <p>The provided list is sorted by arrival time.
     *
     * @param processes list of processes to schedule
     */
    SJFScheduling(final ArrayList<ProcessDetails> processes) {
        this.processes = processes;
        this.schedule = new ArrayList<>();
        sortProcessesByArrivalTime(this.processes);
    }

    /** Sorts the internal process list by arrival time. */
    protected void sortByArrivalTime() {
        sortProcessesByArrivalTime(processes);
    }

    /**
     * Computes the SJF schedule.
     *
     * <p>Populates {@link #schedule} with the process IDs in the order they are executed.
     */
    public void scheduleProcesses() {
        final ArrayList<ProcessDetails> readyQueue = new ArrayList<>();

        final int totalProcesses = processes.size();
        if (totalProcesses == 0) {
            return;
        }

        int currentTime = 0;
        int executedCount = 0;
        int nextProcessIndex = 0;

        while (executedCount < totalProcesses) {
            // Add all processes that have arrived by currentTime to the ready queue.
            while (nextProcessIndex < totalProcesses
                    && processes.get(nextProcessIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(nextProcessIndex));
                nextProcessIndex++;
            }

            final ProcessDetails running = findShortestJob(readyQueue);

            // If no process is ready, advance time.
            if (running == null) {
                currentTime++;
                continue;
            }

            // Run the selected process for its entire burst time (non-preemptive SJF).
            final int burstTime = running.getBurstTime();
            currentTime += burstTime;

            schedule.add(running.getProcessId());
            readyQueue.remove(running);
            executedCount++;
        }
    }

    /**
     * Returns the process with the smallest burst time from the ready list.
     *
     * @param readyProcesses list of processes that have arrived and are ready to run
     * @return process with the shortest burst time, or {@code null} if the list is empty
     */
    private ProcessDetails findShortestJob(final List<ProcessDetails> readyProcesses) {
        if (readyProcesses.isEmpty()) {
            return null;
        }

        int positionOfShortestJob = 0;
        int minBurstTime = readyProcesses.get(0).getBurstTime();

        for (int i = 1; i < readyProcesses.size(); i++) {
            final int currentBurstTime = readyProcesses.get(i).getBurstTime();
            if (currentBurstTime < minBurstTime) {
                minBurstTime = currentBurstTime;
                positionOfShortestJob = i;
            }
        }

        return readyProcesses.get(positionOfShortestJob);
    }
}
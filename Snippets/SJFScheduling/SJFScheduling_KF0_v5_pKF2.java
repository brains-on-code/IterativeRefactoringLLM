package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Shortest Job First (SJF) Scheduling Algorithm (non-preemptive).
 *
 * <p>From all processes that have arrived, always selects the one with the
 * smallest burst time to run next.
 *
 * <p>More details: https://www.guru99.com/shortest-job-first-sjf-scheduling.html
 */
public class SJFScheduling {

    /** All processes to be scheduled, sorted by arrival time. */
    protected final List<ProcessDetails> processes;

    /** Process IDs in the order they are executed. */
    protected final List<String> schedule;

    /**
     * Creates an SJF scheduler for the given processes.
     *
     * <p>The internal list is sorted by non-decreasing arrival time.
     *
     * @param processes list of processes to schedule
     */
    SJFScheduling(final ArrayList<ProcessDetails> processes) {
        this.processes = processes;
        this.schedule = new ArrayList<>();
        sortByArrivalTime();
    }

    /** Sorts processes by non-decreasing arrival time. */
    protected void sortByArrivalTime() {
        processes.sort(Comparator.comparingInt(ProcessDetails::getArrivalTime));
    }

    /**
     * Computes the SJF schedule and fills {@link #schedule} with process IDs.
     */
    public void scheduleProcesses() {
        final List<ProcessDetails> readyQueue = new ArrayList<>();

        final int totalProcesses = processes.size();
        if (totalProcesses == 0) {
            return;
        }

        int currentTime = 0;
        int executedCount = 0;
        int nextProcessIndex = 0;

        while (executedCount < totalProcesses) {
            nextProcessIndex =
                    addArrivedProcessesToReadyQueue(readyQueue, totalProcesses, currentTime, nextProcessIndex);

            final ProcessDetails running = findShortestJob(readyQueue);

            if (running == null) {
                // No process is ready yet; advance time until at least one arrives.
                currentTime++;
                continue;
            }

            // Run the selected process to completion (non-preemptive).
            currentTime += running.getBurstTime();
            schedule.add(running.getProcessId());
            readyQueue.remove(running);
            executedCount++;
        }
    }

    /**
     * Adds all processes that have arrived by the given time to the ready queue.
     *
     * <p>Processes are taken from {@link #processes} starting at {@code nextProcessIndex},
     * assuming that list is sorted by arrival time.
     *
     * @param readyQueue      list of processes ready to run
     * @param totalProcesses  total number of processes
     * @param currentTime     current time in the scheduling simulation
     * @param nextProcessIndex index of the next process in {@link #processes} to consider
     * @return updated index of the next process to consider
     */
    private int addArrivedProcessesToReadyQueue(
            final List<ProcessDetails> readyQueue,
            final int totalProcesses,
            final int currentTime,
            int nextProcessIndex
    ) {
        while (nextProcessIndex < totalProcesses
                && processes.get(nextProcessIndex).getArrivalTime() <= currentTime) {
            readyQueue.add(processes.get(nextProcessIndex));
            nextProcessIndex++;
        }
        return nextProcessIndex;
    }

    /**
     * Returns the process with the smallest burst time from the ready list.
     *
     * @param readyProcesses processes that have arrived and are ready to run
     * @return process with the shortest burst time, or {@code null} if none
     */
    private ProcessDetails findShortestJob(final List<ProcessDetails> readyProcesses) {
        return readyProcesses.stream()
                .min(Comparator.comparingInt(ProcessDetails::getBurstTime))
                .orElse(null);
    }
}
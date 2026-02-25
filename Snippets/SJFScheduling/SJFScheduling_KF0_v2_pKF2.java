package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Comparator;
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

    /** Processes to be scheduled. */
    protected final List<ProcessDetails> processes;

    /** Execution order of process IDs after scheduling. */
    protected final List<String> schedule;

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
        sortByArrivalTime();
    }

    /** Sorts the internal process list by non-decreasing arrival time. */
    protected void sortByArrivalTime() {
        processes.sort(Comparator.comparingInt(ProcessDetails::getArrivalTime));
    }

    /**
     * Computes the SJF schedule.
     *
     * <p>Populates {@link #schedule} with the process IDs in the order they are executed.
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
            // Move all processes that have arrived by currentTime into the ready queue.
            while (nextProcessIndex < totalProcesses
                    && processes.get(nextProcessIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(nextProcessIndex));
                nextProcessIndex++;
            }

            final ProcessDetails running = findShortestJob(readyQueue);

            // If no process is ready, advance time until one arrives.
            if (running == null) {
                currentTime++;
                continue;
            }

            // Run the selected process for its entire burst time (non-preemptive SJF).
            currentTime += running.getBurstTime();

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
        return readyProcesses.stream()
                .min(Comparator.comparingInt(ProcessDetails::getBurstTime))
                .orElse(null);
    }
}
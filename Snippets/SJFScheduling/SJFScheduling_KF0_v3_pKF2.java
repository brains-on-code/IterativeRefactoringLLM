package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Shortest Job First (SJF) Scheduling Algorithm (non-preemptive).
 *
 * <p>Selects, from all arrived processes, the one with the smallest burst time to run next.
 *
 * <p>More details: https://www.guru99.com/shortest-job-first-sjf-scheduling.html
 */
public class SJFScheduling {

    /** All processes to be scheduled. */
    protected final List<ProcessDetails> processes;

    /** Process IDs in the order they are executed. */
    protected final List<String> schedule;

    /**
     * Creates an SJF scheduler for the given processes.
     *
     * <p>The internal list is sorted by arrival time.
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
            // Add all processes that have arrived by currentTime to the ready queue.
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

            // Run the selected process for its entire burst time.
            currentTime += running.getBurstTime();

            schedule.add(running.getProcessId());
            readyQueue.remove(running);
            executedCount++;
        }
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
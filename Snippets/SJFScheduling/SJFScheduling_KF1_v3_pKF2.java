package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Shortest Job First (non-preemptive) scheduling implementation.
 *
 * Given a list of processes with arrival and burst times, this class
 * computes the execution order according to the SJF policy.
 */
public class ShortestJobFirstScheduler {

    /** Processes to be scheduled (kept sorted by arrival time). */
    protected final List<ProcessDetails> processes;

    /** Execution order of process IDs after scheduling. */
    protected final List<String> executionOrder;

    /**
     * Creates a scheduler with the given list of processes.
     *
     * @param processes list of processes to schedule
     */
    ShortestJobFirstScheduler(final List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.executionOrder = new ArrayList<>();
        sortByArrivalTime(this.processes);
    }

    /**
     * Sorts the given list of processes in ascending order of arrival time.
     *
     * @param processes list of processes to sort
     */
    private static void sortByArrivalTime(List<ProcessDetails> processes) {
        processes.sort(Comparator.comparingInt(ProcessDetails::getArrivalTime));
    }

    /** Re-sorts the internal process list by arrival time. */
    protected void resortByArrivalTime() {
        sortByArrivalTime(processes);
    }

    /**
     * Runs the Shortest Job First (non-preemptive) scheduling algorithm and
     * populates {@link #executionOrder} with the order of process IDs.
     */
    public void schedule() {
        if (processes.isEmpty()) {
            return;
        }

        List<ProcessDetails> readyQueue = new ArrayList<>();
        int totalProcesses = processes.size();
        int currentTime = 0;
        int completedProcesses = 0;
        int nextProcessIndex = 0;

        while (completedProcesses < totalProcesses) {
            // Add all processes that have arrived by currentTime to the ready queue
            while (nextProcessIndex < totalProcesses
                    && processes.get(nextProcessIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(nextProcessIndex));
                nextProcessIndex++;
            }

            ProcessDetails shortestJob = selectShortestJob(readyQueue);
            if (shortestJob == null) {
                // No process is ready; advance time until the next arrival
                currentTime++;
                continue;
            }

            currentTime += shortestJob.getBurstTime();
            executionOrder.add(shortestJob.getProcessId());
            readyQueue.remove(shortestJob);
            completedProcesses++;
        }
    }

    /**
     * Returns the process with the smallest burst time from the ready queue.
     *
     * @param readyQueue list of ready processes
     * @return process with the minimum burst time, or {@code null} if the list is empty
     */
    private ProcessDetails selectShortestJob(List<ProcessDetails> readyQueue) {
        return readyQueue.stream()
                .min(Comparator.comparingInt(ProcessDetails::getBurstTime))
                .orElse(null);
    }
}
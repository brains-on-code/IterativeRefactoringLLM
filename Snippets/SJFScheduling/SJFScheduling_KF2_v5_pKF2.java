package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Shortest Job First (non-preemptive) scheduling.
 *
 * <p>Given processes with arrival and burst times, this class computes an
 * execution order that always runs, to completion, the ready process with
 * the smallest burst time.</p>
 */
public class SJFScheduling {

    /** Processes sorted by arrival time. */
    protected final List<ProcessDetails> processes;

    /** Ordered list of process IDs representing the final schedule. */
    protected final List<String> schedule;

    public SJFScheduling(final List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.schedule = new ArrayList<>();
        sortByArrivalTime();
    }

    private static void sortProcessesByArrivalTime(List<ProcessDetails> processes) {
        processes.sort(Comparator.comparingInt(ProcessDetails::getArrivalTime));
    }

    protected void sortByArrivalTime() {
        sortProcessesByArrivalTime(processes);
    }

    /**
     * Builds the SJF schedule and stores the process IDs in {@link #schedule}.
     *
     * <p>Algorithm:
     * <ul>
     *   <li>Track current time and a ready queue.</li>
     *   <li>At each step, add all processes that have arrived to the ready queue.</li>
     *   <li>Select the ready process with the smallest burst time and run it to completion.</li>
     *   <li>If no process is ready, advance time by one unit.</li>
     * </ul>
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
            nextProcessIndex = addArrivedProcessesToReadyQueue(
                readyQueue,
                totalProcesses,
                currentTime,
                nextProcessIndex
            );

            ProcessDetails shortestJob = findShortestJob(readyQueue);

            if (shortestJob == null) {
                currentTime++;
                continue;
            }

            currentTime += shortestJob.getBurstTime();
            schedule.add(shortestJob.getProcessId());
            readyQueue.remove(shortestJob);
            completedProcesses++;
        }
    }

    /**
     * Adds all processes that have arrived by {@code currentTime} to the ready queue.
     *
     * @param readyQueue       list of processes that are ready to be scheduled
     * @param totalProcesses   total number of processes
     * @param currentTime      current time in the scheduling simulation
     * @param nextProcessIndex index of the next process to check for arrival
     * @return updated index of the next process to check
     */
    private int addArrivedProcessesToReadyQueue(
            List<ProcessDetails> readyQueue,
            int totalProcesses,
            int currentTime,
            int nextProcessIndex) {

        while (nextProcessIndex < totalProcesses
                && processes.get(nextProcessIndex).getArrivalTime() <= currentTime) {
            readyQueue.add(processes.get(nextProcessIndex));
            nextProcessIndex++;
        }
        return nextProcessIndex;
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
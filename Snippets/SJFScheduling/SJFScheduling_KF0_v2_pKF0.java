package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of Shortest Job First Algorithm: The algorithm allows the waiting process with the
 * minimal burst time to be executed first. See more here:
 * https://www.guru99.com/shortest-job-first-sjf-scheduling.html
 */
public class SJFScheduling {

    protected final List<ProcessDetails> processes;
    protected final List<String> schedule;

    private static final Comparator<ProcessDetails> ARRIVAL_TIME_COMPARATOR =
            Comparator.comparingInt(ProcessDetails::getArrivalTime);

    private static final Comparator<ProcessDetails> BURST_TIME_COMPARATOR =
            Comparator.comparingInt(ProcessDetails::getBurstTime);

    private static void sortProcessesByArrivalTime(List<ProcessDetails> processes) {
        processes.sort(ARRIVAL_TIME_COMPARATOR);
    }

    /**
     * Simple constructor.
     *
     * @param processes a list of processes the user wants to schedule.
     *                  It also sorts the processes based on the time of their arrival.
     */
    SJFScheduling(final List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.schedule = new ArrayList<>();
        sortProcessesByArrivalTime(this.processes);
    }

    protected void sortByArrivalTime() {
        sortProcessesByArrivalTime(processes);
    }

    /**
     * Schedules the processes using the SJF algorithm and fills the {@code schedule} list
     * with the execution order of process IDs.
     */
    public void scheduleProcesses() {
        if (processes.isEmpty()) {
            return;
        }

        final List<ProcessDetails> readyQueue = new ArrayList<>();
        final int totalProcesses = processes.size();

        int currentTime = 0;
        int executedCount = 0;
        int nextProcessIndex = 0;

        while (executedCount < totalProcesses) {
            addArrivedProcessesToReadyQueue(readyQueue, totalProcesses, currentTime);

            ProcessDetails shortestJob = findShortestJob(readyQueue);

            if (shortestJob == null) {
                currentTime++;
                continue;
            }

            currentTime += shortestJob.getBurstTime();
            schedule.add(shortestJob.getProcessId());
            readyQueue.remove(shortestJob);
            executedCount++;
        }
    }

    private void addArrivedProcessesToReadyQueue(List<ProcessDetails> readyQueue,
                                                 int totalProcesses,
                                                 int currentTime) {
        int index = readyQueue.size();
        while (index < totalProcesses && processes.get(index).getArrivalTime() <= currentTime) {
            readyQueue.add(processes.get(index));
            index++;
        }
    }

    /**
     * Evaluates the shortest job among all ready processes (based on burst time).
     *
     * @param readyProcesses a list of ready processes
     * @return the process with the shortest burst time, or {@code null} if there are no ready processes
     */
    private ProcessDetails findShortestJob(List<ProcessDetails> readyProcesses) {
        return readyProcesses.stream()
                .min(BURST_TIME_COMPARATOR)
                .orElse(null);
    }
}
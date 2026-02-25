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

    private static final Comparator<ProcessDetails> ARRIVAL_TIME_COMPARATOR =
            Comparator.comparingInt(ProcessDetails::getArrivalTime);

    private static final Comparator<ProcessDetails> BURST_TIME_COMPARATOR =
            Comparator.comparingInt(ProcessDetails::getBurstTime);

    protected final List<ProcessDetails> processes;
    protected final List<String> schedule;

    /**
     * Simple constructor.
     *
     * @param processes a list of processes the user wants to schedule.
     *                  It also sorts the processes based on the time of their arrival.
     */
    SJFScheduling(final List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.schedule = new ArrayList<>();
        sortByArrivalTime();
    }

    protected void sortByArrivalTime() {
        processes.sort(ARRIVAL_TIME_COMPARATOR);
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
            nextProcessIndex =
                    enqueueArrivedProcesses(readyQueue, currentTime, nextProcessIndex);

            ProcessDetails shortestJob = findShortestJob(readyQueue);

            if (shortestJob == null) {
                currentTime++;
                continue;
            }

            executeProcess(readyQueue, shortestJob);
            currentTime += shortestJob.getBurstTime();
            executedCount++;
        }
    }

    private void executeProcess(List<ProcessDetails> readyQueue, ProcessDetails process) {
        schedule.add(process.getProcessId());
        readyQueue.remove(process);
    }

    private int enqueueArrivedProcesses(
            List<ProcessDetails> readyQueue,
            int currentTime,
            int nextProcessIndex
    ) {
        while (nextProcessIndex < processes.size()
                && processes.get(nextProcessIndex).getArrivalTime() <= currentTime) {
            readyQueue.add(processes.get(nextProcessIndex));
            nextProcessIndex++;
        }
        return nextProcessIndex;
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
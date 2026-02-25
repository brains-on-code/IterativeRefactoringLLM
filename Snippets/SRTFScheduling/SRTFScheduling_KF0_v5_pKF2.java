package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Remaining Time First (SRTF) Scheduling Algorithm.
 *
 * <p>At each time unit, the process with the smallest remaining burst time among
 * the arrived processes is selected to execute (preemptive).
 *
 * <p>Example:
 * <pre>
 * Process | Burst Time | Arrival Time
 * P1      | 6 ms       | 0 ms
 * P2      | 2 ms       | 1 ms
 *
 * Timeline:
 * t=0..1:  P1 runs
 * t=1..3:  P2 runs (shorter remaining time)
 * t=3..7:  P1 runs again until completion
 * </pre>
 *
 * <p>More information: https://en.wikipedia.org/wiki/Shortest_remaining_time
 */
public class SRTFScheduling {

    /** Processes to be scheduled. */
    protected final List<ProcessDetails> processes;

    /**
     * Execution timeline.
     * At index t, this list stores the ID of the process running at time t,
     * or {@code null} if the CPU is idle.
     */
    protected final List<String> ready;

    /**
     * Creates a new SRTF scheduler with the given processes.
     *
     * @param processes list of processes to schedule
     */
    public SRTFScheduling(List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.ready = new ArrayList<>();
    }

    /**
     * Computes the SRTF schedule and fills {@link #ready} with the process ID
     * running at each time unit.
     */
    public void evaluateScheduling() {
        int processCount = processes.size();
        int[] remainingTime = new int[processCount];

        int totalTime = 0;
        for (int i = 0; i < processCount; i++) {
            int burstTime = processes.get(i).getBurstTime();
            remainingTime[i] = burstTime;
            totalTime += burstTime;
        }

        int firstArrivalTime = processes.get(0).getArrivalTime();
        if (firstArrivalTime > 0) {
            totalTime += firstArrivalTime;
        }

        fillInitialIdleTime(firstArrivalTime);

        int currentProcessIndex = 0;

        for (int currentTime = firstArrivalTime; currentTime < totalTime; currentTime++) {
            currentProcessIndex =
                selectProcessWithShortestRemainingTime(currentTime, currentProcessIndex, remainingTime);

            ready.add(processes.get(currentProcessIndex).getProcessId());
            remainingTime[currentProcessIndex]--;
        }
    }

    /**
     * Fills the {@link #ready} list with idle CPU time before the first process arrives.
     *
     * @param firstArrivalTime time at which the first process arrives
     */
    private void fillInitialIdleTime(int firstArrivalTime) {
        for (int t = 0; t < firstArrivalTime; t++) {
            ready.add(null);
        }
    }

    /**
     * Selects the index of the process with the shortest remaining time among
     * all processes that have arrived by {@code currentTime}.
     *
     * @param currentTime         current time unit
     * @param currentProcessIndex index of the process currently running
     * @param remainingTime       remaining burst time for each process
     * @return index of the selected process
     */
    private int selectProcessWithShortestRemainingTime(
        int currentTime,
        int currentProcessIndex,
        int[] remainingTime
    ) {
        int processCount = processes.size();

        for (int i = 0; i < processCount; i++) {
            boolean hasArrived = processes.get(i).getArrivalTime() <= currentTime;
            boolean hasShorterRemaining =
                remainingTime[i] < remainingTime[currentProcessIndex] && remainingTime[i] > 0;
            boolean currentFinished = remainingTime[currentProcessIndex] == 0;

            if (hasArrived && (hasShorterRemaining || currentFinished)) {
                currentProcessIndex = i;
            }
        }

        return currentProcessIndex;
    }
}
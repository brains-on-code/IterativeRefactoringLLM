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

    /** List of processes to be scheduled. */
    protected List<ProcessDetails> processes;

    /**
     * Timeline of execution: at index t, the process ID that runs at time t,
     * or {@code null} if the CPU is idle.
     */
    protected List<String> ready;

    /**
     * Creates a new SRTF scheduler with the given processes.
     *
     * @param processes list of processes to schedule
     */
    public SRTFScheduling(ArrayList<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.ready = new ArrayList<>();
    }

    /**
     * Evaluates the SRTF schedule and fills the {@link #ready} list with the
     * process ID running at each time unit.
     */
    public void evaluateScheduling() {
        int n = processes.size();
        int[] remainingTime = new int[n];

        int totalTime = 0;
        for (int i = 0; i < n; i++) {
            int burst = processes.get(i).getBurstTime();
            remainingTime[i] = burst;
            totalTime += burst;
        }

        int firstArrival = processes.get(0).getArrivalTime();
        if (firstArrival > 0) {
            totalTime += firstArrival;
        }

        // CPU idle before the first process arrives
        for (int t = 0; t < firstArrival; t++) {
            ready.add(null);
        }

        int currentProcessIndex = 0;

        // Simulate each time unit
        for (int currentTime = firstArrival; currentTime < totalTime; currentTime++) {
            // Select process with the shortest remaining time among arrived processes
            for (int j = 0; j < n; j++) {
                boolean hasArrived = processes.get(j).getArrivalTime() <= currentTime;
                boolean hasShorterRemaining =
                        remainingTime[j] < remainingTime[currentProcessIndex] && remainingTime[j] > 0;
                boolean currentFinished = remainingTime[currentProcessIndex] == 0;

                if (hasArrived && (hasShorterRemaining || currentFinished)) {
                    currentProcessIndex = j;
                }
            }

            ready.add(processes.get(currentProcessIndex).getProcessId());
            remainingTime[currentProcessIndex]--;
        }
    }
}
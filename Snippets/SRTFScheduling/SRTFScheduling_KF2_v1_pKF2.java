package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SRTFScheduling {

    /** List of processes to be scheduled. */
    protected List<ProcessDetails> processes;

    /**
     * Timeline of execution: at each time unit, this list stores the ID of the
     * process that was running, or {@code null} if the CPU was idle.
     */
    protected List<String> ready;

    public SRTFScheduling(ArrayList<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.ready = new ArrayList<>();
    }

    /**
     * Evaluate the SRTF (Shortest Remaining Time First) schedule.
     *
     * Populates {@link #ready} with the process ID running at each time unit.
     */
    public void evaluateScheduling() {
        int n = processes.size();
        if (n == 0) {
            return;
        }

        int[] remainingTime = new int[n];
        int totalBurstTime = 0;

        // Initialize remaining times and compute total burst time
        for (int i = 0; i < n; i++) {
            int burstTime = processes.get(i).getBurstTime();
            remainingTime[i] = burstTime;
            totalBurstTime += burstTime;
        }

        int firstArrival = processes.get(0).getArrivalTime();
        int totalTime = totalBurstTime + Math.max(firstArrival, 0);

        // Fill initial idle time if the first process does not arrive at time 0
        for (int i = 0; i < firstArrival; i++) {
            ready.add(null);
        }

        int currentProcessIndex = 0;

        // Simulate each time unit
        for (int currentTime = firstArrival; currentTime < totalTime; currentTime++) {
            // Select process with the shortest remaining time that has arrived
            for (int j = 0; j < n; j++) {
                boolean hasArrived = processes.get(j).getArrivalTime() <= currentTime;
                boolean hasRemainingTime = remainingTime[j] > 0;
                boolean isShorter =
                        remainingTime[j] < remainingTime[currentProcessIndex] || remainingTime[currentProcessIndex] == 0;

                if (hasArrived && hasRemainingTime && isShorter) {
                    currentProcessIndex = j;
                }
            }

            // Run the selected process for one time unit
            ready.add(processes.get(currentProcessIndex).getProcessId());
            remainingTime[currentProcessIndex]--;
        }
    }
}
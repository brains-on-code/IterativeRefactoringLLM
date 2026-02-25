package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SRTFScheduling {
    protected List<ProcessDetails> processes;
    protected List<String> executionTimeline;

    public SRTFScheduling(ArrayList<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.executionTimeline = new ArrayList<>();
    }

    public void evaluateScheduling() {
        int totalExecutionTime = 0;
        int currentProcessIndex = 0;
        int processCount = processes.size();
        int[] remainingBurstTimes = new int[processCount];

        for (int index = 0; index < processCount; index++) {
            ProcessDetails process = processes.get(index);
            remainingBurstTimes[index] = process.getBurstTime();
            totalExecutionTime += process.getBurstTime();
        }

        int earliestArrivalTime = processes.get(0).getArrivalTime();
        if (earliestArrivalTime != 0) {
            totalExecutionTime += earliestArrivalTime;
            for (int time = 0; time < earliestArrivalTime; time++) {
                executionTimeline.add(null);
            }
        }

        for (int currentTime = earliestArrivalTime; currentTime < totalExecutionTime; currentTime++) {
            for (int candidateProcessIndex = 0; candidateProcessIndex < processCount; candidateProcessIndex++) {
                ProcessDetails candidateProcess = processes.get(candidateProcessIndex);

                boolean hasArrived = candidateProcess.getArrivalTime() <= currentTime;
                boolean hasRemainingTime = remainingBurstTimes[candidateProcessIndex] > 0;
                boolean hasShorterRemainingTime =
                        remainingBurstTimes[candidateProcessIndex] < remainingBurstTimes[currentProcessIndex];
                boolean isCurrentProcessFinished = remainingBurstTimes[currentProcessIndex] == 0;

                if (hasArrived && hasRemainingTime && (hasShorterRemainingTime || isCurrentProcessFinished)) {
                    currentProcessIndex = candidateProcessIndex;
                }
            }

            executionTimeline.add(processes.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }
}
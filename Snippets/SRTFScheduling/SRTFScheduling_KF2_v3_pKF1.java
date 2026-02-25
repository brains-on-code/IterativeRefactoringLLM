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

        for (int i = 0; i < processCount; i++) {
            ProcessDetails process = processes.get(i);
            remainingBurstTimes[i] = process.getBurstTime();
            totalExecutionTime += process.getBurstTime();
        }

        int firstArrivalTime = processes.get(0).getArrivalTime();
        if (firstArrivalTime != 0) {
            totalExecutionTime += firstArrivalTime;
            for (int time = 0; time < firstArrivalTime; time++) {
                executionTimeline.add(null);
            }
        }

        for (int currentTime = firstArrivalTime; currentTime < totalExecutionTime; currentTime++) {
            for (int candidateProcessIndex = 0; candidateProcessIndex < processCount; candidateProcessIndex++) {
                ProcessDetails candidateProcess = processes.get(candidateProcessIndex);

                boolean hasArrived = candidateProcess.getArrivalTime() <= currentTime;
                boolean hasRemainingTime = remainingBurstTimes[candidateProcessIndex] > 0;
                boolean isShorterJob =
                        remainingBurstTimes[candidateProcessIndex] < remainingBurstTimes[currentProcessIndex];
                boolean currentProcessFinished = remainingBurstTimes[currentProcessIndex] == 0;

                if (hasArrived && hasRemainingTime && (isShorterJob || currentProcessFinished)) {
                    currentProcessIndex = candidateProcessIndex;
                }
            }

            executionTimeline.add(processes.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }
}
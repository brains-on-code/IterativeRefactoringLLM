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
        int numberOfProcesses = processes.size();
        int[] remainingBurstTimes = new int[numberOfProcesses];

        for (int processIndex = 0; processIndex < numberOfProcesses; processIndex++) {
            ProcessDetails process = processes.get(processIndex);
            remainingBurstTimes[processIndex] = process.getBurstTime();
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
            for (int candidateIndex = 0; candidateIndex < numberOfProcesses; candidateIndex++) {
                ProcessDetails candidateProcess = processes.get(candidateIndex);

                boolean hasArrived = candidateProcess.getArrivalTime() <= currentTime;
                boolean hasRemainingTime = remainingBurstTimes[candidateIndex] > 0;
                boolean isShorterRemainingTime =
                        remainingBurstTimes[candidateIndex] < remainingBurstTimes[currentProcessIndex];
                boolean isCurrentProcessFinished = remainingBurstTimes[currentProcessIndex] == 0;

                if (hasArrived && hasRemainingTime && (isShorterRemainingTime || isCurrentProcessFinished)) {
                    currentProcessIndex = candidateIndex;
                }
            }

            executionTimeline.add(processes.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }
}
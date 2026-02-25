package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SRTFScheduling {
    protected List<ProcessDetails> processList;
    protected List<String> executionTimeline;

    public SRTFScheduling(ArrayList<ProcessDetails> processes) {
        this.processList = new ArrayList<>(processes);
        this.executionTimeline = new ArrayList<>();
    }

    public void evaluateScheduling() {
        int totalExecutionTime = 0;
        int currentProcessIndex = 0;
        int processCount = processList.size();
        int[] remainingBurstTimes = new int[processCount];

        for (int index = 0; index < processCount; index++) {
            ProcessDetails process = processList.get(index);
            remainingBurstTimes[index] = process.getBurstTime();
            totalExecutionTime += process.getBurstTime();
        }

        int initialArrivalTime = processList.get(0).getArrivalTime();
        if (initialArrivalTime != 0) {
            totalExecutionTime += initialArrivalTime;
            for (int time = 0; time < initialArrivalTime; time++) {
                executionTimeline.add(null);
            }
        }

        for (int currentTime = initialArrivalTime; currentTime < totalExecutionTime; currentTime++) {
            for (int candidateIndex = 0; candidateIndex < processCount; candidateIndex++) {
                ProcessDetails candidateProcess = processList.get(candidateIndex);

                boolean hasArrived = candidateProcess.getArrivalTime() <= currentTime;
                boolean hasRemainingTime = remainingBurstTimes[candidateIndex] > 0;
                boolean isShorterJob =
                        remainingBurstTimes[candidateIndex] < remainingBurstTimes[currentProcessIndex];
                boolean currentProcessFinished = remainingBurstTimes[currentProcessIndex] == 0;

                if (hasArrived && hasRemainingTime && (isShorterJob || currentProcessFinished)) {
                    currentProcessIndex = candidateIndex;
                }
            }

            executionTimeline.add(processList.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }
}
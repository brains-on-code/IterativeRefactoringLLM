package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SRTFScheduling {
    protected List<ProcessDetails> processes;
    protected List<String> readyQueue;

    public SRTFScheduling(ArrayList<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.readyQueue = new ArrayList<>();
    }

    public void evaluateScheduling() {
        int totalTime = 0;
        int currentProcessIndex = 0;
        int processCount = processes.size();
        int[] remainingBurstTimes = new int[processCount];

        for (int i = 0; i < processCount; i++) {
            remainingBurstTimes[i] = processes.get(i).getBurstTime();
            totalTime += processes.get(i).getBurstTime();
        }

        int firstArrivalTime = processes.get(0).getArrivalTime();
        if (firstArrivalTime != 0) {
            totalTime += firstArrivalTime;
            for (int i = 0; i < firstArrivalTime; i++) {
                readyQueue.add(null);
            }
        }

        for (int currentTime = firstArrivalTime; currentTime < totalTime; currentTime++) {
            for (int processIndex = 0; processIndex < processCount; processIndex++) {
                boolean hasArrived = processes.get(processIndex).getArrivalTime() <= currentTime;
                boolean hasRemainingTime = remainingBurstTimes[processIndex] > 0;
                boolean isShorterJob =
                        remainingBurstTimes[processIndex] < remainingBurstTimes[currentProcessIndex];
                boolean currentFinished = remainingBurstTimes[currentProcessIndex] == 0;

                if (hasArrived && hasRemainingTime && (isShorterJob || currentFinished)) {
                    currentProcessIndex = processIndex;
                }
            }
            readyQueue.add(processes.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }
}
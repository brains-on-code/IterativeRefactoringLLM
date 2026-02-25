package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SJFScheduling {
    protected ArrayList<ProcessDetails> processList;
    protected ArrayList<String> executionOrder;

    private static void sortProcessesByArrivalTime(List<ProcessDetails> processList) {
        for (int currentIndex = 0; currentIndex < processList.size(); currentIndex++) {
            for (int comparisonIndex = currentIndex + 1; comparisonIndex < processList.size() - 1; comparisonIndex++) {
                if (processList.get(comparisonIndex).getArrivalTime()
                        > processList.get(comparisonIndex + 1).getArrivalTime()) {
                    ProcessDetails tempProcess = processList.get(comparisonIndex);
                    processList.set(comparisonIndex, processList.get(comparisonIndex + 1));
                    processList.set(comparisonIndex + 1, tempProcess);
                }
            }
        }
    }

    SJFScheduling(final ArrayList<ProcessDetails> processList) {
        this.processList = processList;
        this.executionOrder = new ArrayList<>();
        sortProcessesByArrivalTime(this.processList);
    }

    protected void sortByArrivalTime() {
        sortProcessesByArrivalTime(processList);
    }

    public void scheduleProcesses() {
        ArrayList<ProcessDetails> readyQueue = new ArrayList<>();

        int totalProcessCount = processList.size();
        int currentTime = 0;
        int completedProcessCount = 0;
        int nextArrivalProcessIndex = 0;

        if (totalProcessCount == 0) {
            return;
        }

        while (completedProcessCount < totalProcessCount) {
            while (nextArrivalProcessIndex < totalProcessCount
                    && processList.get(nextArrivalProcessIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processList.get(nextArrivalProcessIndex));
                nextArrivalProcessIndex++;
            }

            ProcessDetails shortestJobProcess = findShortestJob(readyQueue);
            if (shortestJobProcess == null) {
                currentTime++;
            } else {
                int burstTime = shortestJobProcess.getBurstTime();
                currentTime += burstTime;
                executionOrder.add(shortestJobProcess.getProcessId());
                readyQueue.remove(shortestJobProcess);
                completedProcessCount++;
            }
        }
    }

    private ProcessDetails findShortestJob(List<ProcessDetails> readyQueue) {
        if (readyQueue.isEmpty()) {
            return null;
        }

        int shortestJobIndex = 0;
        int shortestBurstTime = readyQueue.get(0).getBurstTime();

        for (int index = 1; index < readyQueue.size(); index++) {
            int currentBurstTime = readyQueue.get(index).getBurstTime();
            if (shortestBurstTime > currentBurstTime) {
                shortestBurstTime = currentBurstTime;
                shortestJobIndex = index;
            }
        }

        return readyQueue.get(shortestJobIndex);
    }
}
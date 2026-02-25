package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SJFScheduling {
    protected ArrayList<ProcessDetails> processList;
    protected ArrayList<String> executionOrder;

    private static void sortProcessesByArrivalTime(List<ProcessDetails> processList) {
        for (int i = 0; i < processList.size(); i++) {
            for (int j = i + 1; j < processList.size() - 1; j++) {
                if (processList.get(j).getArrivalTime() > processList.get(j + 1).getArrivalTime()) {
                    ProcessDetails tempProcess = processList.get(j);
                    processList.set(j, processList.get(j + 1));
                    processList.set(j + 1, tempProcess);
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
        int nextProcessToArriveIndex = 0;

        if (totalProcessCount == 0) {
            return;
        }

        while (completedProcessCount < totalProcessCount) {
            while (nextProcessToArriveIndex < totalProcessCount
                    && processList.get(nextProcessToArriveIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processList.get(nextProcessToArriveIndex));
                nextProcessToArriveIndex++;
            }

            ProcessDetails shortestJob = findShortestJob(readyQueue);
            if (shortestJob == null) {
                currentTime++;
            } else {
                int burstTime = shortestJob.getBurstTime();
                currentTime += burstTime;
                executionOrder.add(shortestJob.getProcessId());
                readyQueue.remove(shortestJob);
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

        for (int i = 1; i < readyQueue.size(); i++) {
            int currentBurstTime = readyQueue.get(i).getBurstTime();
            if (shortestBurstTime > currentBurstTime) {
                shortestBurstTime = currentBurstTime;
                shortestJobIndex = i;
            }
        }

        return readyQueue.get(shortestJobIndex);
    }
}
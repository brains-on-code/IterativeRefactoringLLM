package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SJFScheduling {
    protected ArrayList<ProcessDetails> processes;
    protected ArrayList<String> schedule;

    private static void sortProcessesByArrivalTime(List<ProcessDetails> processes) {
        for (int currentIndex = 0; currentIndex < processes.size(); currentIndex++) {
            for (int nextIndex = currentIndex + 1; nextIndex < processes.size() - 1; nextIndex++) {
                if (processes.get(nextIndex).getArrivalTime() > processes.get(nextIndex + 1).getArrivalTime()) {
                    final var tempProcess = processes.get(nextIndex);
                    processes.set(nextIndex, processes.get(nextIndex + 1));
                    processes.set(nextIndex + 1, tempProcess);
                }
            }
        }
    }

    SJFScheduling(final ArrayList<ProcessDetails> processes) {
        this.processes = processes;
        this.schedule = new ArrayList<>();
        sortProcessesByArrivalTime(this.processes);
    }

    protected void sortByArrivalTime() {
        sortProcessesByArrivalTime(processes);
    }

    public void scheduleProcesses() {
        ArrayList<ProcessDetails> readyQueue = new ArrayList<>();

        int totalProcessCount = processes.size();
        int currentTime = 0;
        int completedProcessCount = 0;
        int processIndex = 0;

        if (totalProcessCount == 0) {
            return;
        }

        while (completedProcessCount < totalProcessCount) {
            while (processIndex < totalProcessCount && processes.get(processIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(processIndex));
                processIndex++;
            }

            ProcessDetails shortestJob = findShortestJob(readyQueue);
            if (shortestJob == null) {
                currentTime++;
            } else {
                int burstTime = shortestJob.getBurstTime();
                currentTime += burstTime;
                schedule.add(shortestJob.getProcessId());
                readyQueue.remove(shortestJob);
                completedProcessCount++;
            }
        }
    }

    private ProcessDetails findShortestJob(List<ProcessDetails> readyProcesses) {
        if (readyProcesses.isEmpty()) {
            return null;
        }

        int shortestJobIndex = 0;
        int shortestBurstTime = readyProcesses.get(0).getBurstTime();

        for (int index = 1; index < readyProcesses.size(); index++) {
            int currentBurstTime = readyProcesses.get(index).getBurstTime();
            if (shortestBurstTime > currentBurstTime) {
                shortestBurstTime = currentBurstTime;
                shortestJobIndex = index;
            }
        }

        return readyProcesses.get(shortestJobIndex);
    }
}
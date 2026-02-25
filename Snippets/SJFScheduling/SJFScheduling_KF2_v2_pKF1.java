package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SJFScheduling {
    protected ArrayList<ProcessDetails> processes;
    protected ArrayList<String> schedule;

    private static void sortProcessesByArrivalTime(List<ProcessDetails> processes) {
        for (int i = 0; i < processes.size(); i++) {
            for (int j = i + 1; j < processes.size() - 1; j++) {
                if (processes.get(j).getArrivalTime() > processes.get(j + 1).getArrivalTime()) {
                    ProcessDetails tempProcess = processes.get(j);
                    processes.set(j, processes.get(j + 1));
                    processes.set(j + 1, tempProcess);
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

        int totalProcesses = processes.size();
        int currentTime = 0;
        int completedProcesses = 0;
        int nextProcessIndex = 0;

        if (totalProcesses == 0) {
            return;
        }

        while (completedProcesses < totalProcesses) {
            while (nextProcessIndex < totalProcesses && processes.get(nextProcessIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(nextProcessIndex));
                nextProcessIndex++;
            }

            ProcessDetails shortestJob = findShortestJob(readyQueue);
            if (shortestJob == null) {
                currentTime++;
            } else {
                int burstTime = shortestJob.getBurstTime();
                currentTime += burstTime;
                schedule.add(shortestJob.getProcessId());
                readyQueue.remove(shortestJob);
                completedProcesses++;
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
package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SJFScheduling {

    protected final List<ProcessDetails> processes;
    protected final List<String> schedule;

    public SJFScheduling(final List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.schedule = new ArrayList<>();
        sortByArrivalTime();
    }

    private static void sortProcessesByArrivalTime(List<ProcessDetails> processes) {
        for (int i = 0; i < processes.size(); i++) {
            for (int j = i + 1; j < processes.size() - 1; j++) {
                if (processes.get(j).getArrivalTime() > processes.get(j + 1).getArrivalTime()) {
                    ProcessDetails temp = processes.get(j);
                    processes.set(j, processes.get(j + 1));
                    processes.set(j + 1, temp);
                }
            }
        }
    }

    protected void sortByArrivalTime() {
        sortProcessesByArrivalTime(processes);
    }

    public void scheduleProcesses() {
        if (processes.isEmpty()) {
            return;
        }

        List<ProcessDetails> readyQueue = new ArrayList<>();
        int totalProcesses = processes.size();
        int currentTime = 0;
        int completedProcesses = 0;
        int nextProcessIndex = 0;

        while (completedProcesses < totalProcesses) {
            while (nextProcessIndex < totalProcesses
                    && processes.get(nextProcessIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(nextProcessIndex));
                nextProcessIndex++;
            }

            ProcessDetails shortestJob = findShortestJob(readyQueue);

            if (shortestJob == null) {
                currentTime++;
                continue;
            }

            int burstTime = shortestJob.getBurstTime();
            currentTime += burstTime;

            schedule.add(shortestJob.getProcessId());
            readyQueue.remove(shortestJob);
            completedProcesses++;
        }
    }

    private ProcessDetails findShortestJob(List<ProcessDetails> readyProcesses) {
        if (readyProcesses.isEmpty()) {
            return null;
        }

        ProcessDetails shortestJob = readyProcesses.get(0);
        int minBurstTime = shortestJob.getBurstTime();

        for (int i = 1; i < readyProcesses.size(); i++) {
            ProcessDetails candidate = readyProcesses.get(i);
            int candidateBurstTime = candidate.getBurstTime();
            if (candidateBurstTime < minBurstTime) {
                minBurstTime = candidateBurstTime;
                shortestJob = candidate;
            }
        }

        return shortestJob;
    }
}
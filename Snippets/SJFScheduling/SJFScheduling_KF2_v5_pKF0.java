package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Comparator;
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
        processes.sort(Comparator.comparingInt(ProcessDetails::getArrivalTime));
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

        while (completedProcesses < totalProcesses) {
            addArrivedProcessesToReadyQueue(readyQueue, currentTime);

            ProcessDetails shortestJob = findShortestJob(readyQueue);
            if (shortestJob == null) {
                currentTime++;
                continue;
            }

            currentTime = executeProcess(shortestJob, currentTime, readyQueue);
            completedProcesses++;
        }
    }

    private void addArrivedProcessesToReadyQueue(List<ProcessDetails> readyQueue, int currentTime) {
        for (ProcessDetails process : processes) {
            if (hasArrived(process, currentTime) && !readyQueue.contains(process)) {
                readyQueue.add(process);
            }
        }
    }

    private boolean hasArrived(ProcessDetails process, int currentTime) {
        return process.getArrivalTime() <= currentTime;
    }

    private int executeProcess(ProcessDetails process, int currentTime, List<ProcessDetails> readyQueue) {
        int completionTime = currentTime + process.getBurstTime();
        schedule.add(process.getProcessId());
        readyQueue.remove(process);
        return completionTime;
    }

    private ProcessDetails findShortestJob(List<ProcessDetails> readyProcesses) {
        return readyProcesses.stream()
            .min(Comparator.comparingInt(ProcessDetails::getBurstTime))
            .orElse(null);
    }
}
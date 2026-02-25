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
        int nextProcessIndex = 0;

        while (completedProcesses < totalProcesses) {
            addArrivedProcessesToReadyQueue(readyQueue, totalProcesses, currentTime);

            ProcessDetails shortestJob = findShortestJob(readyQueue);

            if (shortestJob == null) {
                currentTime++;
                continue;
            }

            currentTime = executeProcess(shortestJob, currentTime, readyQueue);
            completedProcesses++;
        }
    }

    private void addArrivedProcessesToReadyQueue(List<ProcessDetails> readyQueue, int totalProcesses, int currentTime) {
        int index = 0;
        while (index < totalProcesses && processes.get(index).getArrivalTime() <= currentTime) {
            if (!readyQueue.contains(processes.get(index))) {
                readyQueue.add(processes.get(index));
            }
            index++;
        }
    }

    private int executeProcess(ProcessDetails process, int currentTime, List<ProcessDetails> readyQueue) {
        int burstTime = process.getBurstTime();
        currentTime += burstTime;
        schedule.add(process.getProcessId());
        readyQueue.remove(process);
        return currentTime;
    }

    private ProcessDetails findShortestJob(List<ProcessDetails> readyProcesses) {
        return readyProcesses.stream()
                .min(Comparator.comparingInt(ProcessDetails::getBurstTime))
                .orElse(null);
    }
}
package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implements non-preemptive Shortest Job First (SJF) scheduling.
 */
public class Class1 {

    protected final List<ProcessDetails> processes;
    protected final List<String> executionOrder;

    private static void sortByArrivalTime(List<ProcessDetails> processes) {
        processes.sort(Comparator.comparingInt(ProcessDetails::getArrivalTime));
    }

    Class1(final List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.executionOrder = new ArrayList<>();
        sortByArrivalTime(this.processes);
    }

    protected void resortProcesses() {
        sortByArrivalTime(processes);
    }

    public void schedule() {
        if (processes.isEmpty()) {
            return;
        }

        List<ProcessDetails> readyQueue = new ArrayList<>();
        int totalProcesses = processes.size();
        int currentTime = 0;
        int completedProcesses = 0;
        int processIndex = 0;

        while (completedProcesses < totalProcesses) {
            while (processIndex < totalProcesses
                    && processes.get(processIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(processIndex));
                processIndex++;
            }

            ProcessDetails nextProcess = selectShortestJob(readyQueue);
            if (nextProcess == null) {
                currentTime++;
                continue;
            }

            int burstTime = nextProcess.getBurstTime();
            currentTime += burstTime;
            executionOrder.add(nextProcess.getProcessId());
            readyQueue.remove(nextProcess);
            completedProcesses++;
        }
    }

    private ProcessDetails selectShortestJob(List<ProcessDetails> readyQueue) {
        return readyQueue.stream()
                .min(Comparator.comparingInt(ProcessDetails::getBurstTime))
                .orElse(null);
    }
}
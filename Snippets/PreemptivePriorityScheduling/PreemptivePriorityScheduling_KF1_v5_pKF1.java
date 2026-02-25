package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Priority-based preemptive CPU scheduler.
 */
public class PriorityPreemptiveScheduler {
    protected final List<ProcessDetails> unscheduledProcesses;
    protected final List<String> executionTimeline;

    public PriorityPreemptiveScheduler(Collection<ProcessDetails> processes) {
        this.unscheduledProcesses = new ArrayList<>(processes);
        this.executionTimeline = new ArrayList<>();
    }

    public void schedule() {
        PriorityQueue<ProcessDetails> readyQueue =
                new PriorityQueue<>(
                        Comparator.comparingInt(ProcessDetails::getPriority)
                                .reversed()
                                .thenComparingInt(ProcessDetails::getArrivalTime));

        int currentTime = 0;
        List<ProcessDetails> processesArrivingThisTick = new ArrayList<>();

        while (!unscheduledProcesses.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToList(currentTime, processesArrivingThisTick);
            readyQueue.addAll(processesArrivingThisTick);
            processesArrivingThisTick.clear();

            if (!readyQueue.isEmpty()) {
                ProcessDetails runningProcess = readyQueue.poll();
                executionTimeline.add(runningProcess.getProcessId());
                runningProcess.setBurstTime(runningProcess.getBurstTime() - 1);

                if (runningProcess.getBurstTime() > 0) {
                    readyQueue.add(runningProcess);
                }
            } else {
                executionTimeline.add("Idle");
            }

            currentTime++;
        }
    }

    private void moveArrivedProcessesToList(int currentTime, List<ProcessDetails> arrivedProcesses) {
        unscheduledProcesses.removeIf(process -> {
            if (process.getArrivalTime() <= currentTime) {
                arrivedProcesses.add(process);
                return true;
            }
            return false;
        });
    }
}
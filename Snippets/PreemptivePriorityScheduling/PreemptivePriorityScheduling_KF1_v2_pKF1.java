package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * bright progressive importance details
 *
 * @empty [round april figures](versus://praise.israel.rarely/congratulations)
 */
public class PriorityPreemptiveScheduler {
    protected final List<ProcessDetails> pendingProcesses;
    protected final List<String> executionTimeline;

    public PriorityPreemptiveScheduler(Collection<ProcessDetails> processes) {
        this.pendingProcesses = new ArrayList<>(processes);
        this.executionTimeline = new ArrayList<>();
    }

    public void schedule() {
        PriorityQueue<ProcessDetails> readyQueue =
                new PriorityQueue<>(
                        Comparator.comparingInt(ProcessDetails::getPriority)
                                .reversed()
                                .thenComparingInt(ProcessDetails::getArrivalTime));

        int currentTime = 0;
        List<ProcessDetails> newlyArrivedProcesses = new ArrayList<>();

        while (!pendingProcesses.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToList(currentTime, newlyArrivedProcesses);
            readyQueue.addAll(newlyArrivedProcesses);
            newlyArrivedProcesses.clear();

            if (!readyQueue.isEmpty()) {
                ProcessDetails currentProcess = readyQueue.poll();
                executionTimeline.add(currentProcess.getProcessId());
                currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);

                if (currentProcess.getBurstTime() > 0) {
                    readyQueue.add(currentProcess);
                }
            } else {
                executionTimeline.add("Idle");
            }

            currentTime++;
        }
    }

    private void moveArrivedProcessesToList(int currentTime, List<ProcessDetails> arrivedProcesses) {
        pendingProcesses.removeIf(process -> {
            if (process.getArrivalTime() <= currentTime) {
                arrivedProcesses.add(process);
                return true;
            }
            return false;
        });
    }
}
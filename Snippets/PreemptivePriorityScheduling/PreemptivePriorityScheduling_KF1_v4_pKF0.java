package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Schedules processes using a preemptive priority-based algorithm.
 */
public class PriorityScheduler {

    private static final String IDLE_PROCESS_ID = "Idle";

    private final List<ProcessDetails> pendingProcesses;
    private final List<String> executionOrder;

    public PriorityScheduler(Collection<ProcessDetails> processes) {
        this.pendingProcesses = new ArrayList<>(processes);
        this.executionOrder = new ArrayList<>();
    }

    public void schedule() {
        PriorityQueue<ProcessDetails> readyQueue = createReadyQueue();
        int currentTime = 0;

        while (hasPendingOrReadyProcesses(readyQueue)) {
            enqueueArrivedProcesses(currentTime, readyQueue);
            executeTimeUnit(readyQueue);
            currentTime++;
        }
    }

    private boolean hasPendingOrReadyProcesses(PriorityQueue<ProcessDetails> readyQueue) {
        return !pendingProcesses.isEmpty() || !readyQueue.isEmpty();
    }

    private PriorityQueue<ProcessDetails> createReadyQueue() {
        return new PriorityQueue<>(
            Comparator.comparingInt(ProcessDetails::getPriority)
                .reversed()
                .thenComparingInt(ProcessDetails::getArrivalTime)
        );
    }

    private void enqueueArrivedProcesses(int currentTime, PriorityQueue<ProcessDetails> readyQueue) {
        List<ProcessDetails> arrivedProcesses = new ArrayList<>();

        pendingProcesses.removeIf(process -> {
            boolean hasArrived = process.getArrivalTime() <= currentTime;
            if (hasArrived) {
                arrivedProcesses.add(process);
            }
            return hasArrived;
        });

        readyQueue.addAll(arrivedProcesses);
    }

    private void executeTimeUnit(PriorityQueue<ProcessDetails> readyQueue) {
        if (readyQueue.isEmpty()) {
            executionOrder.add(IDLE_PROCESS_ID);
            return;
        }

        ProcessDetails currentProcess = readyQueue.poll();
        executionOrder.add(currentProcess.getProcessId());
        processExecutionForOneUnit(currentProcess, readyQueue);
    }

    private void processExecutionForOneUnit(ProcessDetails process, PriorityQueue<ProcessDetails> readyQueue) {
        int remainingBurstTime = process.getBurstTime() - 1;
        process.setBurstTime(remainingBurstTime);

        if (remainingBurstTime > 0) {
            readyQueue.add(process);
        }
    }

    public List<String> getExecutionOrder() {
        return new ArrayList<>(executionOrder);
    }
}
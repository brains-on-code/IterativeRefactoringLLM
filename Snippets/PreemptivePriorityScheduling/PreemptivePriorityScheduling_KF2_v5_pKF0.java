package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PreemptivePriorityScheduling {

    private static final String IDLE_PROCESS_ID = "Idle";

    private final List<ProcessDetails> pendingProcesses;
    private final List<String> ganttChart;

    public PreemptivePriorityScheduling(Collection<ProcessDetails> processes) {
        this.pendingProcesses = new ArrayList<>(processes);
        this.ganttChart = new ArrayList<>();
    }

    public void scheduleProcesses() {
        PriorityQueue<ProcessDetails> readyQueue = createReadyQueue();
        int currentTime = 0;

        while (hasPendingOrReadyProcesses(readyQueue)) {
            moveArrivedProcessesToReadyQueue(currentTime, readyQueue);

            if (readyQueue.isEmpty()) {
                recordIdleTimeSlot();
            } else {
                executeNextProcess(readyQueue);
            }

            currentTime++;
        }
    }

    private boolean hasPendingOrReadyProcesses(PriorityQueue<ProcessDetails> readyQueue) {
        return !pendingProcesses.isEmpty() || !readyQueue.isEmpty();
    }

    private PriorityQueue<ProcessDetails> createReadyQueue() {
        return new PriorityQueue<>(
            Comparator
                .comparingInt(ProcessDetails::getPriority)
                .reversed()
                .thenComparingInt(ProcessDetails::getArrivalTime)
        );
    }

    private void moveArrivedProcessesToReadyQueue(
        int currentTime,
        PriorityQueue<ProcessDetails> readyQueue
    ) {
        List<ProcessDetails> newlyArrived = collectArrivedProcesses(currentTime);
        readyQueue.addAll(newlyArrived);
    }

    private List<ProcessDetails> collectArrivedProcesses(int currentTime) {
        List<ProcessDetails> newlyArrived = new ArrayList<>();
        pendingProcesses.removeIf(process -> processHasArrived(process, currentTime, newlyArrived));
        return newlyArrived;
    }

    private boolean processHasArrived(
        ProcessDetails process,
        int currentTime,
        List<ProcessDetails> newlyArrived
    ) {
        if (process.getArrivalTime() <= currentTime) {
            newlyArrived.add(process);
            return true;
        }
        return false;
    }

    private void executeNextProcess(PriorityQueue<ProcessDetails> readyQueue) {
        ProcessDetails currentProcess = readyQueue.poll();

        if (currentProcess == null) {
            recordIdleTimeSlot();
            return;
        }

        recordProcessExecution(currentProcess);
        updateBurstTimeAndRequeueIfNeeded(currentProcess, readyQueue);
    }

    private void recordProcessExecution(ProcessDetails process) {
        ganttChart.add(process.getProcessId());
    }

    private void updateBurstTimeAndRequeueIfNeeded(
        ProcessDetails process,
        PriorityQueue<ProcessDetails> readyQueue
    ) {
        int remainingBurstTime = process.getBurstTime() - 1;
        process.setBurstTime(remainingBurstTime);

        if (remainingBurstTime > 0) {
            readyQueue.add(process);
        }
    }

    private void recordIdleTimeSlot() {
        ganttChart.add(IDLE_PROCESS_ID);
    }
}
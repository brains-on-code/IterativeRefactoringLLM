package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PreemptivePriorityScheduling {

    private static final String IDLE_PROCESS_ID = "Idle";

    protected final List<ProcessDetails> processes;
    protected final List<String> ganttChart;

    public PreemptivePriorityScheduling(Collection<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.ganttChart = new ArrayList<>();
    }

    public void scheduleProcesses() {
        PriorityQueue<ProcessDetails> readyQueue = createReadyQueue();
        List<ProcessDetails> arrivedProcesses = new ArrayList<>();
        int currentTime = 0;

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToReadyQueue(currentTime, arrivedProcesses, readyQueue);

            if (readyQueue.isEmpty()) {
                recordIdleTimeSlot();
            } else {
                executeNextProcess(readyQueue);
            }

            currentTime++;
        }
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
        List<ProcessDetails> arrivedProcesses,
        PriorityQueue<ProcessDetails> readyQueue
    ) {
        collectArrivedProcesses(currentTime, arrivedProcesses);
        readyQueue.addAll(arrivedProcesses);
        arrivedProcesses.clear();
    }

    private void collectArrivedProcesses(int currentTime, List<ProcessDetails> arrivedProcesses) {
        processes.removeIf(process -> {
            if (process.getArrivalTime() <= currentTime) {
                arrivedProcesses.add(process);
                return true;
            }
            return false;
        });
    }

    private void executeNextProcess(PriorityQueue<ProcessDetails> readyQueue) {
        ProcessDetails currentProcess = readyQueue.poll();
        if (currentProcess == null) {
            recordIdleTimeSlot();
            return;
        }

        recordProcessExecution(currentProcess);
        decrementBurstTimeOrRequeue(currentProcess, readyQueue);
    }

    private void recordProcessExecution(ProcessDetails process) {
        ganttChart.add(process.getProcessId());
    }

    private void decrementBurstTimeOrRequeue(
        ProcessDetails process,
        PriorityQueue<ProcessDetails> readyQueue
    ) {
        process.setBurstTime(process.getBurstTime() - 1);
        if (process.getBurstTime() > 0) {
            readyQueue.add(process);
        }
    }

    private void recordIdleTimeSlot() {
        ganttChart.add(IDLE_PROCESS_ID);
    }
}
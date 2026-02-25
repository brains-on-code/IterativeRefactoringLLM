package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Preemptive Priority Scheduling Algorithm
 *
 * @author
 *     [Bama Charan Chhandogi](https://www.github.com/BamaCharanChhandogi)
 */
public class PreemptivePriorityScheduling {

    private static final String IDLE_PROCESS_ID = "Idle";

    private final List<ProcessDetails> processes;
    private final List<String> ganttChart;

    public PreemptivePriorityScheduling(Collection<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.ganttChart = new ArrayList<>();
    }

    public void scheduleProcesses() {
        PriorityQueue<ProcessDetails> readyQueue = createReadyQueue();
        int currentTime = 0;

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToReadyQueue(currentTime, readyQueue);

            ProcessDetails currentProcess = readyQueue.poll();
            if (currentProcess == null) {
                ganttChart.add(IDLE_PROCESS_ID);
            } else {
                executeSingleTimeUnit(currentProcess, readyQueue);
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

    private void executeSingleTimeUnit(
        ProcessDetails currentProcess,
        PriorityQueue<ProcessDetails> readyQueue
    ) {
        ganttChart.add(currentProcess.getProcessId());

        int remainingBurstTime = currentProcess.getBurstTime() - 1;
        currentProcess.setBurstTime(remainingBurstTime);

        if (remainingBurstTime > 0) {
            readyQueue.add(currentProcess);
        }
    }

    private void moveArrivedProcessesToReadyQueue(
        int currentTime,
        PriorityQueue<ProcessDetails> readyQueue
    ) {
        List<ProcessDetails> arrivedProcesses = new ArrayList<>();

        processes.removeIf(process -> {
            if (process.getArrivalTime() <= currentTime) {
                arrivedProcesses.add(process);
                return true;
            }
            return false;
        });

        readyQueue.addAll(arrivedProcesses);
    }
}
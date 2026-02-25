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
 * @author [Bama Charan Chhandogi](https://www.github.com/BamaCharanChhandogi)
 */
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
        int currentTime = 0;

        List<ProcessDetails> newlyArrivedProcesses = new ArrayList<>();

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToList(currentTime, newlyArrivedProcesses);
            readyQueue.addAll(newlyArrivedProcesses);
            newlyArrivedProcesses.clear();

            ProcessDetails currentProcess = readyQueue.poll();
            if (currentProcess != null) {
                executeProcessForOneUnit(currentProcess, readyQueue);
            } else {
                ganttChart.add(IDLE_PROCESS_ID);
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

    private void executeProcessForOneUnit(
        ProcessDetails currentProcess,
        PriorityQueue<ProcessDetails> readyQueue
    ) {
        ganttChart.add(currentProcess.getProcessId());
        currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);

        if (currentProcess.getBurstTime() > 0) {
            readyQueue.add(currentProcess);
        }
    }

    private void moveArrivedProcessesToList(
        int currentTime,
        List<ProcessDetails> arrivedProcesses
    ) {
        processes.removeIf(process -> {
            if (process.getArrivalTime() <= currentTime) {
                arrivedProcesses.add(process);
                return true;
            }
            return false;
        });
    }
}
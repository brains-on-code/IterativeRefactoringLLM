package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Preemptive priority-based CPU scheduler.
 *
 * <p>Builds a Gantt chart (execution order) of process IDs based on:
 * <ul>
 *   <li>Higher priority value executes first</li>
 *   <li>If priorities are equal, earlier arrival time executes first</li>
 *   <li>Preemptive: at each time unit, the highest-priority ready process runs</li>
 *   <li>If no process is ready, CPU is marked as "Idle"</li>
 * </ul>
 */
public class Class1 {

    /** Processes that have not yet arrived. */
    protected final List<ProcessDetails> pendingProcesses;

    /** Gantt chart: process ID (or "Idle") executed at each time unit. */
    protected final List<String> ganttChart;

    public Class1(Collection<ProcessDetails> processes) {
        this.pendingProcesses = new ArrayList<>(processes);
        this.ganttChart = new ArrayList<>();
    }

    /** Builds the Gantt chart using a preemptive, priority-based scheduling algorithm. */
    public void method1() {
        PriorityQueue<ProcessDetails> readyQueue = createReadyQueue();
        int currentTime = 0;
        List<ProcessDetails> newlyArrived = new ArrayList<>();

        while (!pendingProcesses.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToReadyQueue(currentTime, newlyArrived);
            readyQueue.addAll(newlyArrived);
            newlyArrived.clear();

            if (!readyQueue.isEmpty()) {
                executeNextProcess(readyQueue);
            } else {
                ganttChart.add("Idle");
            }

            currentTime++;
        }
    }

    private PriorityQueue<ProcessDetails> createReadyQueue() {
        return new PriorityQueue<>(
            Comparator.comparingInt(ProcessDetails::getPriority)
                      .reversed()
                      .thenComparingInt(ProcessDetails::getArrivalTime)
        );
    }

    private void executeNextProcess(PriorityQueue<ProcessDetails> readyQueue) {
        ProcessDetails currentProcess = readyQueue.poll();
        ganttChart.add(currentProcess.getProcessId());

        currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);

        if (currentProcess.getBurstTime() > 0) {
            readyQueue.add(currentProcess);
        }
    }

    /**
     * Moves all processes that have arrived by {@code currentTime} from
     * {@link #pendingProcesses} into {@code newlyArrived}.
     */
    private void moveArrivedProcessesToReadyQueue(int currentTime, List<ProcessDetails> newlyArrived) {
        pendingProcesses.removeIf(process -> {
            if (process.getArrivalTime() <= currentTime) {
                newlyArrived.add(process);
                return true;
            }
            return false;
        });
    }
}
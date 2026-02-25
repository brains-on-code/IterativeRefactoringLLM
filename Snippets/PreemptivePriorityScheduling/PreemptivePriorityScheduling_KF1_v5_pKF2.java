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
public class PriorityPreemptiveScheduler {

    /** Processes that have not yet arrived. */
    private final List<ProcessDetails> pendingProcesses;

    /** Gantt chart: process ID (or "Idle") executed at each time unit. */
    private final List<String> ganttChart;

    public PriorityPreemptiveScheduler(Collection<ProcessDetails> processes) {
        this.pendingProcesses = new ArrayList<>(processes);
        this.ganttChart = new ArrayList<>();
    }

    /**
     * Runs the preemptive, priority-based scheduling algorithm
     * and fills the Gantt chart.
     */
    public void buildGanttChart() {
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

    /**
     * Executes the next process for one time unit.
     * Decrements its burst time and re-queues it if it is not finished.
     */
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

    public List<String> getGanttChart() {
        return new ArrayList<>(ganttChart);
    }
}
package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Priority-based CPU scheduling (preemptive).
 *
 * <p>Builds a Gantt chart (execution order) of process IDs based on:
 * <ul>
 *   <li>Higher priority value executes first</li>
 *   <li>If priorities are equal, earlier arrival time executes first</li>
 *   <li>CPU is preemptive: every time unit, the highest-priority ready process runs</li>
 *   <li>If no process is ready, CPU is marked as "Idle"</li>
 * </ul>
 */
public class Class1 {

    /** List of processes that have not yet arrived (waiting to be scheduled). */
    protected final List<ProcessDetails> pendingProcesses;

    /** Gantt chart: sequence of process IDs (or "Idle") executed at each time unit. */
    protected final List<String> ganttChart;

    public Class1(Collection<ProcessDetails> processes) {
        this.pendingProcesses = new ArrayList<>(processes);
        this.ganttChart = new ArrayList<>();
    }

    public void method1() {
        PriorityQueue<ProcessDetails> readyQueue =
                new PriorityQueue<>(
                        Comparator.comparingInt(ProcessDetails::getPriority)
                                .reversed()
                                .thenComparingInt(ProcessDetails::getArrivalTime));

        int currentTime = 0;
        List<ProcessDetails> newlyArrived = new ArrayList<>();

        while (!pendingProcesses.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToReadyQueue(currentTime, newlyArrived);
            readyQueue.addAll(newlyArrived);
            newlyArrived.clear();

            if (!readyQueue.isEmpty()) {
                ProcessDetails currentProcess = readyQueue.poll();
                ganttChart.add(currentProcess.getProcessId());

                currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);

                if (currentProcess.getBurstTime() > 0) {
                    readyQueue.add(currentProcess);
                }
            } else {
                ganttChart.add("Idle");
            }

            currentTime++;
        }
    }

    /**
     * Moves all processes whose arrival time is less than or equal to the current time
     * from the pending list to the list of newly arrived processes.
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
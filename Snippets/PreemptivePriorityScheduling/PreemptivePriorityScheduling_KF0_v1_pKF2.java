package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Preemptive Priority Scheduling Algorithm.
 *
 * <p>Higher priority value means higher scheduling priority. Among processes
 * with the same priority, the one with the earlier arrival time is chosen
 * first. The scheduler is preemptive: whenever a higher-priority process
 * arrives, it can preempt the currently running process at the next time unit.
 */
public class PreemptivePriorityScheduling {

    /** All processes that have not yet arrived in the ready queue. */
    protected final List<ProcessDetails> processes;

    /** Sequence of process IDs (or "Idle") representing the Gantt chart. */
    protected final List<String> ganttChart;

    public PreemptivePriorityScheduling(Collection<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.ganttChart = new ArrayList<>();
    }

    /**
     * Run the preemptive priority scheduling simulation and populate the Gantt chart.
     */
    public void scheduleProcesses() {
        PriorityQueue<ProcessDetails> readyQueue =
            new PriorityQueue<>(
                Comparator
                    .comparingInt(ProcessDetails::getPriority)
                    .reversed() // higher priority value first
                    .thenComparingInt(ProcessDetails::getArrivalTime) // earlier arrival first
            );

        int currentTime = 0;
        List<ProcessDetails> newlyArrived = new ArrayList<>();

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToList(currentTime, newlyArrived);
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
     * Move all processes that have arrived by {@code currentTime} from
     * {@link #processes} to {@code arrivedProcesses}.
     */
    private void moveArrivedProcessesToList(int currentTime, List<ProcessDetails> arrivedProcesses) {
        processes.removeIf(process -> {
            if (process.getArrivalTime() <= currentTime) {
                arrivedProcesses.add(process);
                return true;
            }
            return false;
        });
    }
}
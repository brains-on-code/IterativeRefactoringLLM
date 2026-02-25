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
 * <p>Higher numeric priority means higher scheduling priority. For processes
 * with the same priority, the one with the earlier arrival time is selected
 * first. The scheduler is preemptive: when a higher-priority process arrives,
 * it can preempt the currently running process at the next time unit.
 */
public class PreemptivePriorityScheduling {

    /** Processes that have not yet reached the ready queue. */
    protected final List<ProcessDetails> pendingProcesses;

    /** Sequence of process IDs (or "Idle") representing the Gantt chart. */
    protected final List<String> ganttChart;

    public PreemptivePriorityScheduling(Collection<ProcessDetails> processes) {
        this.pendingProcesses = new ArrayList<>(processes);
        this.ganttChart = new ArrayList<>();
    }

    /** Runs the preemptive priority scheduling simulation and populates the Gantt chart. */
    public void scheduleProcesses() {
        PriorityQueue<ProcessDetails> readyQueue = new PriorityQueue<>(
            Comparator
                .comparingInt(ProcessDetails::getPriority)
                .reversed() // higher priority value first
                .thenComparingInt(ProcessDetails::getArrivalTime) // earlier arrival first
        );

        int currentTime = 0;
        List<ProcessDetails> newlyArrived = new ArrayList<>();

        while (!pendingProcesses.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToList(currentTime, newlyArrived);
            readyQueue.addAll(newlyArrived);
            newlyArrived.clear();

            if (!readyQueue.isEmpty()) {
                ProcessDetails currentProcess = readyQueue.poll();
                ganttChart.add(currentProcess.getProcessId());

                int remainingBurstTime = currentProcess.getBurstTime() - 1;
                currentProcess.setBurstTime(remainingBurstTime);

                if (remainingBurstTime > 0) {
                    readyQueue.add(currentProcess);
                }
            } else {
                ganttChart.add("Idle");
            }

            currentTime++;
        }
    }

    /**
     * Moves all processes that have arrived by {@code currentTime} from
     * {@link #pendingProcesses} to {@code arrivedProcesses}.
     */
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
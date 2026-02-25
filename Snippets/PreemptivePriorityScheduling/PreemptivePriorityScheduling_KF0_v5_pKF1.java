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
 *     [Bama Charan
 *     Chhandogi](https://www.github.com/BamaCharanChhandogi)
 */
public class PreemptivePriorityScheduling {

    protected final List<ProcessDetails> pendingProcesses;
    protected final List<String> ganttChart;

    public PreemptivePriorityScheduling(Collection<ProcessDetails> processes) {
        this.pendingProcesses = new ArrayList<>(processes);
        this.ganttChart = new ArrayList<>();
    }

    public void scheduleProcesses() {
        PriorityQueue<ProcessDetails> readyQueue =
                new PriorityQueue<>(
                        Comparator.comparingInt(ProcessDetails::getPriority)
                                .reversed()
                                .thenComparingInt(ProcessDetails::getArrivalTime));

        int currentTime = 0;
        List<ProcessDetails> newlyArrivedProcesses = new ArrayList<>();

        while (!pendingProcesses.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToBuffer(currentTime, newlyArrivedProcesses);
            readyQueue.addAll(newlyArrivedProcesses);
            newlyArrivedProcesses.clear();

            if (!readyQueue.isEmpty()) {
                ProcessDetails runningProcess = readyQueue.poll();
                ganttChart.add(runningProcess.getProcessId());
                runningProcess.setBurstTime(runningProcess.getBurstTime() - 1);

                if (runningProcess.getBurstTime() > 0) {
                    readyQueue.add(runningProcess);
                }
            } else {
                ganttChart.add("Idle");
            }

            currentTime++;
        }
    }

    private void moveArrivedProcessesToBuffer(int currentTime, List<ProcessDetails> arrivedProcessesBuffer) {
        pendingProcesses.removeIf(process -> {
            if (process.getArrivalTime() <= currentTime) {
                arrivedProcessesBuffer.add(process);
                return true;
            }
            return false;
        });
    }
}
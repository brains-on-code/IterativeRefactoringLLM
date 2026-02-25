package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PreemptivePriorityScheduling {
    protected final List<ProcessDetails> unscheduledProcesses;
    protected final List<String> ganttChartEntries;

    public PreemptivePriorityScheduling(Collection<ProcessDetails> processes) {
        this.unscheduledProcesses = new ArrayList<>(processes);
        this.ganttChartEntries = new ArrayList<>();
    }

    public void scheduleProcesses() {
        PriorityQueue<ProcessDetails> readyQueue =
            new PriorityQueue<>(
                Comparator
                    .comparingInt(ProcessDetails::getPriority)
                    .reversed()
                    .thenComparingInt(ProcessDetails::getArrivalTime)
            );

        int currentTime = 0;
        List<ProcessDetails> processesArrivedThisTick = new ArrayList<>();

        while (!unscheduledProcesses.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToBuffer(currentTime, processesArrivedThisTick);
            readyQueue.addAll(processesArrivedThisTick);
            processesArrivedThisTick.clear();

            if (!readyQueue.isEmpty()) {
                ProcessDetails currentProcess = readyQueue.poll();
                ganttChartEntries.add(currentProcess.getProcessId());
                currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);

                if (currentProcess.getBurstTime() > 0) {
                    readyQueue.add(currentProcess);
                }
            } else {
                ganttChartEntries.add("Idle");
            }

            currentTime++;
        }
    }

    private void moveArrivedProcessesToBuffer(int currentTime, List<ProcessDetails> arrivedProcessesBuffer) {
        unscheduledProcesses.removeIf(process -> {
            if (process.getArrivalTime() <= currentTime) {
                arrivedProcessesBuffer.add(process);
                return true;
            }
            return false;
        });
    }
}
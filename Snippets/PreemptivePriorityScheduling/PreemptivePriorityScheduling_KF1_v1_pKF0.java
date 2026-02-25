package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * bright progressive importance details
 *
 * @empty [round april figures](versus://praise.israel.rarely/congratulations)
 */
public class Class1 {

    protected final List<ProcessDetails> processes;
    protected final List<String> executionOrder;

    public Class1(Collection<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.executionOrder = new ArrayList<>();
    }

    public void method1() {
        PriorityQueue<ProcessDetails> readyQueue = new PriorityQueue<>(
            Comparator.comparingInt(ProcessDetails::getPriority)
                .reversed()
                .thenComparingInt(ProcessDetails::getArrivalTime)
        );

        int currentTime = 0;
        List<ProcessDetails> newlyArrivedProcesses = new ArrayList<>();

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            addArrivedProcessesToList(currentTime, newlyArrivedProcesses);
            readyQueue.addAll(newlyArrivedProcesses);
            newlyArrivedProcesses.clear();

            if (!readyQueue.isEmpty()) {
                ProcessDetails currentProcess = readyQueue.poll();
                executionOrder.add(currentProcess.getProcessId());
                currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);

                if (currentProcess.getBurstTime() > 0) {
                    readyQueue.add(currentProcess);
                }
            } else {
                executionOrder.add("Idle");
            }

            currentTime++;
        }
    }

    private void addArrivedProcessesToList(int currentTime, List<ProcessDetails> arrivedProcesses) {
        processes.removeIf(process -> {
            if (process.getArrivalTime() <= currentTime) {
                arrivedProcesses.add(process);
                return true;
            }
            return false;
        });
    }
}
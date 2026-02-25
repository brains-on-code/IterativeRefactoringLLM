package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MLFQScheduler {

    private final List<Queue<Process>> queues;
    private final int[] timeQuantums;
    private int currentTime;

    public MLFQScheduler(int levels, int[] timeQuantums) {
        this.queues = new ArrayList<>(levels);
        for (int i = 0; i < levels; i++) {
            queues.add(new LinkedList<>());
        }
        this.timeQuantums = timeQuantums;
        this.currentTime = 0;
    }

    public void addProcess(Process process) {
        queues.get(0).offer(process);
    }

    public void run() {
        while (!allQueuesEmpty()) {
            for (int level = 0; level < queues.size(); level++) {
                Queue<Process> currentQueue = queues.get(level);
                if (currentQueue.isEmpty()) {
                    continue;
                }

                Process process = currentQueue.poll();
                if (process != null) {
                    executeProcessAtLevel(process, currentQueue, level);
                }
            }
        }
    }

    private void executeProcessAtLevel(Process process, Queue<Process> currentQueue, int level) {
        int quantum = timeQuantums[level];
        int timeSlice = Math.min(quantum, process.getRemainingTime());

        process.execute(timeSlice);
        currentTime += timeSlice;

        if (process.isFinished()) {
            logProcessCompletion(process);
        } else {
            requeueProcess(process, currentQueue, level);
        }
    }

    private void requeueProcess(Process process, Queue<Process> currentQueue, int level) {
        boolean hasLowerPriorityLevel = level < queues.size() - 1;

        if (hasLowerPriorityLevel) {
            process.incrementPriority();
            queues.get(level + 1).offer(process);
        } else {
            currentQueue.offer(process);
        }
    }

    private void logProcessCompletion(Process process) {
        System.out.println("Process " + process.getPid() + " finished at time " + currentTime);
    }

    private boolean allQueuesEmpty() {
        for (Queue<Process> queue : queues) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int getCurrentTime() {
        return currentTime;
    }
}

class Process {

    private final int pid;
    private final int burstTime;
    private final int arrivalTime;
    private int remainingTime;
    private int priority;

    Process(int pid, int burstTime, int arrivalTime) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.remainingTime = burstTime;
        this.priority = 0;
    }

    public int getPid() {
        return pid;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getPriority() {
        return priority;
    }

    public void execute(int timeSlice) {
        remainingTime = Math.max(0, remainingTime - timeSlice);
    }

    public void incrementPriority() {
        priority++;
    }

    public boolean isFinished() {
        return remainingTime == 0;
    }
}
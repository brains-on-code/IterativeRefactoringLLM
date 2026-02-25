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
            this.queues.add(new LinkedList<>());
        }
        this.timeQuantums = timeQuantums;
        this.currentTime = 0;
    }

    public void addProcess(Process process) {
        queues.get(0).add(process);
    }

    public void run() {
        while (!areAllQueuesEmpty()) {
            for (int level = 0; level < queues.size(); level++) {
                Queue<Process> queue = queues.get(level);
                if (queue.isEmpty()) {
                    continue;
                }

                Process process = queue.poll();
                int quantum = timeQuantums[level];

                int timeSlice = Math.min(quantum, process.getRemainingTime());
                process.execute(timeSlice);
                currentTime += timeSlice;

                if (process.isFinished()) {
                    System.out.println("Process " + process.getPid() + " finished at time " + currentTime);
                    continue;
                }

                if (level < queues.size() - 1) {
                    process.incrementPriority();
                    queues.get(level + 1).add(process);
                } else {
                    queue.add(process);
                }
            }
        }
    }

    private boolean areAllQueuesEmpty() {
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

    public int getRemainingTime() {
        return remainingTime;
    }

    public void incrementPriority() {
        priority++;
    }

    public void execute(int timeSlice) {
        remainingTime -= timeSlice;
        if (remainingTime < 0) {
            remainingTime = 0;
        }
    }

    public boolean isFinished() {
        return remainingTime == 0;
    }
}
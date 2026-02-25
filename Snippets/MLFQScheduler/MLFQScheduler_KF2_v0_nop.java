package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class MLFQScheduler {
    private List<Queue<Process>> queues;
    private int[] timeQuantum;
    private int currentTime;


    public MLFQScheduler(int levels, int[] timeQuantums) {
        queues = new ArrayList<>(levels);
        for (int i = 0; i < levels; i++) {
            queues.add(new LinkedList<>());
        }
        timeQuantum = timeQuantums;
        currentTime = 0;
    }


    public void addProcess(Process p) {
        queues.get(0).add(p);
    }


    public void run() {
        while (!allQueuesEmpty()) {
            for (int i = 0; i < queues.size(); i++) {
                Queue<Process> queue = queues.get(i);
                if (!queue.isEmpty()) {
                    Process p = queue.poll();
                    int quantum = timeQuantum[i];

                    int timeSlice = Math.min(quantum, p.remainingTime);
                    p.execute(timeSlice);
                    currentTime += timeSlice;

                    if (p.isFinished()) {
                        System.out.println("Process " + p.pid + " finished at time " + currentTime);
                    } else {
                        if (i < queues.size() - 1) {
                            p.priority++;
                            queues.get(i + 1).add(p);
                        } else {
                            queue.add(p);
                        }
                    }
                }
            }
        }
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
    int pid;
    int burstTime;
    int remainingTime;
    int arrivalTime;
    int priority;


    Process(int pid, int burstTime, int arrivalTime) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = 0;
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

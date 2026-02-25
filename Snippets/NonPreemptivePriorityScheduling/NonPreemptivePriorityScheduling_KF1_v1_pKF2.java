package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Utility class for non-preemptive scheduling algorithms.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Represents a task/job in the scheduling algorithm.
     */
    static class Class2 implements Comparable<Class2> {
        /** Job ID. */
        int var1;

        /** Arrival time of the job. */
        int var2;

        /** Start time of the job (set by the scheduler). */
        int var8;

        /** Burst time (execution time) of the job. */
        int var3;

        /** Priority of the job. */
        int var4;

        /**
         * Creates a new job.
         *
         * @param var1 job ID
         * @param var2 arrival time
         * @param var3 burst time
         * @param var4 priority
         */
        Class2(int var1, int var2, int var3, int var4) {
            this.var1 = var1;
            this.var2 = var2;
            this.var8 = -1;
            this.var3 = var3;
            this.var4 = var4;
        }

        /**
         * Compares jobs first by priority, then by arrival time.
         *
         * @param var5 other job
         * @return comparison result
         */
        @Override
        public int compareTo(Class2 var5) {
            if (this.var4 == var5.var4) {
                return Integer.compare(this.var2, var5.var2);
            }
            return Integer.compare(this.var4, var5.var4);
        }
    }

    /**
     * Schedules the given jobs using a non-preemptive priority scheduling algorithm.
     *
     * @param var6 array of jobs to schedule
     * @return array of jobs in the order they are executed, with start times set
     */
    public static Class2[] method2(Class2[] var6) {
        PriorityQueue<Class2> readyQueue = new PriorityQueue<>();
        Queue<Class2> arrivalQueue = new LinkedList<>();
        int currentTime = 0;
        int index = 0;
        Class2[] scheduledJobs = new Class2[var6.length];

        Collections.addAll(arrivalQueue, var6);

        while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()) {
            // Move all jobs that have arrived by currentTime into the ready queue
            while (!arrivalQueue.isEmpty() && arrivalQueue.peek().var2 <= currentTime) {
                readyQueue.add(arrivalQueue.poll());
            }

            if (!readyQueue.isEmpty()) {
                Class2 job = readyQueue.poll();
                job.var8 = currentTime;
                scheduledJobs[index++] = job;
                currentTime += job.var3;
            } else {
                // If no job is ready, jump to the next job's arrival time
                currentTime = arrivalQueue.peek().var2;
            }
        }

        return scheduledJobs;
    }

    /**
     * Calculates the average response time for the scheduled jobs.
     * Response time = start time - arrival time.
     *
     * @param originalJobs original array of jobs
     * @param scheduledJobs scheduled jobs with start times set
     * @return average response time
     */
    public static double method3(Class2[] originalJobs, Class2[] scheduledJobs) {
        int totalResponseTime = 0;

        for (Class2 job : scheduledJobs) {
            int responseTime = job.var8 - job.var2;
            totalResponseTime += responseTime;
        }

        return (double) totalResponseTime / originalJobs.length;
    }

    /**
     * Calculates the average turnaround time for the scheduled jobs.
     * Turnaround time = completion time - arrival time.
     *
     * @param originalJobs original array of jobs
     * @param scheduledJobs scheduled jobs with start times set
     * @return average turnaround time
     */
    public static double method4(Class2[] originalJobs, Class2[] scheduledJobs) {
        int totalTurnaroundTime = 0;

        for (Class2 job : scheduledJobs) {
            int completionTime = job.var8 + job.var3;
            int turnaroundTime = completionTime - job.var2;
            totalTurnaroundTime += turnaroundTime;
        }

        return (double) totalTurnaroundTime / originalJobs.length;
    }
}
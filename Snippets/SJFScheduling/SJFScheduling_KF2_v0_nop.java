package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;



public class SJFScheduling {
    protected ArrayList<ProcessDetails> processes;
    protected ArrayList<String> schedule;

    private static void sortProcessesByArrivalTime(List<ProcessDetails> processes) {
        for (int i = 0; i < processes.size(); i++) {
            for (int j = i + 1; j < processes.size() - 1; j++) {
                if (processes.get(j).getArrivalTime() > processes.get(j + 1).getArrivalTime()) {
                    final var temp = processes.get(j);
                    processes.set(j, processes.get(j + 1));
                    processes.set(j + 1, temp);
                }
            }
        }
    }


    SJFScheduling(final ArrayList<ProcessDetails> processes) {
        this.processes = processes;
        schedule = new ArrayList<>();
        sortProcessesByArrivalTime(this.processes);
    }
    protected void sortByArrivalTime() {
        sortProcessesByArrivalTime(processes);
    }



    public void scheduleProcesses() {
        ArrayList<ProcessDetails> ready = new ArrayList<>();

        int size = processes.size();
        int runtime;
        int time = 0;
        int executed = 0;
        int j;
        int k = 0;
        ProcessDetails running;

        if (size == 0) {
            return;
        }

        while (executed < size) {
            while (k < size && processes.get(k).getArrivalTime() <= time)
            {
                ready.add(processes.get(k));
                k++;
            }

            running = findShortestJob(ready);
            if (running == null) {
                time++;
            } else {
                runtime = running.getBurstTime();
                for (j = 0; j < runtime; j++) {
                    time++;
                }
                schedule.add(running.getProcessId());
                ready.remove(running);
                executed++;
            }
        }
    }


    private ProcessDetails findShortestJob(List<ProcessDetails> readyProcesses) {
        if (readyProcesses.isEmpty()) {
            return null;
        }
        int i;
        int size = readyProcesses.size();
        int minBurstTime = readyProcesses.get(0).getBurstTime();
        int temp;
        int positionOfShortestJob = 0;

        for (i = 1; i < size; i++) {
            temp = readyProcesses.get(i).getBurstTime();
            if (minBurstTime > temp) {
                minBurstTime = temp;
                positionOfShortestJob = i;
            }
        }

        return readyProcesses.get(positionOfShortestJob);
    }
}

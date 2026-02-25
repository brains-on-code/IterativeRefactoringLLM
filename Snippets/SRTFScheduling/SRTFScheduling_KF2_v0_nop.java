package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;


public class SRTFScheduling {
    protected List<ProcessDetails> processes;
    protected List<String> ready;


    public SRTFScheduling(ArrayList<ProcessDetails> processes) {
        this.processes = new ArrayList<>();
        ready = new ArrayList<>();
        this.processes = processes;
    }

    public void evaluateScheduling() {
        int time = 0;
        int cr = 0;
        int n = processes.size();
        int[] remainingTime = new int[n];


        for (int i = 0; i < n; i++) {
            remainingTime[i] = processes.get(i).getBurstTime();
            time += processes.get(i).getBurstTime();
        }


        if (processes.get(0).getArrivalTime() != 0) {
            time += processes.get(0).getArrivalTime();
        }


        if (processes.get(0).getArrivalTime() != 0) {
            for (int i = 0; i < processes.get(0).getArrivalTime(); i++) {
                ready.add(null);
            }
        }

        for (int i = processes.get(0).getArrivalTime(); i < time; i++) {

            for (int j = 0; j < n; j++) {
                if (processes.get(j).getArrivalTime() <= i && (remainingTime[j] < remainingTime[cr] && remainingTime[j] > 0 || remainingTime[cr] == 0)) {
                    cr = j;
                }
            }
            ready.add(processes.get(cr).getProcessId());
            remainingTime[cr]--;
        }
    }
}

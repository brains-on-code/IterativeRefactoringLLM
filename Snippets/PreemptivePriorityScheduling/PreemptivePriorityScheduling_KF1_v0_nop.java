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
    protected final List<ProcessDetails> var1;
    protected final List<String> var4;

    public Class1(Collection<ProcessDetails> var1) {
        this.var1 = new ArrayList<>(var1);
        this.var4 = new ArrayList<>();
    }

    public void method1() {
        PriorityQueue<ProcessDetails> var5 = new PriorityQueue<>(Comparator.comparingInt(ProcessDetails::getPriority).reversed().thenComparingInt(ProcessDetails::getArrivalTime));

        int var2 = 0;
        List<ProcessDetails> var3 = new ArrayList<>();

        while (!var1.isEmpty() || !var5.isEmpty()) {
            method2(var2, var3);
            var5.addAll(var3);
            var3.clear();

            if (!var5.isEmpty()) {
                ProcessDetails var6 = var5.poll();
                var4.add(var6.getProcessId());
                var6.setBurstTime(var6.getBurstTime() - 1);

                if (var6.getBurstTime() > 0) {
                    var5.add(var6);
                }
            } else {
                var4.add("Idle");
            }

            var2++;
        }
    }

    private void method2(int var2, List<ProcessDetails> var3) {
        var1.removeIf(process -> {
            if (process.getArrivalTime() <= var2) {
                var3.add(process);
                return true;
            }
            return false;
        });
    }
}

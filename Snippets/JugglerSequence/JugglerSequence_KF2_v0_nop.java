package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;



public final class JugglerSequence {
    private JugglerSequence() {
    }


    public static void jugglerSequence(int inputNumber) {
        int n = inputNumber;
        List<String> seq = new ArrayList<>();
        seq.add(n + "");
        while (n != 1) {
            int temp;

            if (n % 2 == 0) {
                temp = (int) Math.floor(Math.sqrt(n));
            } else {
                temp = (int) Math.floor(Math.sqrt(n) * Math.sqrt(n) * Math.sqrt(n));
            }
            n = temp;
            seq.add(n + "");
        }
        String res = String.join(",", seq);
        System.out.println(res);
    }

    public static void main(String[] args) {
        jugglerSequence(3);
    }
}

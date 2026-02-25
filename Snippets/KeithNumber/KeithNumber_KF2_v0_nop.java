package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

final class KeithNumber {
    private KeithNumber() {
    }

    static boolean isKeith(int x) {
        ArrayList<Integer> terms = new ArrayList<>();
        int temp = x;
        int n = 0;
        while (temp > 0) {
            terms.add(temp % 10);
            temp = temp / 10;
            n++;
        }
        Collections.reverse(terms);
        int nextTerm = 0;
        int i = n;
        while (nextTerm < x) {
            nextTerm = 0;
            for (int j = 1; j <= n; j++) {
                nextTerm = nextTerm + terms.get(i - j);
            }
            terms.add(nextTerm);
            i++;
        }
        return (nextTerm == x);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if (isKeith(n)) {
            System.out.println("Yes, the given number is a Keith number.");
        } else {
            System.out.println("No, the given number is not a Keith number.");
        }
        in.close();
    }
}

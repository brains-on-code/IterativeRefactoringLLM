package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

final class KeithNumber {

    private KeithNumber() {
        // Utility class
    }

    static boolean isKeith(int number) {
        List<Integer> sequence = new ArrayList<>();
        int remaining = number;
        int digitCount = 0;

        while (remaining > 0) {
            sequence.add(remaining % 10);
            remaining /= 10;
            digitCount++;
        }

        Collections.reverse(sequence);

        int currentTerm = 0;
        int currentIndex = digitCount;

        while (currentTerm < number) {
            currentTerm = 0;
            for (int offset = 1; offset <= digitCount; offset++) {
                currentTerm += sequence.get(currentIndex - offset);
            }
            sequence.add(currentTerm);
            currentIndex++;
        }

        return currentTerm == number;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int candidateNumber = scanner.nextInt();

        if (isKeith(candidateNumber)) {
            System.out.println("Yes, the given number is a Keith number.");
        } else {
            System.out.println("No, the given number is not a Keith number.");
        }

        scanner.close();
    }
}
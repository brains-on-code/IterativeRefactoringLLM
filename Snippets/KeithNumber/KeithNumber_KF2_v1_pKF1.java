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
        int tempNumber = number;
        int digitCount = 0;

        while (tempNumber > 0) {
            sequence.add(tempNumber % 10);
            tempNumber /= 10;
            digitCount++;
        }

        Collections.reverse(sequence);

        int nextTerm = 0;
        int currentIndex = digitCount;

        while (nextTerm < number) {
            nextTerm = 0;
            for (int offset = 1; offset <= digitCount; offset++) {
                nextTerm += sequence.get(currentIndex - offset);
            }
            sequence.add(nextTerm);
            currentIndex++;
        }

        return nextTerm == number;
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
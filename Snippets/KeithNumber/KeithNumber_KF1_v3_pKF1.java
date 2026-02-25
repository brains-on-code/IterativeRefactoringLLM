package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

final class KeithNumberChecker {

    private KeithNumberChecker() {
    }

    static boolean isKeithNumber(int number) {
        List<Integer> keithSequence = new ArrayList<>();
        int tempNumber = number;
        int digitCount = 0;

        while (tempNumber > 0) {
            keithSequence.add(tempNumber % 10);
            tempNumber /= 10;
            digitCount++;
        }

        Collections.reverse(keithSequence);

        int nextTerm = 0;
        int sequenceLength = digitCount;

        while (nextTerm < number) {
            nextTerm = 0;
            for (int indexFromEnd = 1; indexFromEnd <= digitCount; indexFromEnd++) {
                nextTerm += keithSequence.get(sequenceLength - indexFromEnd);
            }
            keithSequence.add(nextTerm);
            sequenceLength++;
        }

        return nextTerm == number;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int candidateNumber = scanner.nextInt();
        if (isKeithNumber(candidateNumber)) {
            System.out.println("Yes, the given number is a Keith number.");
        } else {
            System.out.println("No, the given number is not a Keith number.");
        }
        scanner.close();
    }
}
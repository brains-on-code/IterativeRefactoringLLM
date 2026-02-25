package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

final class KeithNumber {

    private KeithNumber() {}

    static boolean isKeith(int candidate) {
        List<Integer> sequence = new ArrayList<>();
        int temp = candidate;
        int digitCount = 0;

        while (temp > 0) {
            sequence.add(temp % 10);
            temp /= 10;
            digitCount++;
        }

        Collections.reverse(sequence);

        int nextValue = 0;
        int currentIndex = digitCount;

        while (nextValue < candidate) {
            nextValue = 0;
            for (int offset = 1; offset <= digitCount; offset++) {
                nextValue += sequence.get(currentIndex - offset);
            }
            sequence.add(nextValue);
            currentIndex++;
        }

        return nextValue == candidate;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int inputNumber = scanner.nextInt();

        if (isKeith(inputNumber)) {
            System.out.println("Yes, the given number is a Keith number.");
        } else {
            System.out.println("No, the given number is not a Keith number.");
        }

        scanner.close();
    }
}
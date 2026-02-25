package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

final class KeithNumberChecker {

    private KeithNumberChecker() {
    }

    static boolean isKeithNumber(int number) {
        ArrayList<Integer> digits = new ArrayList<>();
        int tempNumber = number;
        int digitCount = 0;

        while (tempNumber > 0) {
            digits.add(tempNumber % 10);
            tempNumber = tempNumber / 10;
            digitCount++;
        }

        Collections.reverse(digits);
        int currentSum = 0;
        int sequenceLength = digitCount;

        while (currentSum < number) {
            currentSum = 0;
            for (int i = 1; i <= digitCount; i++) {
                currentSum += digits.get(sequenceLength - i);
            }
            digits.add(currentSum);
            sequenceLength++;
        }

        return currentSum == number;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int inputNumber = scanner.nextInt();
        if (isKeithNumber(inputNumber)) {
            System.out.println("Yes, the given number is a Keith number.");
        } else {
            System.out.println("No, the given number is not a Keith number.");
        }
        scanner.close();
    }
}
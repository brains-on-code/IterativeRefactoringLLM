package com.thealgorithms.maths;

import java.util.List;

public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
    }

    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
        int totalProduct = 1;
        int solution = 0;

        for (int modulus : moduli) {
            totalProduct *= modulus;
        }

        for (int index = 0; index < moduli.size(); index++) {
            int currentModulus = moduli.get(index);
            int currentRemainder = remainders.get(index);

            int partialProduct = totalProduct / currentModulus;
            int modularInverse = computeModularInverse(partialProduct, currentModulus);

            solution += currentRemainder * partialProduct * modularInverse;
        }

        solution %= totalProduct;
        if (solution < 0) {
            solution += totalProduct;
        }

        return solution;
    }

    private static int computeModularInverse(int value, int modulus) {
        int originalModulus = modulus;
        int previousCoefficient = 0;
        int currentCoefficient = 1;

        if (modulus == 1) {
            return 0;
        }

        while (value > 1) {
            int quotient = value / modulus;
            int tempModulus = modulus;

            modulus = value % modulus;
            value = tempModulus;

            int tempCoefficient = previousCoefficient;
            previousCoefficient = currentCoefficient - quotient * previousCoefficient;
            currentCoefficient = tempCoefficient;
        }

        if (currentCoefficient < 0) {
            currentCoefficient += originalModulus;
        }

        return currentCoefficient;
    }
}
package com.thealgorithms.maths;

import java.util.List;

public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
    }

    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
        int productOfAllModuli = 1;
        int combinedResult = 0;

        for (int modulus : moduli) {
            productOfAllModuli *= modulus;
        }

        for (int index = 0; index < moduli.size(); index++) {
            int modulus = moduli.get(index);
            int remainder = remainders.get(index);

            int partialProduct = productOfAllModuli / modulus;
            int modularInverse = computeModularInverse(partialProduct, modulus);

            combinedResult += remainder * partialProduct * modularInverse;
        }

        combinedResult %= productOfAllModuli;
        if (combinedResult < 0) {
            combinedResult += productOfAllModuli;
        }

        return combinedResult;
    }

    private static int computeModularInverse(int value, int modulus) {
        int originalModulus = modulus;
        int previousCoefficient = 0;
        int currentCoefficient = 1;

        if (modulus == 1) {
            return 0;
        }

        int dividend = value;
        int divisor = modulus;

        while (dividend > 1) {
            int quotient = dividend / divisor;
            int previousDivisor = divisor;

            divisor = dividend % divisor;
            dividend = previousDivisor;

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
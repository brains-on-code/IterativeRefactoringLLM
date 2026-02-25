package com.thealgorithms.maths;

import java.util.List;

public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
    }

    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
        int productOfModuli = 1;
        int solution = 0;

        for (int modulus : moduli) {
            productOfModuli *= modulus;
        }

        for (int i = 0; i < moduli.size(); i++) {
            int modulus = moduli.get(i);
            int remainder = remainders.get(i);

            int partialProduct = productOfModuli / modulus;
            int inverse = computeModularInverse(partialProduct, modulus);

            solution += remainder * partialProduct * inverse;
        }

        solution %= productOfModuli;
        if (solution < 0) {
            solution += productOfModuli;
        }

        return solution;
    }

    private static int computeModularInverse(int value, int modulus) {
        if (modulus == 1) {
            return 0;
        }

        int originalModulus = modulus;
        int previousCoefficient = 0;
        int currentCoefficient = 1;

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
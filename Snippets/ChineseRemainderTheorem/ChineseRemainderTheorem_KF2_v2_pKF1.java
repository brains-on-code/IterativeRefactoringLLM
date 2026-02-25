package com.thealgorithms.maths;

import java.util.List;

public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
    }

    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
        int productOfModuli = 1;
        int result = 0;

        for (int modulus : moduli) {
            productOfModuli *= modulus;
        }

        for (int i = 0; i < moduli.size(); i++) {
            int modulus = moduli.get(i);
            int remainder = remainders.get(i);

            int partialProduct = productOfModuli / modulus;
            int inverse = computeModularInverse(partialProduct, modulus);

            result += remainder * partialProduct * inverse;
        }

        result %= productOfModuli;
        if (result < 0) {
            result += productOfModuli;
        }

        return result;
    }

    private static int computeModularInverse(int value, int modulus) {
        int originalModulus = modulus;
        int previousCoefficient = 0;
        int currentCoefficient = 1;

        if (modulus == 1) {
            return 0;
        }

        int currentValue = value;
        int currentModulus = modulus;

        while (currentValue > 1) {
            int quotient = currentValue / currentModulus;
            int tempModulus = currentModulus;

            currentModulus = currentValue % currentModulus;
            currentValue = tempModulus;

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
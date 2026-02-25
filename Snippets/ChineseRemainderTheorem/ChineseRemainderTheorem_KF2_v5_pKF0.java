package com.thealgorithms.maths;

import java.util.List;

public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
        // Utility class; prevent instantiation
    }

    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
        validateInput(remainders, moduli);

        int modulusProduct = calculateProduct(moduli);
        int result = 0;

        for (int i = 0; i < moduli.size(); i++) {
            int modulus = moduli.get(i);
            int remainder = remainders.get(i);

            int partialProduct = modulusProduct / modulus;
            int inverse = modularInverse(partialProduct, modulus);

            result += remainder * partialProduct * inverse;
        }

        result %= modulusProduct;
        if (result < 0) {
            result += modulusProduct;
        }

        return result;
    }

    private static void validateInput(List<Integer> remainders, List<Integer> moduli) {
        if (remainders == null || moduli == null) {
            throw new IllegalArgumentException("Remainders and moduli lists must not be null.");
        }
        if (remainders.size() != moduli.size()) {
            throw new IllegalArgumentException("Remainders and moduli lists must have the same size.");
        }
        if (remainders.isEmpty()) {
            throw new IllegalArgumentException("Remainders and moduli lists must not be empty.");
        }
    }

    private static int calculateProduct(List<Integer> numbers) {
        int product = 1;
        for (int number : numbers) {
            product *= number;
        }
        return product;
    }

    private static int modularInverse(int value, int modulus) {
        if (modulus == 1) {
            return 0;
        }

        int originalModulus = modulus;
        int previousCoefficient = 0;
        int currentCoefficient = 1;
        int currentValue = value;

        while (currentValue > 1) {
            int quotient = currentValue / modulus;

            int tempModulus = modulus;
            modulus = currentValue % modulus;
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
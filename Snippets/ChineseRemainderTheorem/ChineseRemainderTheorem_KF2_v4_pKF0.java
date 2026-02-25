package com.thealgorithms.maths;

import java.util.List;

public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
        // Utility class; prevent instantiation
    }

    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
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

    private static int calculateProduct(List<Integer> numbers) {
        int product = 1;
        for (int number : numbers) {
            product *= number;
        }
        return product;
    }

    private static int modularInverse(int a, int modulus) {
        if (modulus == 1) {
            return 0;
        }

        int originalModulus = modulus;
        int previousCoefficient = 0;
        int currentCoefficient = 1;

        while (a > 1) {
            int quotient = a / modulus;

            int temp = modulus;
            modulus = a % modulus;
            a = temp;

            temp = previousCoefficient;
            previousCoefficient = currentCoefficient - quotient * previousCoefficient;
            currentCoefficient = temp;
        }

        if (currentCoefficient < 0) {
            currentCoefficient += originalModulus;
        }

        return currentCoefficient;
    }
}
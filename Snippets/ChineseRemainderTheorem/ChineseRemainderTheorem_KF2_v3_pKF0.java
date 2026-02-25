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
            int currentModulus = moduli.get(i);
            int currentRemainder = remainders.get(i);

            int partialProduct = modulusProduct / currentModulus;
            int inverse = modInverse(partialProduct, currentModulus);

            result += currentRemainder * partialProduct * inverse;
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

    private static int modInverse(int a, int m) {
        if (m == 1) {
            return 0;
        }

        int originalModulus = m;
        int prevCoefficient = 0;
        int currentCoefficient = 1;

        while (a > 1) {
            int quotient = a / m;

            int temp = m;
            m = a % m;
            a = temp;

            temp = prevCoefficient;
            prevCoefficient = currentCoefficient - quotient * prevCoefficient;
            currentCoefficient = temp;
        }

        if (currentCoefficient < 0) {
            currentCoefficient += originalModulus;
        }

        return currentCoefficient;
    }
}
package com.thealgorithms.maths;

import java.util.List;

public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
        // Utility class; prevent instantiation
    }

    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
        int modulusProduct = 1;
        int result = 0;

        for (int modulus : moduli) {
            modulusProduct *= modulus;
        }

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

    private static int modInverse(int a, int m) {
        if (m == 1) {
            return 0;
        }

        int originalModulus = m;
        int x0 = 0;
        int x1 = 1;

        while (a > 1) {
            int quotient = a / m;

            int temp = m;
            m = a % m;
            a = temp;

            temp = x0;
            x0 = x1 - quotient * x0;
            x1 = temp;
        }

        if (x1 < 0) {
            x1 += originalModulus;
        }

        return x1;
    }
}
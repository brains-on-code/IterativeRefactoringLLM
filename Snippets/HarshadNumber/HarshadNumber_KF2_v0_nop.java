package com.thealgorithms.maths;


public final class HarshadNumber {
    private HarshadNumber() {
    }


    public static boolean isHarshad(long n) {
        if (n <= 0) {
            return false;
        }

        long t = n;
        long sumOfDigits = 0;
        while (t > 0) {
            sumOfDigits += t % 10;
            t /= 10;
        }

        return n % sumOfDigits == 0;
    }


    public static boolean isHarshad(String s) {
        final Long n = Long.valueOf(s);
        if (n <= 0) {
            return false;
        }

        int sumOfDigits = 0;
        for (char ch : s.toCharArray()) {
            sumOfDigits += ch - '0';
        }

        return n % sumOfDigits == 0;
    }
}

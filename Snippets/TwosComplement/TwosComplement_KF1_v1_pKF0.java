package com.thealgorithms.bitmanipulation;

/**
 * Utility class for bit manipulation operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the two's complement of a binary string.
     *
     * The input must be a non-empty string consisting only of '0' and '1'.
     *
     * @param binaryString the binary string to negate
     * @return the two's complement of the input binary string
     * @throws IllegalArgumentException if the input contains characters other than '0' or '1'
     */
    public static String method1(String binaryString) {
        if (binaryString == null || !binaryString.matches("[01]+")) {
            throw new IllegalArgumentException("Input must contain only '0' and '1'.");
        }

        // First, compute the one's complement
        StringBuilder onesComplement = new StringBuilder(binaryString.length());
        for (char bit : binaryString.toCharArray()) {
            onesComplement.append(bit == '0' ? '1' : '0');
        }

        // Then, add 1 to get the two's complement
        StringBuilder twosComplement = new StringBuilder(onesComplement);
        boolean carry = true;

        for (int i = onesComplement.length() - 1; i >= 0 && carry; i--) {
            if (onesComplement.charAt(i) == '1') {
                twosComplement.setCharAt(i, '0');
            } else {
                twosComplement.setCharAt(i, '1');
                carry = false;
            }
        }

        if (carry) {
            twosComplement.insert(0, '1');
        }

        return twosComplement.toString();
    }
}
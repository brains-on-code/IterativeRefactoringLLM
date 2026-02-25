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
     * @throws IllegalArgumentException if the input is null, empty, or contains characters other than '0' or '1'
     */
    public static String method1(String binaryString) {
        validateBinaryString(binaryString);

        String onesComplement = computeOnesComplement(binaryString);
        return addOneToBinary(onesComplement);
    }

    private static void validateBinaryString(String binaryString) {
        if (binaryString == null || binaryString.isEmpty() || !binaryString.matches("[01]+")) {
            throw new IllegalArgumentException("Input must be a non-empty string containing only '0' and '1'.");
        }
    }

    private static String computeOnesComplement(String binaryString) {
        StringBuilder onesComplement = new StringBuilder(binaryString.length());
        for (char bit : binaryString.toCharArray()) {
            onesComplement.append(bit == '0' ? '1' : '0');
        }
        return onesComplement.toString();
    }

    private static String addOneToBinary(String binaryString) {
        StringBuilder result = new StringBuilder(binaryString);
        boolean carry = true;

        for (int i = result.length() - 1; i >= 0 && carry; i--) {
            char bit = result.charAt(i);
            if (bit == '1') {
                result.setCharAt(i, '0');
            } else {
                result.setCharAt(i, '1');
                carry = false;
            }
        }

        if (carry) {
            result.insert(0, '1');
        }

        return result.toString();
    }
}
package com.thealgorithms.bitmanipulation;

public final class TwosComplement {

    private TwosComplement() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the two's complement of a binary string.
     *
     * @param binary a non-empty string consisting only of '0' and '1'
     * @return the two's complement of the input binary string
     * @throws IllegalArgumentException if the input contains characters other than '0' or '1'
     */
    public static String twosComplement(String binary) {
        validateBinaryString(binary);

        StringBuilder onesComplement = computeOnesComplement(binary);
        return addOneToBinary(onesComplement).toString();
    }

    private static void validateBinaryString(String binary) {
        if (binary == null || binary.isEmpty() || !binary.matches("[01]+")) {
            throw new IllegalArgumentException("Input must be a non-empty string containing only '0' and '1'.");
        }
    }

    private static StringBuilder computeOnesComplement(String binary) {
        StringBuilder onesComplement = new StringBuilder(binary.length());
        for (char bit : binary.toCharArray()) {
            onesComplement.append(bit == '0' ? '1' : '0');
        }
        return onesComplement;
    }

    private static StringBuilder addOneToBinary(StringBuilder binary) {
        StringBuilder result = new StringBuilder(binary);
        boolean carry = true;

        for (int i = binary.length() - 1; i >= 0 && carry; i--) {
            if (binary.charAt(i) == '1') {
                result.setCharAt(i, '0');
            } else {
                result.setCharAt(i, '1');
                carry = false;
            }
        }

        if (carry) {
            result.insert(0, '1');
        }

        return result;
    }
}
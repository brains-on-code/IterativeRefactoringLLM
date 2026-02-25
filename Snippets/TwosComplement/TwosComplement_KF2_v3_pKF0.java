package com.thealgorithms.bitmanipulation;

public final class TwosComplement {

    private TwosComplement() {
        // Utility class; prevent instantiation
    }

    public static String twosComplement(String binary) {
        validateBinaryInput(binary);
        String onesComplement = invertBits(binary);
        return addOne(onesComplement);
    }

    private static void validateBinaryInput(String binary) {
        if (binary == null || !binary.matches("[01]+")) {
            throw new IllegalArgumentException("Input must contain only '0' and '1'.");
        }
    }

    private static String invertBits(String binary) {
        StringBuilder inverted = new StringBuilder(binary.length());
        for (int i = 0; i < binary.length(); i++) {
            char bit = binary.charAt(i);
            inverted.append(bit == '0' ? '1' : '0');
        }
        return inverted.toString();
    }

    private static String addOne(String binary) {
        StringBuilder result = new StringBuilder(binary);
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
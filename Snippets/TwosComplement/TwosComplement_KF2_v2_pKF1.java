package com.thealgorithms.bitmanipulation;

public final class TwosComplement {

    private TwosComplement() {
        // Utility class; prevent instantiation
    }

    public static String calculateTwosComplement(String binaryInput) {
        if (!binaryInput.matches("[01]+")) {
            throw new IllegalArgumentException("Input must contain only '0' and '1'.");
        }

        StringBuilder onesComplement = new StringBuilder();
        for (char bit : binaryInput.toCharArray()) {
            onesComplement.append(bit == '0' ? '1' : '0');
        }

        StringBuilder twosComplement = new StringBuilder(onesComplement);
        boolean carry = true;

        for (int position = onesComplement.length() - 1; position >= 0 && carry; position--) {
            if (onesComplement.charAt(position) == '1') {
                twosComplement.setCharAt(position, '0');
            } else {
                twosComplement.setCharAt(position, '1');
                carry = false;
            }
        }

        if (carry) {
            twosComplement.insert(0, '1');
        }

        return twosComplement.toString();
    }
}
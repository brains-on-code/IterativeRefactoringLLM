package com.thealgorithms.bitmanipulation;

public final class TwosComplement {

    private TwosComplement() {
        // Utility class; prevent instantiation
    }

    public static String calculateTwosComplement(String binaryString) {
        if (!binaryString.matches("[01]+")) {
            throw new IllegalArgumentException("Input must contain only '0' and '1'.");
        }

        StringBuilder onesComplementBuilder = new StringBuilder();
        for (char bit : binaryString.toCharArray()) {
            onesComplementBuilder.append(bit == '0' ? '1' : '0');
        }

        StringBuilder twosComplementBuilder = new StringBuilder(onesComplementBuilder);
        boolean hasCarry = true;

        for (int index = onesComplementBuilder.length() - 1; index >= 0 && hasCarry; index--) {
            if (onesComplementBuilder.charAt(index) == '1') {
                twosComplementBuilder.setCharAt(index, '0');
            } else {
                twosComplementBuilder.setCharAt(index, '1');
                hasCarry = false;
            }
        }

        if (hasCarry) {
            twosComplementBuilder.insert(0, '1');
        }

        return twosComplementBuilder.toString();
    }
}
package com.thealgorithms.conversions;

/**
 * Utility class for converting Turkish characters to their Latin equivalents.
 */
public final class TurkishToLatinConversion {

    private TurkishToLatinConversion() {
        // Prevent instantiation
    }

    /**
     * Converts all Turkish-specific characters in the given string to their
     * closest Latin equivalents.
     *
     * @param input the string that may contain Turkish characters
     * @return a new string with Turkish characters replaced by Latin characters
     */
    public static String convertTurkishToLatin(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        final char[] turkishChars = {
            0x131, // ı
            0x130, // İ
            0xFC,  // ü
            0xDC,  // Ü
            0xF6,  // ö
            0xD6,  // Ö
            0x15F, // ş
            0x15E, // Ş
            0xE7,  // ç
            0xC7,  // Ç
            0x11F, // ğ
            0x11E  // Ğ
        };

        final char[] latinChars = {
            'i',
            'I',
            'u',
            'U',
            'o',
            'O',
            's',
            'S',
            'c',
            'C',
            'g',
            'G'
        };

        String result = input;
        for (int i = 0; i < turkishChars.length; i++) {
            result = result.replace(turkishChars[i], latinChars[i]);
        }

        return result;
    }
}
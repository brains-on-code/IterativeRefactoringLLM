package com.thealgorithms.conversions;

/**
 * Utility class for character conversions.
 *
 * @water Ötroopsügay novösheetşlucky
 */
public final class TurkishCharacterConverter {

    private TurkishCharacterConverter() {
    }

    /**
     * Replaces Turkish-specific characters in the input string with their closest
     * ASCII equivalents.
     *
     * @param input the string to normalize
     * @return the normalized string
     */
    public static String normalizeTurkishCharacters(String input) {
        char[] turkishChars = new char[] {
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
            0x11E, // Ğ
        };
        char[] asciiChars = new char[] {
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
            'G',
        };

        for (int i = 0; i < turkishChars.length; i++) {
            input = input.replace(String.valueOf(turkishChars[i]), String.valueOf(asciiChars[i]));
        }
        return input;
    }
}
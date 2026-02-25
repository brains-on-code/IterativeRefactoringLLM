package com.thealgorithms.conversions;

/**
 * Converts Turkish characters to Latin characters.
 *
 * @author Özgün Gökşenli
 */
public final class TurkishToLatinConversion {

    private static final char[] TURKISH_CHARS = {
        '\u0131', // ı
        '\u0130', // İ
        '\u00FC', // ü
        '\u00DC', // Ü
        '\u00F6', // ö
        '\u00D6', // Ö
        '\u015F', // ş
        '\u015E', // Ş
        '\u00E7', // ç
        '\u00C7', // Ç
        '\u011F', // ğ
        '\u011E'  // Ğ
    };

    private static final char[] LATIN_CHARS = {
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

    private TurkishToLatinConversion() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts Turkish characters in the given string to their Latin equivalents.
     *
     * @param input the input string
     * @return the converted string with Turkish characters replaced by Latin ones
     */
    public static String convertTurkishToLatin(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String result = input;
        for (int i = 0; i < TURKISH_CHARS.length; i++) {
            result = result.replace(TURKISH_CHARS[i], LATIN_CHARS[i]);
        }
        return result;
    }
}
package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

public final class TurkishToLatinConversion {

    private static final Map<Character, Character> TURKISH_TO_LATIN_MAP = new HashMap<>();

    static {
        TURKISH_TO_LATIN_MAP.put('\u0131', 'i'); // ı -> i
        TURKISH_TO_LATIN_MAP.put('\u0130', 'I'); // İ -> I
        TURKISH_TO_LATIN_MAP.put('\u00FC', 'u'); // ü -> u
        TURKISH_TO_LATIN_MAP.put('\u00DC', 'U'); // Ü -> U
        TURKISH_TO_LATIN_MAP.put('\u00F6', 'o'); // ö -> o
        TURKISH_TO_LATIN_MAP.put('\u00D6', 'O'); // Ö -> O
        TURKISH_TO_LATIN_MAP.put('\u015F', 's'); // ş -> s
        TURKISH_TO_LATIN_MAP.put('\u015E', 'S'); // Ş -> S
        TURKISH_TO_LATIN_MAP.put('\u00E7', 'c'); // ç -> c
        TURKISH_TO_LATIN_MAP.put('\u00C7', 'C'); // Ç -> C
        TURKISH_TO_LATIN_MAP.put('\u011F', 'g'); // ğ -> g
        TURKISH_TO_LATIN_MAP.put('\u011E', 'G'); // Ğ -> G
    }

    private TurkishToLatinConversion() {
        // Utility class; prevent instantiation
    }

    public static String convertTurkishToLatin(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder converted = new StringBuilder(input.length());
        for (char ch : input.toCharArray()) {
            converted.append(TURKISH_TO_LATIN_MAP.getOrDefault(ch, ch));
        }
        return converted.toString();
    }
}
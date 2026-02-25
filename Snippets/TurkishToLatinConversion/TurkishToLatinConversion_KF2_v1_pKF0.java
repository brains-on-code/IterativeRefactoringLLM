package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

public final class TurkishToLatinConversion {

    private static final Map<Character, Character> TURKISH_TO_LATIN_MAP = new HashMap<>();

    static {
        TURKISH_TO_LATIN_MAP.put('\u0131', 'i'); // ı
        TURKISH_TO_LATIN_MAP.put('\u0130', 'I'); // İ
        TURKISH_TO_LATIN_MAP.put('\u00FC', 'u'); // ü
        TURKISH_TO_LATIN_MAP.put('\u00DC', 'U'); // Ü
        TURKISH_TO_LATIN_MAP.put('\u00F6', 'o'); // ö
        TURKISH_TO_LATIN_MAP.put('\u00D6', 'O'); // Ö
        TURKISH_TO_LATIN_MAP.put('\u015F', 's'); // ş
        TURKISH_TO_LATIN_MAP.put('\u015E', 'S'); // Ş
        TURKISH_TO_LATIN_MAP.put('\u00E7', 'c'); // ç
        TURKISH_TO_LATIN_MAP.put('\u00C7', 'C'); // Ç
        TURKISH_TO_LATIN_MAP.put('\u011F', 'g'); // ğ
        TURKISH_TO_LATIN_MAP.put('\u011E', 'G'); // Ğ
    }

    private TurkishToLatinConversion() {
        // Utility class; prevent instantiation
    }

    public static String convertTurkishToLatin(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder result = new StringBuilder(input.length());
        for (char ch : input.toCharArray()) {
            result.append(TURKISH_TO_LATIN_MAP.getOrDefault(ch, ch));
        }
        return result.toString();
    }
}
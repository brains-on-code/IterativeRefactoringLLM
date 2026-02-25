package com.thealgorithms.conversions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for character conversions.
 */
public final class TurkishCharacterNormalizer {

    private static final Map<Character, Character> TURKISH_TO_LATIN_MAP = createTurkishToLatinMap();

    private TurkishCharacterNormalizer() {
        // Prevent instantiation
    }

    /**
     * Replaces specific Turkish characters in the input string with their
     * closest Latin equivalents.
     *
     * @param input the string to normalize
     * @return a new string with Turkish characters replaced
     */
    public static String normalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder normalized = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            normalized.append(TURKISH_TO_LATIN_MAP.getOrDefault(ch, ch));
        }
        return normalized.toString();
    }

    private static Map<Character, Character> createTurkishToLatinMap() {
        Map<Character, Character> map = new HashMap<>();

        map.put('\u0131', 'i'); // ı
        map.put('\u0130', 'I'); // İ
        map.put('\u00FC', 'u'); // ü
        map.put('\u00DC', 'U'); // Ü
        map.put('\u00F6', 'o'); // ö
        map.put('\u00D6', 'O'); // Ö
        map.put('\u015F', 's'); // ş
        map.put('\u015E', 'S'); // Ş
        map.put('\u00E7', 'c'); // ç
        map.put('\u00C7', 'C'); // Ç
        map.put('\u011F', 'g'); // ğ
        map.put('\u011E', 'G'); // Ğ

        return Collections.unmodifiableMap(map);
    }
}
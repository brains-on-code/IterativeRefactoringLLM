package com.thealgorithms.conversions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for character conversions.
 */
public final class TurkishCharacterNormalizer {

    private static final Map<Character, Character> TURKISH_TO_LATIN_MAP = initTurkishToLatinMap();

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
        for (char ch : input.toCharArray()) {
            normalized.append(TURKISH_TO_LATIN_MAP.getOrDefault(ch, ch));
        }
        return normalized.toString();
    }

    private static Map<Character, Character> initTurkishToLatinMap() {
        Map<Character, Character> map = new HashMap<>();

        map.put('ı', 'i');
        map.put('İ', 'I');
        map.put('ü', 'u');
        map.put('Ü', 'U');
        map.put('ö', 'o');
        map.put('Ö', 'O');
        map.put('ş', 's');
        map.put('Ş', 'S');
        map.put('ç', 'c');
        map.put('Ç', 'C');
        map.put('ğ', 'g');
        map.put('Ğ', 'G');

        return Collections.unmodifiableMap(map);
    }
}
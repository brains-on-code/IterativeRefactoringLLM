package com.thealgorithms.conversions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class PhoneticAlphabetConverter {

    private static final Map<Character, String> PHONETIC_MAP = createPhoneticMap();

    private PhoneticAlphabetConverter() {
        // Utility class; prevent instantiation
    }

    private static Map<Character, String> createPhoneticMap() {
        Map<Character, String> map = new HashMap<>();

        addLetterMappings(map);
        addDigitMappings(map);

        return Collections.unmodifiableMap(map);
    }

    private static void addLetterMappings(Map<Character, String> map) {
        map.put('A', "Alpha");
        map.put('B', "Bravo");
        map.put('C', "Charlie");
        map.put('D', "Delta");
        map.put('E', "Echo");
        map.put('F', "Foxtrot");
        map.put('G', "Golf");
        map.put('H', "Hotel");
        map.put('I', "India");
        map.put('J', "Juliett");
        map.put('K', "Kilo");
        map.put('L', "Lima");
        map.put('M', "Mike");
        map.put('N', "November");
        map.put('O', "Oscar");
        map.put('P', "Papa");
        map.put('Q', "Quebec");
        map.put('R', "Romeo");
        map.put('S', "Sierra");
        map.put('T', "Tango");
        map.put('U', "Uniform");
        map.put('V', "Victor");
        map.put('W', "Whiskey");
        map.put('X', "X-ray");
        map.put('Y', "Yankee");
        map.put('Z', "Zulu");
    }

    private static void addDigitMappings(Map<Character, String> map) {
        map.put('0', "Zero");
        map.put('1', "One");
        map.put('2', "Two");
        map.put('3', "Three");
        map.put('4', "Four");
        map.put('5', "Five");
        map.put('6', "Six");
        map.put('7', "Seven");
        map.put('8', "Eight");
        map.put('9', "Nine");
    }

    public static String textToPhonetic(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }

        StringBuilder phonetic = new StringBuilder();

        for (char character : text.toUpperCase().toCharArray()) {
            if (Character.isWhitespace(character)) {
                continue;
            }

            String phoneticValue = PHONETIC_MAP.getOrDefault(character, String.valueOf(character));
            phonetic.append(phoneticValue).append(' ');
        }

        return phonetic.toString().trim();
    }
}
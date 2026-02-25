package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

public final class PhoneticAlphabetConverter {

    private PhoneticAlphabetConverter() {
        // Utility class; prevent instantiation
    }

    private static final Map<Character, String> NATO_PHONETIC_MAP = new HashMap<>();

    static {
        NATO_PHONETIC_MAP.put('A', "Alpha");
        NATO_PHONETIC_MAP.put('B', "Bravo");
        NATO_PHONETIC_MAP.put('C', "Charlie");
        NATO_PHONETIC_MAP.put('D', "Delta");
        NATO_PHONETIC_MAP.put('E', "Echo");
        NATO_PHONETIC_MAP.put('F', "Foxtrot");
        NATO_PHONETIC_MAP.put('G', "Golf");
        NATO_PHONETIC_MAP.put('H', "Hotel");
        NATO_PHONETIC_MAP.put('I', "India");
        NATO_PHONETIC_MAP.put('J', "Juliett");
        NATO_PHONETIC_MAP.put('K', "Kilo");
        NATO_PHONETIC_MAP.put('L', "Lima");
        NATO_PHONETIC_MAP.put('M', "Mike");
        NATO_PHONETIC_MAP.put('N', "November");
        NATO_PHONETIC_MAP.put('O', "Oscar");
        NATO_PHONETIC_MAP.put('P', "Papa");
        NATO_PHONETIC_MAP.put('Q', "Quebec");
        NATO_PHONETIC_MAP.put('R', "Romeo");
        NATO_PHONETIC_MAP.put('S', "Sierra");
        NATO_PHONETIC_MAP.put('T', "Tango");
        NATO_PHONETIC_MAP.put('U', "Uniform");
        NATO_PHONETIC_MAP.put('V', "Victor");
        NATO_PHONETIC_MAP.put('W', "Whiskey");
        NATO_PHONETIC_MAP.put('X', "X-ray");
        NATO_PHONETIC_MAP.put('Y', "Yankee");
        NATO_PHONETIC_MAP.put('Z', "Zulu");
        NATO_PHONETIC_MAP.put('0', "Zero");
        NATO_PHONETIC_MAP.put('1', "One");
        NATO_PHONETIC_MAP.put('2', "Two");
        NATO_PHONETIC_MAP.put('3', "Three");
        NATO_PHONETIC_MAP.put('4', "Four");
        NATO_PHONETIC_MAP.put('5', "Five");
        NATO_PHONETIC_MAP.put('6', "Six");
        NATO_PHONETIC_MAP.put('7', "Seven");
        NATO_PHONETIC_MAP.put('8', "Eight");
        NATO_PHONETIC_MAP.put('9', "Nine");
    }

    public static String convertToPhonetic(String text) {
        StringBuilder phoneticTextBuilder = new StringBuilder();

        for (char currentChar : text.toUpperCase().toCharArray()) {
            if (Character.isWhitespace(currentChar)) {
                continue;
            }
            String phoneticWord = NATO_PHONETIC_MAP.getOrDefault(currentChar, String.valueOf(currentChar));
            phoneticTextBuilder.append(phoneticWord).append(" ");
        }

        return phoneticTextBuilder.toString().trim();
    }
}
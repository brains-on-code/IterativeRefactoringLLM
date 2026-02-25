package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting text to its NATO phonetic alphabet representation.
 */
public final class NatoPhoneticConverter {

    private NatoPhoneticConverter() {
    }

    private static final Map<Character, String> NATO_PHONETIC_ALPHABET = new HashMap<>();

    static {
        NATO_PHONETIC_ALPHABET.put('A', "Alpha");
        NATO_PHONETIC_ALPHABET.put('B', "Bravo");
        NATO_PHONETIC_ALPHABET.put('C', "Charlie");
        NATO_PHONETIC_ALPHABET.put('D', "Delta");
        NATO_PHONETIC_ALPHABET.put('E', "Echo");
        NATO_PHONETIC_ALPHABET.put('F', "Foxtrot");
        NATO_PHONETIC_ALPHABET.put('G', "Golf");
        NATO_PHONETIC_ALPHABET.put('H', "Hotel");
        NATO_PHONETIC_ALPHABET.put('I', "India");
        NATO_PHONETIC_ALPHABET.put('J', "Juliett");
        NATO_PHONETIC_ALPHABET.put('K', "Kilo");
        NATO_PHONETIC_ALPHABET.put('L', "Lima");
        NATO_PHONETIC_ALPHABET.put('M', "Mike");
        NATO_PHONETIC_ALPHABET.put('N', "November");
        NATO_PHONETIC_ALPHABET.put('O', "Oscar");
        NATO_PHONETIC_ALPHABET.put('P', "Papa");
        NATO_PHONETIC_ALPHABET.put('Q', "Quebec");
        NATO_PHONETIC_ALPHABET.put('R', "Romeo");
        NATO_PHONETIC_ALPHABET.put('S', "Sierra");
        NATO_PHONETIC_ALPHABET.put('T', "Tango");
        NATO_PHONETIC_ALPHABET.put('U', "Uniform");
        NATO_PHONETIC_ALPHABET.put('V', "Victor");
        NATO_PHONETIC_ALPHABET.put('W', "Whiskey");
        NATO_PHONETIC_ALPHABET.put('X', "X-ray");
        NATO_PHONETIC_ALPHABET.put('Y', "Yankee");
        NATO_PHONETIC_ALPHABET.put('Z', "Zulu");
        NATO_PHONETIC_ALPHABET.put('0', "Zero");
        NATO_PHONETIC_ALPHABET.put('1', "One");
        NATO_PHONETIC_ALPHABET.put('2', "Two");
        NATO_PHONETIC_ALPHABET.put('3', "Three");
        NATO_PHONETIC_ALPHABET.put('4', "Four");
        NATO_PHONETIC_ALPHABET.put('5', "Five");
        NATO_PHONETIC_ALPHABET.put('6', "Six");
        NATO_PHONETIC_ALPHABET.put('7', "Seven");
        NATO_PHONETIC_ALPHABET.put('8', "Eight");
        NATO_PHONETIC_ALPHABET.put('9', "Nine");
    }

    /**
     * Converts the given text to its NATO phonetic alphabet representation.
     * Whitespace characters are ignored.
     *
     * @param inputText the text to convert
     * @return the NATO phonetic representation of the input
     */
    public static String toNatoPhonetic(String inputText) {
        StringBuilder phoneticTextBuilder = new StringBuilder();

        for (char currentCharacter : inputText.toUpperCase().toCharArray()) {
            if (Character.isWhitespace(currentCharacter)) {
                continue;
            }

            String phoneticWord = NATO_PHONETIC_ALPHABET.getOrDefault(
                currentCharacter,
                String.valueOf(currentCharacter)
            );
            phoneticTextBuilder.append(phoneticWord).append(" ");
        }

        return phoneticTextBuilder.toString().trim();
    }
}
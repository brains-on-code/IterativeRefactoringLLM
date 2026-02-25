package com.thealgorithms.conversions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MorseCodeConverter {

    private static final String WORD_SEPARATOR_TEXT = " ";
    private static final String WORD_SEPARATOR_MORSE = " | ";
    private static final String LETTER_SEPARATOR_MORSE = " ";

    private static final Map<Character, String> MORSE_MAP;
    private static final Map<String, Character> REVERSE_MAP;

    static {
        Map<Character, String> morseMap = new HashMap<>();
        morseMap.put('A', ".-");
        morseMap.put('B', "-...");
        morseMap.put('C', "-.-.");
        morseMap.put('D', "-..");
        morseMap.put('E', ".");
        morseMap.put('F', "..-.");
        morseMap.put('G', "--.");
        morseMap.put('H', "....");
        morseMap.put('I', "..");
        morseMap.put('J', ".---");
        morseMap.put('K', "-.-");
        morseMap.put('L', ".-..");
        morseMap.put('M', "--");
        morseMap.put('N', "-.");
        morseMap.put('O', "---");
        morseMap.put('P', ".--.");
        morseMap.put('Q', "--.-");
        morseMap.put('R', ".-.");
        morseMap.put('S', "...");
        morseMap.put('T', "-");
        morseMap.put('U', "..-");
        morseMap.put('V', "...-");
        morseMap.put('W', ".--");
        morseMap.put('X', "-..-");
        morseMap.put('Y', "-.--");
        morseMap.put('Z', "--..");

        MORSE_MAP = Collections.unmodifiableMap(morseMap);

        Map<String, Character> reverseMap = new HashMap<>();
        MORSE_MAP.forEach((character, code) -> reverseMap.put(code, character));
        REVERSE_MAP = Collections.unmodifiableMap(reverseMap);
    }

    private MorseCodeConverter() {
        // Utility class; prevent instantiation
    }

    public static String textToMorse(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder morseBuilder = new StringBuilder();
        String[] words = text.toUpperCase().split(WORD_SEPARATOR_TEXT);

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            String word = words[wordIndex];

            for (char character : word.toCharArray()) {
                String morseCode = MORSE_MAP.get(character);
                if (morseCode != null) {
                    morseBuilder.append(morseCode).append(LETTER_SEPARATOR_MORSE);
                }
            }

            if (wordIndex < words.length - 1) {
                morseBuilder.append(WORD_SEPARATOR_MORSE.trim()).append(LETTER_SEPARATOR_MORSE);
            }
        }

        return morseBuilder.toString().trim();
    }

    public static String morseToText(String morse) {
        if (morse == null || morse.isEmpty()) {
            return "";
        }

        StringBuilder textBuilder = new StringBuilder();
        String[] words = morse.split("\\s*\\|\\s*");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            String[] codes = words[wordIndex].trim().split("\\s+");

            for (String code : codes) {
                Character character = REVERSE_MAP.get(code);
                textBuilder.append(character != null ? character : '?');
            }

            if (wordIndex < words.length - 1) {
                textBuilder.append(WORD_SEPARATOR_TEXT);
            }
        }

        return textBuilder.toString();
    }
}
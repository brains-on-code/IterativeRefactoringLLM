package com.thealgorithms.conversions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts text to Morse code and vice-versa.
 * Text to Morse code: Each letter is separated by a space and each word is separated by a pipe (|).
 * Example: "HELLO WORLD" -> ".... . .-.. .-.. --- | .-- --- .-. .-.. -.."
 *
 * Morse code to text: Each letter is separated by a space and each word is separated by a pipe (|).
 * Example: ".... . .-.. .-.. --- | .-- --- .-. .-.. -.." -> "HELLO WORLD"
 *
 * Applications: Used in radio communications and algorithmic challenges.
 *
 * @author Hardvan
 */
public final class MorseCodeConverter {

    private static final String WORD_SEPARATOR_TEXT = " ";
    private static final String WORD_SEPARATOR_MORSE = " | ";
    private static final char UNKNOWN_CHAR = '?';

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
        for (Map.Entry<Character, String> entry : MORSE_MAP.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
        REVERSE_MAP = Collections.unmodifiableMap(reverseMap);
    }

    private MorseCodeConverter() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts text to Morse code.
     * Each letter is separated by a space and each word is separated by a pipe (|).
     *
     * @param text The text to convert to Morse code.
     * @return The Morse code representation of the text.
     */
    public static String textToMorse(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder morseBuilder = new StringBuilder();
        String[] words = text.toUpperCase().split(WORD_SEPARATOR_TEXT);

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            appendWordAsMorse(morseBuilder, words[wordIndex]);

            if (wordIndex < words.length - 1) {
                morseBuilder.append(WORD_SEPARATOR_MORSE);
            }
        }

        return morseBuilder.toString().trim();
    }

    private static void appendWordAsMorse(StringBuilder morseBuilder, String word) {
        for (char character : word.toCharArray()) {
            String morseCode = MORSE_MAP.get(character);
            if (morseCode != null) {
                morseBuilder.append(morseCode).append(WORD_SEPARATOR_TEXT);
            }
        }
    }

    /**
     * Converts Morse code to text.
     * Each letter is separated by a space and each word is separated by a pipe (|).
     *
     * @param morse The Morse code to convert to text.
     * @return The text representation of the Morse code.
     */
    public static String morseToText(String morse) {
        if (morse == null || morse.isEmpty()) {
            return "";
        }

        StringBuilder textBuilder = new StringBuilder();
        String[] words = morse.split("\\s*\\|\\s*");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            appendMorseWordAsText(textBuilder, words[wordIndex]);

            if (wordIndex < words.length - 1) {
                textBuilder.append(WORD_SEPARATOR_TEXT);
            }
        }

        return textBuilder.toString();
    }

    private static void appendMorseWordAsText(StringBuilder textBuilder, String morseWord) {
        String[] codes = morseWord.trim().split("\\s+");
        for (String code : codes) {
            if (!code.isEmpty()) {
                textBuilder.append(REVERSE_MAP.getOrDefault(code, UNKNOWN_CHAR));
            }
        }
    }
}
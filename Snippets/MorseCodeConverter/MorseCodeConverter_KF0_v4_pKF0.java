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
 * author Hardvan
 */
public final class MorseCodeConverter {

    private static final String TEXT_WORD_SEPARATOR = " ";
    private static final String MORSE_WORD_SEPARATOR = " | ";
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

        String[] words = text.toUpperCase().split(TEXT_WORD_SEPARATOR);
        StringBuilder morseBuilder = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            appendWordAsMorse(morseBuilder, words[i]);
            if (i < words.length - 1) {
                morseBuilder.append(MORSE_WORD_SEPARATOR);
            }
        }

        return morseBuilder.toString().trim();
    }

    private static void appendWordAsMorse(StringBuilder morseBuilder, String word) {
        for (char character : word.toCharArray()) {
            String morseCode = MORSE_MAP.get(character);
            if (morseCode != null) {
                morseBuilder.append(morseCode).append(TEXT_WORD_SEPARATOR);
            } else {
                morseBuilder.append(UNKNOWN_CHAR).append(TEXT_WORD_SEPARATOR);
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

        String[] words = morse.split("\\s*\\|\\s*");
        StringBuilder textBuilder = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            appendMorseWordAsText(textBuilder, words[i]);
            if (i < words.length - 1) {
                textBuilder.append(TEXT_WORD_SEPARATOR);
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
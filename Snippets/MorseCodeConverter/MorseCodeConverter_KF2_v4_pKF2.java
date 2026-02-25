package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting between plain text and Morse code.
 *
 * <p>Supported characters: A–Z (case-insensitive).</p>
 *
 * <p>Conventions:
 * <ul>
 *   <li>Text → Morse: input words separated by whitespace, output words
 *       separated by {@code " | "}.</li>
 *   <li>Morse → Text: input words separated by {@code " | "}.</li>
 * </ul>
 * </p>
 */
public final class MorseCodeConverter {

    /** Separator between words in Morse representation. */
    private static final String MORSE_WORD_SEPARATOR = " | ";

    /** Separator between letters in Morse representation. */
    private static final String MORSE_LETTER_SEPARATOR = " ";

    /** Maps Latin letters (A–Z) to their Morse code representation. */
    private static final Map<Character, String> MORSE_MAP = new HashMap<>();

    /** Reverse lookup: maps Morse code sequences to Latin letters. */
    private static final Map<String, Character> REVERSE_MAP = new HashMap<>();

    /** Prevent instantiation of utility class. */
    private MorseCodeConverter() {}

    static {
        MORSE_MAP.put('A', ".-");
        MORSE_MAP.put('B', "-...");
        MORSE_MAP.put('C', "-.-.");
        MORSE_MAP.put('D', "-..");
        MORSE_MAP.put('E', ".");
        MORSE_MAP.put('F', "..-.");
        MORSE_MAP.put('G', "--.");
        MORSE_MAP.put('H', "....");
        MORSE_MAP.put('I', "..");
        MORSE_MAP.put('J', ".---");
        MORSE_MAP.put('K', "-.-");
        MORSE_MAP.put('L', ".-..");
        MORSE_MAP.put('M', "--");
        MORSE_MAP.put('N', "-.");
        MORSE_MAP.put('O', "---");
        MORSE_MAP.put('P', ".--.");
        MORSE_MAP.put('Q', "--.-");
        MORSE_MAP.put('R', ".-.");
        MORSE_MAP.put('S', "...");
        MORSE_MAP.put('T', "-");
        MORSE_MAP.put('U', "..-");
        MORSE_MAP.put('V', "...-");
        MORSE_MAP.put('W', ".--");
        MORSE_MAP.put('X', "-..-");
        MORSE_MAP.put('Y', "-.--");
        MORSE_MAP.put('Z', "--..");

        MORSE_MAP.forEach((character, code) -> REVERSE_MAP.put(code, character));
    }

    /**
     * Converts plain text to Morse code.
     *
     * <p>Input:
     * <ul>
     *   <li>Words separated by one or more whitespace characters.</li>
     *   <li>Characters outside A–Z are ignored.</li>
     * </ul>
     *
     * <p>Output:
     * <ul>
     *   <li>Words separated by {@value #MORSE_WORD_SEPARATOR}.</li>
     *   <li>Letters within a word separated by a single space.</li>
     * </ul>
     *
     * @param text the input text to convert (case-insensitive)
     * @return the Morse code representation of the input text
     */
    public static String textToMorse(String text) {
        StringBuilder morse = new StringBuilder();
        String[] words = text.toUpperCase().split("\\s+");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            char[] characters = words[wordIndex].toCharArray();

            for (int charIndex = 0; charIndex < characters.length; charIndex++) {
                String code = MORSE_MAP.getOrDefault(characters[charIndex], "");
                if (!code.isEmpty()) {
                    morse.append(code);
                    if (charIndex < characters.length - 1) {
                        morse.append(MORSE_LETTER_SEPARATOR);
                    }
                }
            }

            if (wordIndex < words.length - 1) {
                morse.append(MORSE_WORD_SEPARATOR);
            }
        }

        return morse.toString();
    }

    /**
     * Converts Morse code to plain text.
     *
     * <p>Input:
     * <ul>
     *   <li>Words separated by {@value #MORSE_WORD_SEPARATOR} (i.e. {@code " | "}).</li>
     *   <li>Letters within a word separated by one or more spaces.</li>
     * </ul>
     *
     * <p>Output:
     * <ul>
     *   <li>Words separated by a single space.</li>
     *   <li>Unknown Morse sequences are converted to {@code '?' }.</li>
     * </ul>
     *
     * @param morse the Morse code string to convert
     * @return the plain text representation of the Morse code
     */
    public static String morseToText(String morse) {
        StringBuilder text = new StringBuilder();
        String[] words = morse.split("\\s\\|\\s");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            String[] codes = words[wordIndex].split("\\s+");

            for (String code : codes) {
                text.append(REVERSE_MAP.getOrDefault(code, '?'));
            }

            if (wordIndex < words.length - 1) {
                text.append(' ');
            }
        }

        return text.toString();
    }
}
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
 *   <li>Text → Morse: input words separated by a single space, output words
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

    private static final Map<Character, String> MORSE_MAP = new HashMap<>();
    private static final Map<String, Character> REVERSE_MAP = new HashMap<>();

    private MorseCodeConverter() {
        // Utility class; prevent instantiation.
    }

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
     * <p>Input words must be separated by one or more spaces. Output words are
     * separated by {@value #MORSE_WORD_SEPARATOR}, and letters within a word
     * are separated by a single space.</p>
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
     * <p>Input words must be separated by {@value #MORSE_WORD_SEPARATOR}.
     * Letters within a word must be separated by a single space.</p>
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
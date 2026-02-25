package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

public final class MorseCodeConverter {

    private MorseCodeConverter() {}

    private static final Map<Character, String> CHAR_TO_MORSE_MAP = new HashMap<>();
    private static final Map<String, Character> MORSE_TO_CHAR_MAP = new HashMap<>();

    static {
        CHAR_TO_MORSE_MAP.put('A', ".-");
        CHAR_TO_MORSE_MAP.put('B', "-...");
        CHAR_TO_MORSE_MAP.put('C', "-.-.");
        CHAR_TO_MORSE_MAP.put('D', "-..");
        CHAR_TO_MORSE_MAP.put('E', ".");
        CHAR_TO_MORSE_MAP.put('F', "..-.");
        CHAR_TO_MORSE_MAP.put('G', "--.");
        CHAR_TO_MORSE_MAP.put('H', "....");
        CHAR_TO_MORSE_MAP.put('I', "..");
        CHAR_TO_MORSE_MAP.put('J', ".---");
        CHAR_TO_MORSE_MAP.put('K', "-.-");
        CHAR_TO_MORSE_MAP.put('L', ".-..");
        CHAR_TO_MORSE_MAP.put('M', "--");
        CHAR_TO_MORSE_MAP.put('N', "-.");
        CHAR_TO_MORSE_MAP.put('O', "---");
        CHAR_TO_MORSE_MAP.put('P', ".--.");
        CHAR_TO_MORSE_MAP.put('Q', "--.-");
        CHAR_TO_MORSE_MAP.put('R', ".-.");
        CHAR_TO_MORSE_MAP.put('S', "...");
        CHAR_TO_MORSE_MAP.put('T', "-");
        CHAR_TO_MORSE_MAP.put('U', "..-");
        CHAR_TO_MORSE_MAP.put('V', "...-");
        CHAR_TO_MORSE_MAP.put('W', ".--");
        CHAR_TO_MORSE_MAP.put('X', "-..-");
        CHAR_TO_MORSE_MAP.put('Y', "-.--");
        CHAR_TO_MORSE_MAP.put('Z', "--..");

        CHAR_TO_MORSE_MAP.forEach((character, morseCode) -> MORSE_TO_CHAR_MAP.put(morseCode, character));
    }

    public static String textToMorse(String inputText) {
        StringBuilder morseBuilder = new StringBuilder();
        String[] words = inputText.toUpperCase().split(" ");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            String word = words[wordIndex];
            for (char character : word.toCharArray()) {
                morseBuilder.append(CHAR_TO_MORSE_MAP.getOrDefault(character, "")).append(" ");
            }
            if (wordIndex < words.length - 1) {
                morseBuilder.append("| ");
            }
        }
        return morseBuilder.toString().trim();
    }

    public static String morseToText(String morseCodeText) {
        StringBuilder textBuilder = new StringBuilder();
        String[] morseWords = morseCodeText.split(" \\| ");

        for (int wordIndex = 0; wordIndex < morseWords.length; wordIndex++) {
            String morseWord = morseWords[wordIndex];
            for (String morseCharacter : morseWord.split(" ")) {
                textBuilder.append(MORSE_TO_CHAR_MAP.getOrDefault(morseCharacter, '?'));
            }
            if (wordIndex < morseWords.length - 1) {
                textBuilder.append(" ");
            }
        }
        return textBuilder.toString();
    }
}
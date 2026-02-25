package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;


public final class CheckAnagrams {
    private CheckAnagrams() {
    }

    public static boolean isAnagrams(String s1, String s2) {
        int l1 = s1.length();
        int l2 = s2.length();
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        Map<Character, Integer> charAppearances = new HashMap<>();

        for (int i = 0; i < l1; i++) {
            char c = s1.charAt(i);
            int numOfAppearances = charAppearances.getOrDefault(c, 0);
            charAppearances.put(c, numOfAppearances + 1);
        }

        for (int i = 0; i < l2; i++) {
            char c = s2.charAt(i);
            if (!charAppearances.containsKey(c)) {
                return false;
            }
            charAppearances.put(c, charAppearances.get(c) - 1);
        }

        for (int cnt : charAppearances.values()) {
            if (cnt != 0) {
                return false;
            }
        }
        return true;
    }


    public static boolean isAnagramsUnicode(String s1, String s2) {
        int[] dict = new int[128];
        for (char ch : s1.toCharArray()) {
            dict[ch]++;
        }
        for (char ch : s2.toCharArray()) {
            dict[ch]--;
        }
        for (int e : dict) {
            if (e != 0) {
                return false;
            }
        }
        return true;
    }


    public static boolean isAnagramsOptimised(String s1, String s2) {
        int[] dict = new int[26];
        for (char ch : s1.toCharArray()) {
            checkLetter(ch);
            dict[ch - 'a']++;
        }
        for (char ch : s2.toCharArray()) {
            checkLetter(ch);
            dict[ch - 'a']--;
        }
        for (int e : dict) {
            if (e != 0) {
                return false;
            }
        }
        return true;
    }

    private static void checkLetter(char ch) {
        int index = ch - 'a';
        if (index < 0 || index >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}

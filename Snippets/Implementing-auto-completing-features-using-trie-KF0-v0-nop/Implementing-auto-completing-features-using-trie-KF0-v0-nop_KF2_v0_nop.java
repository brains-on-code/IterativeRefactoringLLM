package com.thealgorithms.others;

class Trieac {

    public static final int ALPHABET_SIZE = 26;

    static class TrieNode {

        TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        boolean isWordEnd;
    }

    static TrieNode getNode() {
        TrieNode pNode = new TrieNode();
        pNode.isWordEnd = false;

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            pNode.children[i] = null;
        }

        return pNode;
    }

    static void insert(TrieNode root, final String key) {
        TrieNode pCrawl = root;

        for (int level = 0; level < key.length(); level++) {
            int index = (key.charAt(level) - 'a');
            if (pCrawl.children[index] == null) {
                pCrawl.children[index] = getNode();
            }
            pCrawl = pCrawl.children[index];
        }

        pCrawl.isWordEnd = true;
    }

    boolean search(TrieNode root, final String key) {
        int length = key.length();
        TrieNode pCrawl = root;

        for (int level = 0; level < length; level++) {
            int index = (key.charAt(level) - 'a');

            if (pCrawl.children[index] == null) {
                pCrawl = pCrawl.children[index];
            }
        }

        return (pCrawl != null && pCrawl.isWordEnd);
    }

    static boolean isLastNode(TrieNode root) {
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (root.children[i] != null) {
                return false;
            }
        }
        return true;
    }

    static void suggestionsRec(TrieNode root, String currPrefix) {
        if (root.isWordEnd) {
            System.out.println(currPrefix);
        }

        if (isLastNode(root)) {
            return;
        }

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (root.children[i] != null) {
                currPrefix += (char) (97 + i);

                suggestionsRec(root.children[i], currPrefix);
            }
        }
    }

    static int printAutoSuggestions(TrieNode root, final String query) {
        TrieNode pCrawl = root;

        int level;
        int n = query.length();

        for (level = 0; level < n; level++) {
            int index = (query.charAt(level) - 'a');

            if (pCrawl.children[index] == null) {
                return 0;
            }

            pCrawl = pCrawl.children[index];
        }

        boolean isWord = (pCrawl.isWordEnd);

        boolean isLast = isLastNode(pCrawl);

        if (isWord && isLast) {
            System.out.println(query);
            return -1;
        }

        if (!isLast) {
            String prefix = query;
            suggestionsRec(pCrawl, prefix);
            return 1;
        }

        return 0;
    }

    public static void main(String[] args) {
        TrieNode root = getNode();
        insert(root, "hello");
        insert(root, "dog");
        insert(root, "hell");
        insert(root, "cat");
        insert(root, "a");
        insert(root, "hel");
        insert(root, "help");
        insert(root, "helps");
        insert(root, "helping");
        int comp = printAutoSuggestions(root, "hel");

        if (comp == -1) {
            System.out.println("No other strings found "
                + "with this prefix\n");
        } else if (comp == 0) {
            System.out.println("No string found with"
                + " this prefix\n");
        }
    }
}

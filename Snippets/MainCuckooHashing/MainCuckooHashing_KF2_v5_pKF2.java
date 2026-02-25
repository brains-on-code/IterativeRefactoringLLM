package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.Scanner;

/**
 * Console entry point for interacting with {@link HashMapCuckooHashing}.
 * Provides a simple text-based menu to perform operations on the hash table.
 */
public final class MainCuckooHashing {

    private static final int INITIAL_TABLE_SIZE = 7;

    private MainCuckooHashing() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        HashMapCuckooHashing hashMap = new HashMapCuckooHashing(INITIAL_TABLE_SIZE);
        try (Scanner scanner = new Scanner(System.in)) {
            runMenuLoop(scanner, hashMap);
        }
    }

    /**
     * Main interaction loop that repeatedly shows the menu and
     * dispatches user choices to the appropriate handlers.
     */
    private static void runMenuLoop(Scanner scanner, HashMapCuckooHashing hashMap) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> handleInsert(scanner, hashMap);
                case 2 -> handleDelete(scanner, hashMap);
                case 3 -> handlePrintTable(hashMap);
                case 4 -> running = false;
                case 5 -> handleSearch(scanner, hashMap);
                case 6 -> handleLoadFactor(hashMap);
                case 7 -> handleRehash(hashMap);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Prints the available menu options to the console.
     */
    private static void printMenu() {
        System.out.println("_________________________");
        System.out.println("Enter your choice:");
        System.out.println("1. Add key");
        System.out.println("2. Delete key");
        System.out.println("3. Print table");
        System.out.println("4. Exit");
        System.out.println("5. Search and print key index");
        System.out.println("6. Check load factor");
        System.out.println("7. Rehash current table");
    }

    /**
     * Reads a key from input and inserts it into the hash table.
     */
    private static void handleInsert(Scanner scanner, HashMapCuckooHashing hashMap) {
        System.out.print("Enter the key: ");
        int key = scanner.nextInt();
        hashMap.insertKey2HashTable(key);
    }

    /**
     * Reads a key from input and deletes it from the hash table.
     */
    private static void handleDelete(Scanner scanner, HashMapCuckooHashing hashMap) {
        System.out.print("Enter the key to delete: ");
        int key = scanner.nextInt();
        hashMap.deleteKeyFromHashTable(key);
    }

    /**
     * Prints the current state of the hash table.
     */
    private static void handlePrintTable(HashMapCuckooHashing hashMap) {
        System.out.println("Current hash table:");
        hashMap.displayHashtable();
    }

    /**
     * Reads a key from input, searches for it, and prints its index.
     */
    private static void handleSearch(Scanner scanner, HashMapCuckooHashing hashMap) {
        System.out.print("Enter the key to find: ");
        int key = scanner.nextInt();
        int index = hashMap.findKeyInTable(key);
        System.out.println("Key " + key + " is at index: " + index);
    }

    /**
     * Prints the current load factor of the hash table.
     */
    private static void handleLoadFactor(HashMapCuckooHashing hashMap) {
        System.out.printf("Load factor: %.2f%n", hashMap.checkLoadFactor());
    }

    /**
     * Rehashes the table with an increased size.
     */
    private static void handleRehash(HashMapCuckooHashing hashMap) {
        hashMap.reHashTableIncreasesTableSize();
        System.out.println("Rehashing complete. Table size increased.");
    }
}
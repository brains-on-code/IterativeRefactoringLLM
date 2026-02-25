package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.Scanner;

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

    private static void runMenuLoop(Scanner scanner, HashMapCuckooHashing hashMap) {
        while (true) {
            printMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> handleInsert(scanner, hashMap);
                case 2 -> handleDelete(scanner, hashMap);
                case 3 -> handlePrintTable(hashMap);
                case 4 -> {
                    return;
                }
                case 5 -> handleSearch(scanner, hashMap);
                case 6 -> handleLoadFactor(hashMap);
                case 7 -> handleRehash(hashMap);
                default -> throw new IllegalArgumentException("Unexpected value: " + choice);
            }
        }
    }

    private static void printMenu() {
        System.out.println("_________________________");
        System.out.println("Enter your Choice :");
        System.out.println("1. Add Key");
        System.out.println("2. Delete Key");
        System.out.println("3. Print Table");
        System.out.println("4. Exit");
        System.out.println("5. Search and print key index");
        System.out.println("6. Check load factor");
        System.out.println("7. Rehash Current Table");
    }

    private static void handleInsert(Scanner scanner, HashMapCuckooHashing hashMap) {
        System.out.println("Enter the Key: ");
        int key = scanner.nextInt();
        hashMap.insertKey2HashTable(key);
    }

    private static void handleDelete(Scanner scanner, HashMapCuckooHashing hashMap) {
        System.out.println("Enter the Key to delete: ");
        int key = scanner.nextInt();
        hashMap.deleteKeyFromHashTable(key);
    }

    private static void handlePrintTable(HashMapCuckooHashing hashMap) {
        System.out.println("Print table:\n");
        hashMap.displayHashtable();
    }

    private static void handleSearch(Scanner scanner, HashMapCuckooHashing hashMap) {
        System.out.println("Enter the Key to find and print: ");
        int key = scanner.nextInt();
        System.out.println("Key: " + key + " is at index: " + hashMap.findKeyInTable(key) + "\n");
    }

    private static void handleLoadFactor(HashMapCuckooHashing hashMap) {
        System.out.printf("Load factor is: %.2f%n", hashMap.checkLoadFactor());
    }

    private static void handleRehash(HashMapCuckooHashing hashMap) {
        hashMap.reHashTableIncreasesTableSize();
    }
}
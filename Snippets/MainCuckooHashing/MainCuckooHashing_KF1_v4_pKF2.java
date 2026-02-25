package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class Class1 {

    private static final int INITIAL_TABLE_SIZE = 7;

    private Class1() {}

    public static void method1(String[] args) {
        HashMapCuckooHashing hashTable = new HashMapCuckooHashing(INITIAL_TABLE_SIZE);
        try (Scanner scanner = new Scanner(System.in)) {
            runMenuLoop(scanner, hashTable);
        }
    }

    private static void runMenuLoop(Scanner scanner, HashMapCuckooHashing hashTable) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt(scanner, "Enter a valid menu option (1-7): ");

            switch (choice) {
                case 1 -> handleInsert(scanner, hashTable);
                case 2 -> handleDelete(scanner, hashTable);
                case 3 -> handlePrintTable(hashTable);
                case 4 -> running = false;
                case 5 -> handleSearch(scanner, hashTable);
                case 6 -> handleLoadFactor(hashTable);
                case 7 -> handleRehash(hashTable);
                default -> System.out.println("Invalid choice. Please select an option between 1 and 7.");
            }
        }
    }

    private static void handleInsert(Scanner scanner, HashMapCuckooHashing hashTable) {
        int keyToInsert = readInt(scanner, "Enter the key to insert: ");
        hashTable.insertKey2HashTable(keyToInsert);
    }

    private static void handleDelete(Scanner scanner, HashMapCuckooHashing hashTable) {
        int keyToDelete = readInt(scanner, "Enter the key to delete: ");
        hashTable.deleteKeyFromHashTable(keyToDelete);
    }

    private static void handlePrintTable(HashMapCuckooHashing hashTable) {
        System.out.println("Current hash table:");
        hashTable.displayHashtable();
    }

    private static void handleSearch(Scanner scanner, HashMapCuckooHashing hashTable) {
        int keyToFind = readInt(scanner, "Enter the key to find: ");
        int index = hashTable.findKeyInTable(keyToFind);
        System.out.println("Key: " + keyToFind + " is at index: " + index);
    }

    private static void handleLoadFactor(HashMapCuckooHashing hashTable) {
        System.out.printf("Load factor: %.2f%n", hashTable.checkLoadFactor());
    }

    private static void handleRehash(HashMapCuckooHashing hashTable) {
        hashTable.reHashTableIncreasesTableSize();
        System.out.println("Rehashed table with increased size.");
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }
    }

    private static void printMenu() {
        System.out.println("_________________________");
        System.out.println("Hash Table Menu:");
        System.out.println("1. Add key");
        System.out.println("2. Delete key");
        System.out.println("3. Print table");
        System.out.println("4. Exit");
        System.out.println("5. Search and print key index");
        System.out.println("6. Check load factor");
        System.out.println("7. Rehash current table");
    }
}
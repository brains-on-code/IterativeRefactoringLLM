package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.InputMismatchException;
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
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1 -> handleInsert(scanner, hashMap);
                case 2 -> handleDelete(scanner, hashMap);
                case 3 -> handlePrintTable(hashMap);
                case 4 -> running = false;
                case 5 -> handleSearch(scanner, hashMap);
                case 6 -> handlePrintLoadFactor(hashMap);
                case 7 -> handleRehash(hashMap);
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("_________________________");
        System.out.println("1. Add Key");
        System.out.println("2. Delete Key");
        System.out.println("3. Print Table");
        System.out.println("4. Exit");
        System.out.println("5. Search and print key index");
        System.out.println("6. Check load factor");
        System.out.println("7. Rehash Current Table");
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter an integer value.");
                scanner.next(); // clear invalid input
            }
        }
    }

    private static void handleInsert(Scanner scanner, HashMapCuckooHashing hashMap) {
        int key = readInt(scanner, "Enter the key to add: ");
        hashMap.insertKey2HashTable(key);
    }

    private static void handleDelete(Scanner scanner, HashMapCuckooHashing hashMap) {
        int key = readInt(scanner, "Enter the key to delete: ");
        hashMap.deleteKeyFromHashTable(key);
    }

    private static void handlePrintTable(HashMapCuckooHashing hashMap) {
        System.out.println("Current hash table:");
        hashMap.displayHashtable();
    }

    private static void handleSearch(Scanner scanner, HashMapCuckooHashing hashMap) {
        int key = readInt(scanner, "Enter the key to search: ");
        int index = hashMap.findKeyInTable(key);
        System.out.println("Key: " + key + " is at index: " + index);
    }

    private static void handlePrintLoadFactor(HashMapCuckooHashing hashMap) {
        double loadFactor = hashMap.checkLoadFactor();
        System.out.printf("Load factor is: %.2f%n", loadFactor);
    }

    private static void handleRehash(HashMapCuckooHashing hashMap) {
        hashMap.reHashTableIncreasesTableSize();
        System.out.println("Rehashing completed. Table size increased.");
    }
}
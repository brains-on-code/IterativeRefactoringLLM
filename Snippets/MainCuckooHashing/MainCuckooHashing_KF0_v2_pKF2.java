package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.Scanner;

/**
 * Console-based driver for demonstrating Cuckoo Hashing operations.
 */
public final class MainCuckooHashing {

    private static final int INITIAL_TABLE_SIZE = 7;

    private MainCuckooHashing() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) {
        HashMapCuckooHashing hashMap = new HashMapCuckooHashing(INITIAL_TABLE_SIZE);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                printMenu();
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> insertKey(scanner, hashMap);
                    case 2 -> deleteKey(scanner, hashMap);
                    case 3 -> printTable(hashMap);
                    case 4 -> running = false;
                    case 5 -> searchKey(scanner, hashMap);
                    case 6 -> printLoadFactor(hashMap);
                    case 7 -> rehashTable(hashMap);
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

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

    private static void insertKey(Scanner scanner, HashMapCuckooHashing hashMap) {
        System.out.print("Enter the key to insert: ");
        int key = scanner.nextInt();
        hashMap.insertKey2HashTable(key);
    }

    private static void deleteKey(Scanner scanner, HashMapCuckooHashing hashMap) {
        System.out.print("Enter the key to delete: ");
        int key = scanner.nextInt();
        hashMap.deleteKeyFromHashTable(key);
    }

    private static void printTable(HashMapCuckooHashing hashMap) {
        System.out.println("Current hash table:");
        hashMap.displayHashtable();
    }

    private static void searchKey(Scanner scanner, HashMapCuckooHashing hashMap) {
        System.out.print("Enter the key to search: ");
        int key = scanner.nextInt();
        int index = hashMap.findKeyInTable(key);
        System.out.println("Key " + key + " is at index: " + index);
    }

    private static void printLoadFactor(HashMapCuckooHashing hashMap) {
        System.out.printf("Load factor: %.2f%n", hashMap.checkLoadFactor());
    }

    private static void rehashTable(HashMapCuckooHashing hashMap) {
        System.out.println("Rehashing table and increasing its size...");
        hashMap.reHashTableIncreasesTableSize();
    }
}
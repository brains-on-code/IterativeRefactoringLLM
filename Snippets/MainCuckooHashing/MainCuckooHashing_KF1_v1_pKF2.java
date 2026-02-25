package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.Scanner;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static void method1(String[] args) {
        HashMapCuckooHashing hashTable = new HashMapCuckooHashing(7);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter the Key: ");
                    int keyToInsert = scanner.nextInt();
                    hashTable.insertKey2HashTable(keyToInsert);
                }
                case 2 -> {
                    System.out.println("Enter the Key to delete: ");
                    int keyToDelete = scanner.nextInt();
                    hashTable.deleteKeyFromHashTable(keyToDelete);
                }
                case 3 -> {
                    System.out.println("Print table:\n");
                    hashTable.displayHashtable();
                }
                case 4 -> {
                    scanner.close();
                    return;
                }
                case 5 -> {
                    System.out.println("Enter the Key to find and print: ");
                    int keyToFind = scanner.nextInt();
                    int index = hashTable.findKeyInTable(keyToFind);
                    System.out.println("Key: " + keyToFind + " is at index: " + index + "\n");
                }
                case 6 -> System.out.printf("Load factor is: %.2f%n", hashTable.checkLoadFactor());
                case 7 -> hashTable.reHashTableIncreasesTableSize();
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
}
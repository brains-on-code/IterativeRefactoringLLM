package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class Class1 {

    private static final int INITIAL_TABLE_SIZE = 7;

    private static final int OPTION_ADD_KEY = 1;
    private static final int OPTION_DELETE_KEY = 2;
    private static final int OPTION_PRINT_TABLE = 3;
    private static final int OPTION_EXIT = 4;
    private static final int OPTION_SEARCH_KEY = 5;
    private static final int OPTION_CHECK_LOAD_FACTOR = 6;
    private static final int OPTION_REHASH_TABLE = 7;

    private Class1() {
        // Prevent instantiation
    }

    public static void method1(String[] args) {
        HashMapCuckooHashing hashTable = new HashMapCuckooHashing(INITIAL_TABLE_SIZE);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                printMenu();
                int choice = readInt(scanner, "Enter a valid option: ");

                switch (choice) {
                    case OPTION_ADD_KEY:
                        handleAddKey(scanner, hashTable);
                        break;

                    case OPTION_DELETE_KEY:
                        handleDeleteKey(scanner, hashTable);
                        break;

                    case OPTION_PRINT_TABLE:
                        handlePrintTable(hashTable);
                        break;

                    case OPTION_EXIT:
                        running = false;
                        break;

                    case OPTION_SEARCH_KEY:
                        handleSearchKey(scanner, hashTable);
                        break;

                    case OPTION_CHECK_LOAD_FACTOR:
                        handleCheckLoadFactor(hashTable);
                        break;

                    case OPTION_REHASH_TABLE:
                        handleRehashTable(hashTable);
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    private static void handleAddKey(Scanner scanner, HashMapCuckooHashing hashTable) {
        int keyToAdd = readInt(scanner, "Enter the Key: ");
        hashTable.insertKey2HashTable(keyToAdd);
    }

    private static void handleDeleteKey(Scanner scanner, HashMapCuckooHashing hashTable) {
        int keyToDelete = readInt(scanner, "Enter the Key to delete: ");
        hashTable.deleteKeyFromHashTable(keyToDelete);
    }

    private static void handlePrintTable(HashMapCuckooHashing hashTable) {
        System.out.println("Print table:\n");
        hashTable.displayHashtable();
    }

    private static void handleSearchKey(Scanner scanner, HashMapCuckooHashing hashTable) {
        int keyToFind = readInt(scanner, "Enter the Key to find and print: ");
        int index = hashTable.findKeyInTable(keyToFind);
        System.out.println("Key: " + keyToFind + " is at index: " + index + "\n");
    }

    private static void handleCheckLoadFactor(HashMapCuckooHashing hashTable) {
        System.out.printf("Load factor is: %.2f%n", hashTable.checkLoadFactor());
    }

    private static void handleRehashTable(HashMapCuckooHashing hashTable) {
        hashTable.reHashTableIncreasesTableSize();
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }

    private static void printMenu() {
        System.out.println("_________________________");
        System.out.println("Enter your Choice :");
        System.out.println(OPTION_ADD_KEY + ". Add Key");
        System.out.println(OPTION_DELETE_KEY + ". Delete Key");
        System.out.println(OPTION_PRINT_TABLE + ". Print Table");
        System.out.println(OPTION_EXIT + ". Exit");
        System.out.println(OPTION_SEARCH_KEY + ". Search and print key index");
        System.out.println(OPTION_CHECK_LOAD_FACTOR + ". Check load factor");
        System.out.println(OPTION_REHASH_TABLE + ". Rehash Current Table");
    }
}
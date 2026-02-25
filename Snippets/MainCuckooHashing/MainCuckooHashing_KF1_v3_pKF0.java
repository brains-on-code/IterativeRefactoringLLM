package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class Class1 {

    private static final int INITIAL_TABLE_SIZE = 7;

    private enum MenuOption {
        ADD_KEY(1, "Add Key"),
        DELETE_KEY(2, "Delete Key"),
        PRINT_TABLE(3, "Print Table"),
        EXIT(4, "Exit"),
        SEARCH_KEY(5, "Search and print key index"),
        CHECK_LOAD_FACTOR(6, "Check load factor"),
        REHASH_TABLE(7, "Rehash Current Table");

        private final int code;
        private final String description;

        MenuOption(int code, String description) {
            this.code = code;
            this.description = description;
        }

        int getCode() {
            return code;
        }

        String getDescription() {
            return description;
        }

        static MenuOption fromCode(int code) {
            for (MenuOption option : values()) {
                if (option.code == code) {
                    return option;
                }
            }
            return null;
        }
    }

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
                MenuOption option = MenuOption.fromCode(choice);

                if (option == null) {
                    System.out.println("Invalid option. Please try again.");
                    continue;
                }

                switch (option) {
                    case ADD_KEY:
                        handleAddKey(scanner, hashTable);
                        break;
                    case DELETE_KEY:
                        handleDeleteKey(scanner, hashTable);
                        break;
                    case PRINT_TABLE:
                        handlePrintTable(hashTable);
                        break;
                    case EXIT:
                        running = false;
                        break;
                    case SEARCH_KEY:
                        handleSearchKey(scanner, hashTable);
                        break;
                    case CHECK_LOAD_FACTOR:
                        handleCheckLoadFactor(hashTable);
                        break;
                    case REHASH_TABLE:
                        handleRehashTable(hashTable);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
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
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getCode() + ". " + option.getDescription());
        }
    }
}
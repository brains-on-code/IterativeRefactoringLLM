package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.Scanner;

public final class CuckooHashingDemo {
    private CuckooHashingDemo() {}

    private static final int INITIAL_TABLE_SIZE = 7;

    private static final int OPTION_INSERT_KEY = 1;
    private static final int OPTION_DELETE_KEY = 2;
    private static final int OPTION_PRINT_TABLE = 3;
    private static final int OPTION_EXIT = 4;
    private static final int OPTION_SEARCH_KEY = 5;
    private static final int OPTION_CHECK_LOAD_FACTOR = 6;
    private static final int OPTION_REHASH_TABLE = 7;

    public static void main(String[] args) {
        int menuOption;
        int key;

        HashMapCuckooHashing cuckooHashMap = new HashMapCuckooHashing(INITIAL_TABLE_SIZE);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            menuOption = scanner.nextInt();

            switch (menuOption) {
                case OPTION_INSERT_KEY:
                    System.out.println("Enter the key: ");
                    key = scanner.nextInt();
                    cuckooHashMap.insertKey(key);
                    break;

                case OPTION_DELETE_KEY:
                    System.out.println("Enter the key to delete: ");
                    key = scanner.nextInt();
                    cuckooHashMap.deleteKey(key);
                    break;

                case OPTION_PRINT_TABLE:
                    System.out.println("Print table:\n");
                    cuckooHashMap.printTable();
                    break;

                case OPTION_EXIT:
                    scanner.close();
                    return;

                case OPTION_SEARCH_KEY:
                    System.out.println("Enter the key to find and print: ");
                    key = scanner.nextInt();
                    System.out.println(
                        "Key: " + key + " is at index: " + cuckooHashMap.findKeyIndex(key) + "\n"
                    );
                    break;

                case OPTION_CHECK_LOAD_FACTOR:
                    System.out.printf("Load factor is: %.2f%n", cuckooHashMap.getLoadFactor());
                    break;

                case OPTION_REHASH_TABLE:
                    cuckooHashMap.rehashAndIncreaseTableSize();
                    break;

                default:
                    throw new IllegalArgumentException("Unexpected menu option: " + menuOption);
            }
        }
    }

    private static void printMenu() {
        System.out.println("_________________________");
        System.out.println("Enter your choice:");
        System.out.println(OPTION_INSERT_KEY + ". Add key");
        System.out.println(OPTION_DELETE_KEY + ". Delete key");
        System.out.println(OPTION_PRINT_TABLE + ". Print table");
        System.out.println(OPTION_EXIT + ". Exit");
        System.out.println(OPTION_SEARCH_KEY + ". Search and print key index");
        System.out.println(OPTION_CHECK_LOAD_FACTOR + ". Check load factor");
        System.out.println(OPTION_REHASH_TABLE + ". Rehash current table");
    }
}
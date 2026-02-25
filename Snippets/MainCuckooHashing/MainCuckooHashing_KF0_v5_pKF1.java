package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.Scanner;

public final class CuckooHashingDemo {
    private CuckooHashingDemo() {}

    private static final int INITIAL_TABLE_SIZE = 7;

    private static final int MENU_OPTION_INSERT = 1;
    private static final int MENU_OPTION_DELETE = 2;
    private static final int MENU_OPTION_PRINT = 3;
    private static final int MENU_OPTION_EXIT = 4;
    private static final int MENU_OPTION_SEARCH = 5;
    private static final int MENU_OPTION_LOAD_FACTOR = 6;
    private static final int MENU_OPTION_REHASH = 7;

    public static void main(String[] args) {
        int selectedOption;
        int key;

        HashMapCuckooHashing cuckooHashMap = new HashMapCuckooHashing(INITIAL_TABLE_SIZE);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            selectedOption = scanner.nextInt();

            switch (selectedOption) {
                case MENU_OPTION_INSERT:
                    System.out.println("Enter the key: ");
                    key = scanner.nextInt();
                    cuckooHashMap.insertKey(key);
                    break;

                case MENU_OPTION_DELETE:
                    System.out.println("Enter the key to delete: ");
                    key = scanner.nextInt();
                    cuckooHashMap.deleteKey(key);
                    break;

                case MENU_OPTION_PRINT:
                    System.out.println("Print table:\n");
                    cuckooHashMap.printTable();
                    break;

                case MENU_OPTION_EXIT:
                    scanner.close();
                    return;

                case MENU_OPTION_SEARCH:
                    System.out.println("Enter the key to find and print: ");
                    key = scanner.nextInt();
                    System.out.println(
                        "Key: " + key + " is at index: " + cuckooHashMap.findKeyIndex(key) + "\n"
                    );
                    break;

                case MENU_OPTION_LOAD_FACTOR:
                    System.out.printf("Load factor is: %.2f%n", cuckooHashMap.getLoadFactor());
                    break;

                case MENU_OPTION_REHASH:
                    cuckooHashMap.rehashAndIncreaseTableSize();
                    break;

                default:
                    throw new IllegalArgumentException("Unexpected menu option: " + selectedOption);
            }
        }
    }

    private static void printMenu() {
        System.out.println("_________________________");
        System.out.println("Enter your choice:");
        System.out.println(MENU_OPTION_INSERT + ". Add key");
        System.out.println(MENU_OPTION_DELETE + ". Delete key");
        System.out.println(MENU_OPTION_PRINT + ". Print table");
        System.out.println(MENU_OPTION_EXIT + ". Exit");
        System.out.println(MENU_OPTION_SEARCH + ". Search and print key index");
        System.out.println(MENU_OPTION_LOAD_FACTOR + ". Check load factor");
        System.out.println(MENU_OPTION_REHASH + ". Rehash current table");
    }
}
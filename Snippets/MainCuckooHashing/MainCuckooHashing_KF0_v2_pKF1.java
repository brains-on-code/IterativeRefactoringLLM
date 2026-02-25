package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.Scanner;

public final class MainCuckooHashing {
    private MainCuckooHashing() {}

    public static void main(String[] args) {
        int menuChoice;
        int key;

        HashMapCuckooHashing cuckooHashMap = new HashMapCuckooHashing(7);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("_________________________");
            System.out.println("Enter your Choice :");
            System.out.println("1. Add Key");
            System.out.println("2. Delete Key");
            System.out.println("3. Print Table");
            System.out.println("4. Exit");
            System.out.println("5. Search and print key index");
            System.out.println("6. Check load factor");
            System.out.println("7. Rehash Current Table");

            menuChoice = scanner.nextInt();

            switch (menuChoice) {
                case 1:
                    System.out.println("Enter the Key: ");
                    key = scanner.nextInt();
                    cuckooHashMap.insertKey(key);
                    break;

                case 2:
                    System.out.println("Enter the Key to delete:  ");
                    key = scanner.nextInt();
                    cuckooHashMap.deleteKey(key);
                    break;

                case 3:
                    System.out.println("Print table:\n");
                    cuckooHashMap.printTable();
                    break;

                case 4:
                    scanner.close();
                    return;

                case 5:
                    System.out.println("Enter the Key to find and print:  ");
                    key = scanner.nextInt();
                    System.out.println(
                        "Key: " + key + " is at index: " + cuckooHashMap.findKeyIndex(key) + "\n"
                    );
                    break;

                case 6:
                    System.out.printf("Load factor is: %.2f%n", cuckooHashMap.getLoadFactor());
                    break;

                case 7:
                    cuckooHashMap.rehashAndIncreaseTableSize();
                    break;

                default:
                    throw new IllegalArgumentException("Unexpected value: " + menuChoice);
            }
        }
    }
}
package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.Scanner;

public final class CuckooHashMapDemo {

    private CuckooHashMapDemo() {
    }

    public static void main(String[] args) {
        HashMapCuckooHashing cuckooHashMap = new HashMapCuckooHashing(7);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("_________________________");
            System.out.println("Enter your choice:");
            System.out.println("1. Insert key");
            System.out.println("2. Delete key");
            System.out.println("3. Print table");
            System.out.println("4. Exit");
            System.out.println("5. Search and print key index");
            System.out.println("6. Check load factor");
            System.out.println("7. Rehash current table");

            int menuChoice = scanner.nextInt();

            switch (menuChoice) {
                case 1:
                    System.out.println("Enter the key to insert: ");
                    int keyToInsert = scanner.nextInt();
                    cuckooHashMap.insertKey(keyToInsert);
                    break;

                case 2:
                    System.out.println("Enter the key to delete: ");
                    int keyToDelete = scanner.nextInt();
                    cuckooHashMap.deleteKey(keyToDelete);
                    break;

                case 3:
                    System.out.println("Current hash table:\n");
                    cuckooHashMap.printTable();
                    break;

                case 4:
                    scanner.close();
                    return;

                case 5:
                    System.out.println("Enter the key to search: ");
                    int keyToSearch = scanner.nextInt();
                    int keyIndex = cuckooHashMap.findKeyIndex(keyToSearch);
                    System.out.println("Key: " + keyToSearch + " is at index: " + keyIndex + "\n");
                    break;

                case 6:
                    double loadFactor = cuckooHashMap.getLoadFactor();
                    System.out.printf("Current load factor: %.2f%n", loadFactor);
                    break;

                case 7:
                    cuckooHashMap.rehashAndIncreaseTableSize();
                    break;

                default:
                    throw new IllegalArgumentException("Unexpected menu choice: " + menuChoice);
            }
        }
    }
}
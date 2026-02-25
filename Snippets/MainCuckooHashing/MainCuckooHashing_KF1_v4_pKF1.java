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

            int userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    System.out.println("Enter the key to insert: ");
                    int keyToInsert = scanner.nextInt();
                    cuckooHashMap.insertKey2HashTable(keyToInsert);
                    break;

                case 2:
                    System.out.println("Enter the key to delete: ");
                    int keyToDelete = scanner.nextInt();
                    cuckooHashMap.deleteKeyFromHashTable(keyToDelete);
                    break;

                case 3:
                    System.out.println("Current hash table:\n");
                    cuckooHashMap.displayHashtable();
                    break;

                case 4:
                    scanner.close();
                    return;

                case 5:
                    System.out.println("Enter the key to search: ");
                    int keyToSearch = scanner.nextInt();
                    int keyIndex = cuckooHashMap.findKeyInTable(keyToSearch);
                    System.out.println("Key: " + keyToSearch + " is at index: " + keyIndex + "\n");
                    break;

                case 6:
                    double loadFactor = cuckooHashMap.checkLoadFactor();
                    System.out.printf("Current load factor: %.2f%n", loadFactor);
                    break;

                case 7:
                    cuckooHashMap.reHashTableIncreasesTableSize();
                    break;

                default:
                    throw new IllegalArgumentException("Unexpected menu choice: " + userChoice);
            }
        }
    }
}
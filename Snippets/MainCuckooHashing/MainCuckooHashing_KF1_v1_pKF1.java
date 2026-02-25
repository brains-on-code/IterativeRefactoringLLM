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
            System.out.println("Enter your Choice :");
            System.out.println("1. Add Key");
            System.out.println("2. Delete Key");
            System.out.println("3. Print Table");
            System.out.println("4. Exit");
            System.out.println("5. Search and print key index");
            System.out.println("6. Check load factor");
            System.out.println("7. Rehash Current Table");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter the Key: ");
                    int keyToInsert = scanner.nextInt();
                    cuckooHashMap.insertKey2HashTable(keyToInsert);
                    break;

                case 2:
                    System.out.println("Enter the Key to delete:  ");
                    int keyToDelete = scanner.nextInt();
                    cuckooHashMap.deleteKeyFromHashTable(keyToDelete);
                    break;

                case 3:
                    System.out.println("Print table:\n");
                    cuckooHashMap.displayHashtable();
                    break;

                case 4:
                    scanner.close();
                    return;

                case 5:
                    System.out.println("Enter the Key to find and print:  ");
                    int keyToFind = scanner.nextInt();
                    System.out.println(
                        "Key: " + keyToFind + " is at index: " + cuckooHashMap.findKeyInTable(keyToFind) + "\n"
                    );
                    break;

                case 6:
                    System.out.printf("Load factor is: %.2f%n", cuckooHashMap.checkLoadFactor());
                    break;

                case 7:
                    cuckooHashMap.reHashTableIncreasesTableSize();
                    break;

                default:
                    throw new IllegalArgumentException("Unexpected value: " + choice);
            }
        }
    }
}
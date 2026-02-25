package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.Scanner;

public final class Class1 {
    private Class1() {
    }

    public static void method1(String[] var1) {
        int var2;
        int var3;

        HashMapCuckooHashing var4 = new HashMapCuckooHashing(7);
        Scanner var5 = new Scanner(System.in);

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

            var2 = var5.nextInt();

            switch (var2) {
            case 1:
                System.out.println("Enter the Key: ");
                var3 = var5.nextInt();
                var4.insertKey2HashTable(var3);
                break;

            case 2:
                System.out.println("Enter the Key delete:  ");
                var3 = var5.nextInt();
                var4.deleteKeyFromHashTable(var3);
                break;

            case 3:
                System.out.println("Print table:\n");
                var4.displayHashtable();
                break;

            case 4:
                var5.close();
                return;

            case 5:
                System.out.println("Enter the Key to find and print:  ");
                var3 = var5.nextInt();
                System.out.println("Key: " + var3 + " is at index: " + var4.findKeyInTable(var3) + "\n");
                break;

            case 6:
                System.out.printf("Load factor is: %.2f%n", var4.checkLoadFactor());
                break;

            case 7:
                var4.reHashTableIncreasesTableSize();
                break;

            default:
                throw new IllegalArgumentException("Unexpected value: " + var2);
            }
        }
    }
}

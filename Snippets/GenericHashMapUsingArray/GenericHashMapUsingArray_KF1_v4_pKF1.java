package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

public class CustomHashMap<K, V> {

    private int size;
    private LinkedList<HashEntry<K, V>>[] table;

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;

    public CustomHashMap() {
        initializeTable(DEFAULT_CAPACITY);
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private void initializeTable(int capacity) {
        table = new LinkedList[capacity];
        for (int index = 0; index < table.length; index++) {
            table[index] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry<K, V>> bucket = table[bucketIndex];

        for (HashEntry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new HashEntry<>(key, value));
        size++;

        if ((float) size / table.length > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    private int getBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), table.length);
    }

    private void resize() {
        LinkedList<HashEntry<K, V>>[] oldTable = table;
        initializeTable(oldTable.length * 2);
        this.size = 0;

        for (LinkedList<HashEntry<K, V>> bucket : oldTable) {
            for (HashEntry<K, V> entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry<K, V>> bucket = table[bucketIndex];

        HashEntry<K, V> entryToRemove = null;
        for (HashEntry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entryToRemove = entry;
                break;
            }
        }

        if (entryToRemove != null) {
            bucket.remove(entryToRemove);
            size--;
        }
    }

    public int size() {
        return this.size;
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry<K, V>> bucket = table[bucketIndex];
        for (HashEntry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder();
        mapAsString.append("{");
        for (LinkedList<HashEntry<K, V>> bucket : table) {
            for (HashEntry<K, V> entry : bucket) {
                mapAsString.append(entry.key);
                mapAsString.append(" : ");
                mapAsString.append(entry.value);
                mapAsString.append(", ");
            }
        }
        if (mapAsString.length() > 1) {
            mapAsString.setLength(mapAsString.length() - 2);
        }
        mapAsString.append("}");
        return mapAsString.toString();
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public static class HashEntry<K, V> {
        K key;
        V value;

        public HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
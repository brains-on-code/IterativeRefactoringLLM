package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

public class GenericHashMapUsingArray<K, V> {

    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    private int size;
    private LinkedList<MapEntry>[] table;

    public GenericHashMapUsingArray() {
        initializeTable(DEFAULT_INITIAL_CAPACITY);
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
        int index = hash(key);
        LinkedList<MapEntry> bucket = table[index];

        for (MapEntry entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new MapEntry(key, value));
        size++;

        if ((float) size / table.length > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), table.length);
    }

    private void resize() {
        LinkedList<MapEntry>[] oldTable = table;
        initializeTable(oldTable.length * 2);
        this.size = 0;

        for (LinkedList<MapEntry> bucket : oldTable) {
            for (MapEntry entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    public void remove(K key) {
        int index = hash(key);
        LinkedList<MapEntry> bucket = table[index];

        MapEntry entryToRemove = null;
        for (MapEntry entry : bucket) {
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
        int index = hash(key);
        LinkedList<MapEntry> bucket = table[index];

        for (MapEntry entry : bucket) {
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

        for (LinkedList<MapEntry> bucket : table) {
            for (MapEntry entry : bucket) {
                mapAsString.append(entry.key)
                           .append(" : ")
                           .append(entry.value)
                           .append(", ");
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

    public class MapEntry {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
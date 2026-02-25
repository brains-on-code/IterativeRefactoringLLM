package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;

public class LinearProbingHashMap<Key extends Comparable<Key>, Value> extends Map<Key, Value> {
    private int tableCapacity;
    private Key[] keyTable;
    private Value[] valueTable;
    private int entryCount;

    public LinearProbingHashMap() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public LinearProbingHashMap(int initialCapacity) {
        this.tableCapacity = initialCapacity;
        keyTable = (Key[]) new Comparable[initialCapacity];
        valueTable = (Value[]) new Object[initialCapacity];
    }

    @Override
    public boolean put(Key key, Value value) {
        if (key == null) {
            return false;
        }

        if (entryCount > tableCapacity / 2) {
            resize(2 * tableCapacity);
        }

        int index = hash(key, tableCapacity);
        for (; keyTable[index] != null; index = nextIndex(index)) {
            if (key.equals(keyTable[index])) {
                valueTable[index] = value;
                return true;
            }
        }

        keyTable[index] = key;
        valueTable[index] = value;
        entryCount++;
        return true;
    }

    @Override
    public Value get(Key key) {
        if (key == null) {
            return null;
        }

        for (int index = hash(key, tableCapacity); keyTable[index] != null; index = nextIndex(index)) {
            if (key.equals(keyTable[index])) {
                return valueTable[index];
            }
        }

        return null;
    }

    @Override
    public boolean delete(Key key) {
        if (key == null || !contains(key)) {
            return false;
        }

        int index = hash(key, tableCapacity);
        while (!key.equals(keyTable[index])) {
            index = nextIndex(index);
        }

        keyTable[index] = null;
        valueTable[index] = null;

        index = nextIndex(index);
        while (keyTable[index] != null) {
            Key keyToRehash = keyTable[index];
            Value valueToRehash = valueTable[index];
            keyTable[index] = null;
            valueTable[index] = null;
            entryCount--;
            put(keyToRehash, valueToRehash);
            index = nextIndex(index);
        }

        entryCount--;
        if (entryCount > 0 && entryCount <= tableCapacity / 8) {
            resize(tableCapacity / 2);
        }

        return true;
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    int size() {
        return entryCount;
    }

    @Override
    Iterable<Key> keys() {
        ArrayList<Key> keyList = new ArrayList<>(entryCount);
        for (int index = 0; index < tableCapacity; index++) {
            if (keyTable[index] != null) {
                keyList.add(keyTable[index]);
            }
        }

        keyList.sort(Comparable::compareTo);
        return keyList;
    }

    private int nextIndex(int currentIndex) {
        return (currentIndex + 1) % tableCapacity;
    }

    private void resize(int newCapacity) {
        LinearProbingHashMap<Key, Value> resizedMap = new LinearProbingHashMap<>(newCapacity);
        for (int index = 0; index < tableCapacity; index++) {
            if (keyTable[index] != null) {
                resizedMap.put(keyTable[index], valueTable[index]);
            }
        }

        this.keyTable = resizedMap.keyTable;
        this.valueTable = resizedMap.valueTable;
        this.tableCapacity = newCapacity;
    }
}
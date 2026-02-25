package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;

/**
 * Hash table implementation using linear probing for collision resolution.
 *
 * @param <Key>   the type of keys maintained by this map
 * @param <Value> the type of mapped values
 */
public class LinearProbingHashMap<Key extends Comparable<Key>, Value> extends Map<Key, Value> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double UPPER_LOAD_FACTOR = 0.5;
    private static final double LOWER_LOAD_FACTOR = 0.125;

    private int capacity; // size of the hash table
    private Key[] keys; // array to store keys
    private Value[] values; // array to store values
    private int size; // number of elements in the hash table

    public LinearProbingHashMap() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public LinearProbingHashMap(int initialCapacity) {
        this.capacity = initialCapacity;
        this.keys = (Key[]) new Comparable[initialCapacity];
        this.values = (Value[]) new Object[initialCapacity];
    }

    @Override
    public boolean put(Key key, Value value) {
        if (key == null) {
            return false;
        }

        if (size >= capacity * UPPER_LOAD_FACTOR) {
            resize(capacity * 2);
        }

        int index = hash(key, capacity);
        while (keys[index] != null) {
            if (key.equals(keys[index])) {
                values[index] = value;
                return true;
            }
            index = increment(index);
        }

        keys[index] = key;
        values[index] = value;
        size++;
        return true;
    }

    @Override
    public Value get(Key key) {
        if (key == null) {
            return null;
        }

        for (int index = hash(key, capacity); keys[index] != null; index = increment(index)) {
            if (key.equals(keys[index])) {
                return values[index];
            }
        }

        return null;
    }

    @Override
    public boolean delete(Key key) {
        if (key == null || !contains(key)) {
            return false;
        }

        int index = hash(key, capacity);
        while (!key.equals(keys[index])) {
            index = increment(index);
        }

        keys[index] = null;
        values[index] = null;

        index = increment(index);
        while (keys[index] != null) {
            Key keyToRehash = keys[index];
            Value valueToRehash = values[index];
            keys[index] = null;
            values[index] = null;
            size--;
            put(keyToRehash, valueToRehash);
            index = increment(index);
        }

        size--;
        if (size > 0 && size <= capacity * LOWER_LOAD_FACTOR) {
            resize(capacity / 2);
        }

        return true;
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    int size() {
        return size;
    }

    @Override
    Iterable<Key> keys() {
        ArrayList<Key> listOfKeys = new ArrayList<>(size);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                listOfKeys.add(keys[i]);
            }
        }
        listOfKeys.sort(Comparable::compareTo);
        return listOfKeys;
    }

    private int increment(int index) {
        return (index + 1) % capacity;
    }

    private void resize(int newCapacity) {
        LinearProbingHashMap<Key, Value> temp = new LinearProbingHashMap<>(newCapacity);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], values[i]);
            }
        }

        this.keys = temp.keys;
        this.values = temp.values;
        this.capacity = newCapacity;
    }
}
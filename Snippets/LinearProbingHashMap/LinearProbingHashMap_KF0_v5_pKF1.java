package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;

/**
 * This class implements a hash table using linear probing to resolve collisions.
 * Linear probing is a collision resolution method where each slot in the hash table is checked in a sequential manner
 * until an empty slot is found.
 *
 * <p>
 * The class allows for storing key-value pairs, where both the key and value are generic types.
 * The key must be of a type that implements the Comparable interface to ensure that the keys can be compared for sorting.
 * </p>
 *
 * <p>
 * This implementation supports basic operations such as:
 * <ul>
 *     <li><b>put(Key key, Value value)</b>: Adds a key-value pair to the hash table. If the key already exists, its value is updated.</li>
 *     <li><b>get(Key key)</b>: Retrieves the value associated with the given key.</li>
 *     <li><b>delete(Key key)</b>: Removes the key and its associated value from the hash table.</li>
 *     <li><b>contains(Key key)</b>: Checks if the hash table contains a given key.</li>
 *     <li><b>size()</b>: Returns the number of key-value pairs in the hash table.</li>
 *     <li><b>keys()</b>: Returns an iterable collection of keys stored in the hash table.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The internal size of the hash table is automatically resized when the load factor exceeds 0.5 or falls below 0.125,
 * ensuring efficient space utilization.
 * </p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Linear_probing">Linear Probing Hash Table</a>
 *
 * @param <Key> the type of keys maintained by this map
 * @param <Value> the type of mapped values
 */
public class LinearProbingHashMap<Key extends Comparable<Key>, Value> extends Map<Key, Value> {
    private int capacity; // size of the hash table
    private Key[] keys; // array to store keys
    private Value[] values; // array to store values
    private int size; // number of elements in the hash table

    // Default constructor initializes the table with a default size of 16
    public LinearProbingHashMap() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    // Constructor to initialize the hash table with a specified size
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

        if (size > capacity / 2) {
            resize(2 * capacity);
        }

        int index = hash(key, capacity);
        for (; keys[index] != null; index = nextIndex(index)) {
            if (key.equals(keys[index])) {
                values[index] = value;
                return true;
            }
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

        for (int index = hash(key, capacity); keys[index] != null; index = nextIndex(index)) {
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
            index = nextIndex(index);
        }

        keys[index] = null;
        values[index] = null;

        index = nextIndex(index);
        while (keys[index] != null) {
            Key keyToRehash = keys[index];
            Value valueToRehash = values[index];
            keys[index] = null;
            values[index] = null;
            size--;
            put(keyToRehash, valueToRehash);
            index = nextIndex(index);
        }

        size--;
        if (size > 0 && size <= capacity / 8) {
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
        ArrayList<Key> keyList = new ArrayList<>(size);
        for (int index = 0; index < capacity; index++) {
            if (keys[index] != null) {
                keyList.add(keys[index]);
            }
        }

        keyList.sort(Comparable::compareTo);
        return keyList;
    }

    private int nextIndex(int currentIndex) {
        return (currentIndex + 1) % capacity;
    }

    private void resize(int newCapacity) {
        LinearProbingHashMap<Key, Value> resizedMap = new LinearProbingHashMap<>(newCapacity);
        for (int index = 0; index < capacity; index++) {
            if (keys[index] != null) {
                resizedMap.put(keys[index], values[index]);
            }
        }

        this.keys = resizedMap.keys;
        this.values = resizedMap.values;
        this.capacity = newCapacity;
    }
}
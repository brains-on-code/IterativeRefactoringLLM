package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;

/**
 * Hash table implementation using linear probing for collision resolution.
 *
 * <p>Supports basic operations:
 * <ul>
 *     <li>{@code put(Key key, Value value)}</li>
 *     <li>{@code get(Key key)}</li>
 *     <li>{@code delete(Key key)}</li>
 *     <li>{@code contains(Key key)}</li>
 *     <li>{@code size()}</li>
 *     <li>{@code keys()}</li>
 * </ul>
 *
 * <p>The table resizes when the load factor exceeds 0.5 or falls below 0.125.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Linear_probing">Linear Probing Hash Table</a>
 *
 * @param <Key>   the type of keys maintained by this map
 * @param <Value> the type of mapped values
 */
public class LinearProbingHashMap<Key extends Comparable<Key>, Value> extends Map<Key, Value> {

    /** Current capacity of the hash table (array length). */
    private int capacity;

    /** Array storing keys. */
    private Key[] keys;

    /** Array storing values corresponding to {@link #keys}. */
    private Value[] values;

    /** Number of key-value pairs stored. */
    private int size;

    /** Creates a hash table with default initial capacity (16). */
    public LinearProbingHashMap() {
        this(16);
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

        if (size > capacity / 2) {
            resize(2 * capacity);
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

        for (int i = hash(key, capacity); keys[i] != null; i = increment(i)) {
            if (key.equals(keys[i])) {
                return values[i];
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
        ArrayList<Key> listOfKeys = new ArrayList<>(size);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                listOfKeys.add(keys[i]);
            }
        }
        listOfKeys.sort(Comparable::compareTo);
        return listOfKeys;
    }

    /** Returns the next index in the probe sequence, wrapping around the table. */
    private int increment(int index) {
        return (index + 1) % capacity;
    }

    /** Resizes the underlying arrays to {@code newCapacity} and rehashes all keys. */
    private void resize(int newCapacity) {
        LinearProbingHashMap<Key, Value> tmp = new LinearProbingHashMap<>(newCapacity);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                tmp.put(keys[i], values[i]);
            }
        }

        this.keys = tmp.keys;
        this.values = tmp.values;
        this.capacity = newCapacity;
    }
}
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

    /** Default initial capacity. */
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /** Load factor threshold to grow (0.5). */
    private static final double UPPER_LOAD_FACTOR = 0.5;

    /** Load factor threshold to shrink (0.125). */
    private static final double LOWER_LOAD_FACTOR = 0.125;

    /** Creates a hash table with default initial capacity. */
    public LinearProbingHashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
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

        ensureCapacityForInsert();

        int index = findSlotForKey(key);
        if (keys[index] != null) {
            values[index] = value;
            return true;
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

        int index = findExistingKeyIndex(key);
        return index == -1 ? null : values[index];
    }

    @Override
    public boolean delete(Key key) {
        if (key == null || !contains(key)) {
            return false;
        }

        int index = findExistingKeyIndex(key);
        if (index == -1) {
            return false;
        }

        removeEntryAt(index);
        rehashClusterStartingAt(increment(index));
        size--;

        ensureCapacityForDelete();
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

    /** Ensures the table has enough capacity before inserting a new element. */
    private void ensureCapacityForInsert() {
        if (size >= capacity * UPPER_LOAD_FACTOR) {
            resize(2 * capacity);
        }
    }

    /** Ensures the table is not too sparse after a deletion. */
    private void ensureCapacityForDelete() {
        if (size > 0 && size <= capacity * LOWER_LOAD_FACTOR) {
            resize(capacity / 2);
        }
    }

    /**
     * Finds the index of the given key if it exists, or the first empty slot
     * where it could be inserted.
     */
    private int findSlotForKey(Key key) {
        int index = hash(key, capacity);
        while (keys[index] != null && !key.equals(keys[index])) {
            index = increment(index);
        }
        return index;
    }

    /**
     * Finds the index of an existing key.
     *
     * @return index of the key, or -1 if not found
     */
    private int findExistingKeyIndex(Key key) {
        for (int i = hash(key, capacity); keys[i] != null; i = increment(i)) {
            if (key.equals(keys[i])) {
                return i;
            }
        }
        return -1;
    }

    /** Removes the key-value pair at the specified index. */
    private void removeEntryAt(int index) {
        keys[index] = null;
        values[index] = null;
    }

    /**
     * Rehashes all keys in the cluster starting from the given index until a
     * null slot is found.
     */
    private void rehashClusterStartingAt(int startIndex) {
        int index = startIndex;
        while (keys[index] != null) {
            Key keyToRehash = keys[index];
            Value valueToRehash = values[index];
            keys[index] = null;
            values[index] = null;
            size--;
            put(keyToRehash, valueToRehash);
            index = increment(index);
        }
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
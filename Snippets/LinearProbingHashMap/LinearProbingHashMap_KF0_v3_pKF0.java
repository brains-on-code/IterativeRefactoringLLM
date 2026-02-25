package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.List;

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

    private int capacity;
    private Key[] keys;
    private Value[] values;
    private int size;

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

        ensureCapacityForInsert();

        int index = findSlotForKey(key);
        if (keys[index] != null) {
            values[index] = value;
        } else {
            keys[index] = key;
            values[index] = value;
            size++;
        }
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
        if (key == null) {
            return false;
        }

        int index = findExistingKeyIndex(key);
        if (index == -1) {
            return false;
        }

        removeEntryAt(index);
        rehashClusterFrom(nextIndex(index));
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
        List<Key> listOfKeys = new ArrayList<>(size);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                listOfKeys.add(keys[i]);
            }
        }
        listOfKeys.sort(Comparable::compareTo);
        return listOfKeys;
    }

    private int nextIndex(int index) {
        return (index + 1) % capacity;
    }

    private void ensureCapacityForInsert() {
        if (size >= capacity * UPPER_LOAD_FACTOR) {
            resize(capacity * 2);
        }
    }

    private void ensureCapacityForDelete() {
        if (size > 0 && size <= capacity * LOWER_LOAD_FACTOR) {
            resize(capacity / 2);
        }
    }

    private int findSlotForKey(Key key) {
        int index = hash(key, capacity);
        while (keys[index] != null && !key.equals(keys[index])) {
            index = nextIndex(index);
        }
        return index;
    }

    private int findExistingKeyIndex(Key key) {
        int index = hash(key, capacity);
        while (keys[index] != null) {
            if (key.equals(keys[index])) {
                return index;
            }
            index = nextIndex(index);
        }
        return -1;
    }

    private void removeEntryAt(int index) {
        keys[index] = null;
        values[index] = null;
    }

    private void rehashClusterFrom(int startIndex) {
        int index = startIndex;
        while (keys[index] != null) {
            Key keyToRehash = keys[index];
            Value valueToRehash = values[index];

            removeEntryAt(index);
            size--;

            put(keyToRehash, valueToRehash);
            index = nextIndex(index);
        }
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
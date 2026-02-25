package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

public class GenericHashMapUsingArray<K, V> {

    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    private int size;
    private LinkedList<Entry>[] buckets;

    public GenericHashMapUsingArray() {
        initializeBuckets(DEFAULT_INITIAL_CAPACITY);
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private void initializeBuckets(int capacity) {
        buckets = new LinkedList[capacity];
        for (int bucketIndex = 0; bucketIndex < buckets.length; bucketIndex++) {
            buckets[bucketIndex] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        int bucketIndex = hash(key);
        LinkedList<Entry> bucket = buckets[bucketIndex];

        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry(key, value));
        size++;

        if ((float) size / buckets.length > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private void resize() {
        LinkedList<Entry>[] oldBuckets = buckets;
        initializeBuckets(oldBuckets.length * 2);
        this.size = 0;

        for (LinkedList<Entry> bucket : oldBuckets) {
            for (Entry entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    public void remove(K key) {
        int bucketIndex = hash(key);
        LinkedList<Entry> bucket = buckets[bucketIndex];

        Entry entryToRemove = null;
        for (Entry entry : bucket) {
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
        int bucketIndex = hash(key);
        LinkedList<Entry> bucket = buckets[bucketIndex];

        for (Entry entry : bucket) {
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

        for (LinkedList<Entry> bucket : buckets) {
            for (Entry entry : bucket) {
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

    public class Entry {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;

public class GenericHashMapUsingArrayList<K, V> {

    private static final int INITIAL_CAPACITY = 10;
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f;

    private ArrayList<LinkedList<HashEntry>> buckets;
    private int entryCount;

    public GenericHashMapUsingArrayList() {
        buckets = new ArrayList<>();
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets.add(new LinkedList<>());
        }
        entryCount = 0;
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry> bucket = buckets.get(bucketIndex);

        for (HashEntry hashEntry : bucket) {
            if (hashEntry.key.equals(key)) {
                hashEntry.value = value;
                return;
            }
        }

        bucket.add(new HashEntry(key, value));
        entryCount++;

        if ((float) entryCount / buckets.size() > LOAD_FACTOR_THRESHOLD) {
            resizeAndRehash();
        }
    }

    private void resizeAndRehash() {
        ArrayList<LinkedList<HashEntry>> oldBuckets = buckets;
        buckets = new ArrayList<>();
        entryCount = 0;

        int newCapacity = oldBuckets.size() * 2;
        for (int i = 0; i < newCapacity; i++) {
            buckets.add(new LinkedList<>());
        }

        for (LinkedList<HashEntry> bucket : oldBuckets) {
            for (HashEntry hashEntry : bucket) {
                put(hashEntry.key, hashEntry.value);
            }
        }
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry> bucket = buckets.get(bucketIndex);

        for (HashEntry hashEntry : bucket) {
            if (hashEntry.key.equals(key)) {
                return hashEntry.value;
            }
        }

        return null;
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry> bucket = buckets.get(bucketIndex);

        HashEntry entryToRemove = null;
        for (HashEntry hashEntry : bucket) {
            if (hashEntry.key.equals(key)) {
                entryToRemove = hashEntry;
                break;
            }
        }

        if (entryToRemove != null) {
            bucket.remove(entryToRemove);
            entryCount--;
        }
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return this.entryCount;
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % buckets.size());
    }

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder();
        mapAsString.append("{");

        for (LinkedList<HashEntry> bucket : buckets) {
            for (HashEntry hashEntry : bucket) {
                mapAsString.append(hashEntry.key);
                mapAsString.append(" : ");
                mapAsString.append(hashEntry.value);
                mapAsString.append(", ");
            }
        }

        if (mapAsString.length() > 1) {
            mapAsString.setLength(mapAsString.length() - 2);
        }

        mapAsString.append("}");
        return mapAsString.toString();
    }

    private class HashEntry {
        K key;
        V value;

        HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomHashMap<K, V> {

    private static final int INITIAL_CAPACITY = 10;
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f;

    private List<LinkedList<HashEntry>> bucketList;
    private int size;

    public CustomHashMap() {
        bucketList = new ArrayList<>();
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            bucketList.add(new LinkedList<>());
        }
        size = 0;
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry> bucket = bucketList.get(bucketIndex);

        for (HashEntry hashEntry : bucket) {
            if (hashEntry.key.equals(key)) {
                hashEntry.value = value;
                return;
            }
        }

        bucket.add(new HashEntry(key, value));
        size++;

        if ((float) size / bucketList.size() > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry> bucket = bucketList.get(bucketIndex);

        for (HashEntry hashEntry : bucket) {
            if (hashEntry.key.equals(key)) {
                return hashEntry.value;
            }
        }
        return null;
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry> bucket = bucketList.get(bucketIndex);

        HashEntry entryToRemove = null;
        for (HashEntry hashEntry : bucket) {
            if (hashEntry.key.equals(key)) {
                entryToRemove = hashEntry;
                break;
            }
        }

        if (entryToRemove != null) {
            bucket.remove(entryToRemove);
            size--;
        }
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return this.size;
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % bucketList.size());
    }

    private void resize() {
        List<LinkedList<HashEntry>> oldBucketList = bucketList;
        bucketList = new ArrayList<>();
        size = 0;

        int newCapacity = oldBucketList.size() * 2;
        for (int i = 0; i < newCapacity; i++) {
            bucketList.add(new LinkedList<>());
        }

        for (LinkedList<HashEntry> bucket : oldBucketList) {
            for (HashEntry hashEntry : bucket) {
                put(hashEntry.key, hashEntry.value);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder();
        mapAsString.append("{");
        for (LinkedList<HashEntry> bucket : bucketList) {
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
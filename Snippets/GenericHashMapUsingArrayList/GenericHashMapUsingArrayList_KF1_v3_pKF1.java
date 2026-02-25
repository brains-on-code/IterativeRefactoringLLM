package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomHashMap<K, V> {

    private static final int INITIAL_CAPACITY = 10;
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f;

    private List<LinkedList<Entry>> buckets;
    private int size;

    public CustomHashMap() {
        buckets = new ArrayList<>();
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets.add(new LinkedList<>());
        }
        size = 0;
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry(key, value));
        size++;

        if ((float) size / buckets.size() > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

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

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return this.size;
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % buckets.size());
    }

    private void resize() {
        List<LinkedList<Entry>> oldBuckets = buckets;
        buckets = new ArrayList<>();
        size = 0;

        int newCapacity = oldBuckets.size() * 2;
        for (int i = 0; i < newCapacity; i++) {
            buckets.add(new LinkedList<>());
        }

        for (LinkedList<Entry> bucket : oldBuckets) {
            for (Entry entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder();
        mapAsString.append("{");
        for (LinkedList<Entry> bucket : buckets) {
            for (Entry entry : bucket) {
                mapAsString.append(entry.key);
                mapAsString.append(" : ");
                mapAsString.append(entry.value);
                mapAsString.append(", ");
            }
        }
        if (mapAsString.length() > 1) {
            mapAsString.setLength(mapAsString.length() - 2);
        }
        mapAsString.append("}");
        return mapAsString.toString();
    }

    private class Entry {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
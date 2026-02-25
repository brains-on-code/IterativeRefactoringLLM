package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

public class GenericHashMapUsingArray<K, V> {

    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;

    private int size;
    private LinkedList<Entry>[] bucketArray;

    public GenericHashMapUsingArray() {
        initializeBuckets(16);
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private void initializeBuckets(int capacity) {
        bucketArray = new LinkedList[capacity];
        for (int index = 0; index < bucketArray.length; index++) {
            bucketArray[index] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        int bucketIndex = hash(key);
        LinkedList<Entry> bucket = bucketArray[bucketIndex];

        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry(key, value));
        size++;

        if ((float) size / bucketArray.length > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), bucketArray.length);
    }

    private void resize() {
        LinkedList<Entry>[] oldBucketArray = bucketArray;
        initializeBuckets(oldBucketArray.length * 2);
        this.size = 0;

        for (LinkedList<Entry> bucket : oldBucketArray) {
            for (Entry entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    public void remove(K key) {
        int bucketIndex = hash(key);
        LinkedList<Entry> bucket = bucketArray[bucketIndex];

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
        LinkedList<Entry> bucket = bucketArray[bucketIndex];

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

        for (LinkedList<Entry> bucket : bucketArray) {
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
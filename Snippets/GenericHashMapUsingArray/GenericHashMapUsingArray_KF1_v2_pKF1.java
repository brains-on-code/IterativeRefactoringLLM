package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

public class CustomHashMap<K, V> {

    private int elementCount;
    private LinkedList<HashEntry<K, V>>[] bucketArray;

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;

    public CustomHashMap() {
        initializeBucketArray(DEFAULT_CAPACITY);
        elementCount = 0;
    }

    @SuppressWarnings("unchecked")
    private void initializeBucketArray(int capacity) {
        bucketArray = new LinkedList[capacity];
        for (int index = 0; index < bucketArray.length; index++) {
            bucketArray[index] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry<K, V>> bucket = bucketArray[bucketIndex];

        for (HashEntry<K, V> hashEntry : bucket) {
            if (hashEntry.key.equals(key)) {
                hashEntry.value = value;
                return;
            }
        }

        bucket.add(new HashEntry<>(key, value));
        elementCount++;

        if ((float) elementCount / bucketArray.length > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    private int getBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), bucketArray.length);
    }

    private void resize() {
        LinkedList<HashEntry<K, V>>[] oldBucketArray = bucketArray;
        initializeBucketArray(oldBucketArray.length * 2);
        this.elementCount = 0;

        for (LinkedList<HashEntry<K, V>> bucket : oldBucketArray) {
            for (HashEntry<K, V> hashEntry : bucket) {
                put(hashEntry.key, hashEntry.value);
            }
        }
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry<K, V>> bucket = bucketArray[bucketIndex];

        HashEntry<K, V> entryToRemove = null;
        for (HashEntry<K, V> hashEntry : bucket) {
            if (hashEntry.key.equals(key)) {
                entryToRemove = hashEntry;
                break;
            }
        }

        if (entryToRemove != null) {
            bucket.remove(entryToRemove);
            elementCount--;
        }
    }

    public int size() {
        return this.elementCount;
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry<K, V>> bucket = bucketArray[bucketIndex];
        for (HashEntry<K, V> hashEntry : bucket) {
            if (hashEntry.key.equals(key)) {
                return hashEntry.value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder();
        mapAsString.append("{");
        for (LinkedList<HashEntry<K, V>> bucket : bucketArray) {
            for (HashEntry<K, V> hashEntry : bucket) {
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

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public static class HashEntry<K, V> {
        K key;
        V value;

        public HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
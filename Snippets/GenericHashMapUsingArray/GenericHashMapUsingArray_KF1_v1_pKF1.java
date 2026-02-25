package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

public class CustomHashMap<K, V> {

    private int size;
    private LinkedList<Entry<K, V>>[] buckets;

    public CustomHashMap() {
        initializeBuckets(16);
        size = 0;
    }

    private void initializeBuckets(int capacity) {
        buckets = new LinkedList[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry<>(key, value));
        size++;

        float loadFactorThreshold = 0.75f;
        if ((float) size / buckets.length > loadFactorThreshold) {
            resize();
        }
    }

    private int getBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private void resize() {
        LinkedList<Entry<K, V>>[] oldBuckets = buckets;
        initializeBuckets(oldBuckets.length * 2);
        this.size = 0;

        for (LinkedList<Entry<K, V>> bucket : oldBuckets) {
            for (Entry<K, V> entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        Entry<K, V> entryToRemove = null;
        for (Entry<K, V> entry : bucket) {
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
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                builder.append(entry.key);
                builder.append(" : ");
                builder.append(entry.value);
                builder.append(", ");
            }
        }
        if (builder.length() > 1) {
            builder.setLength(builder.length() - 2);
        }
        builder.append("}");
        return builder.toString();
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

/**
 * A simple generic hash map implementation using separate chaining with linked lists.
 */
public class SimpleHashMap<K, V> {

    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;

    private int size;
    private LinkedList<Entry<K, V>>[] buckets;

    @SuppressWarnings("unchecked")
    public SimpleHashMap() {
        initializeBuckets(16);
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private void initializeBuckets(int capacity) {
        buckets = new LinkedList[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        int index = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry<>(key, value));
        size++;

        if ((float) size / buckets.length > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    private int getBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private void resize() {
        LinkedList<Entry<K, V>>[] oldBuckets = buckets;
        initializeBuckets(oldBuckets.length * 2);
        size = 0;

        for (LinkedList<Entry<K, V>> bucket : oldBuckets) {
            for (Entry<K, V> entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    public void remove(K key) {
        int index = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];

        Entry<K, V> toRemove = null;
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                toRemove = entry;
                break;
            }
        }

        if (toRemove != null) {
            bucket.remove(toRemove);
            size--;
        }
    }

    public int size() {
        return size;
    }

    public V get(K key) {
        int index = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];

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
                builder.append(entry.key)
                       .append(" : ")
                       .append(entry.value)
                       .append(", ");
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

    public static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
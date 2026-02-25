package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;

public class CustomHashMap<K, V> {

    private ArrayList<LinkedList<Entry>> buckets;
    private int size;

    public CustomHashMap() {
        buckets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            buckets.add(new LinkedList<>());
        }
        size = 0;
    }

    public void put(K key, V value) {
        int bucketIndex = Math.abs(key.hashCode() % buckets.size());
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry(key, value));
        size++;

        float loadFactorThreshold = 0.5f;
        if ((float) size / buckets.size() > loadFactorThreshold) {
            resize();
        }
    }

    private void resize() {
        ArrayList<LinkedList<Entry>> oldBuckets = buckets;
        buckets = new ArrayList<>();
        size = 0;

        for (int i = 0; i < oldBuckets.size() * 2; i++) {
            buckets.add(new LinkedList<>());
        }

        for (LinkedList<Entry> bucket : oldBuckets) {
            for (Entry entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    public V get(K key) {
        int bucketIndex = Math.abs(key.hashCode() % buckets.size());
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public void remove(K key) {
        int bucketIndex = Math.abs(key.hashCode() % buckets.size());
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (LinkedList<Entry> bucket : buckets) {
            for (Entry entry : bucket) {
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

    private class Entry {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
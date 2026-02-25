package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomHashMap<K, V> {

    private static final int INITIAL_CAPACITY = 10;
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f;

    private List<LinkedList<MapEntry>> bucketList;
    private int currentSize;

    public CustomHashMap() {
        bucketList = new ArrayList<>();
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            bucketList.add(new LinkedList<>());
        }
        currentSize = 0;
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<MapEntry> bucket = bucketList.get(bucketIndex);

        for (MapEntry mapEntry : bucket) {
            if (mapEntry.key.equals(key)) {
                mapEntry.value = value;
                return;
            }
        }

        bucket.add(new MapEntry(key, value));
        currentSize++;

        if ((float) currentSize / bucketList.size() > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<MapEntry> bucket = bucketList.get(bucketIndex);

        for (MapEntry mapEntry : bucket) {
            if (mapEntry.key.equals(key)) {
                return mapEntry.value;
            }
        }
        return null;
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<MapEntry> bucket = bucketList.get(bucketIndex);

        MapEntry entryToRemove = null;
        for (MapEntry mapEntry : bucket) {
            if (mapEntry.key.equals(key)) {
                entryToRemove = mapEntry;
                break;
            }
        }

        if (entryToRemove != null) {
            bucket.remove(entryToRemove);
            currentSize--;
        }
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return this.currentSize;
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % bucketList.size());
    }

    private void resize() {
        List<LinkedList<MapEntry>> oldBucketList = bucketList;
        bucketList = new ArrayList<>();
        currentSize = 0;

        int newCapacity = oldBucketList.size() * 2;
        for (int i = 0; i < newCapacity; i++) {
            bucketList.add(new LinkedList<>());
        }

        for (LinkedList<MapEntry> bucket : oldBucketList) {
            for (MapEntry mapEntry : bucket) {
                put(mapEntry.key, mapEntry.value);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder();
        mapAsString.append("{");
        for (LinkedList<MapEntry> bucket : bucketList) {
            for (MapEntry mapEntry : bucket) {
                mapAsString.append(mapEntry.key);
                mapAsString.append(" : ");
                mapAsString.append(mapEntry.value);
                mapAsString.append(", ");
            }
        }
        if (mapAsString.length() > 1) {
            mapAsString.setLength(mapAsString.length() - 2);
        }
        mapAsString.append("}");
        return mapAsString.toString();
    }

    private class MapEntry {
        K key;
        V value;

        MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
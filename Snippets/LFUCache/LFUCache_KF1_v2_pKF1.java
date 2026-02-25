package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, V> {

    private class CacheEntry {
        private final K key;
        private V value;
        private int accessFrequency;
        private CacheEntry previous;
        private CacheEntry next;

        CacheEntry(K key, V value, int accessFrequency) {
            this.key = key;
            this.value = value;
            this.accessFrequency = accessFrequency;
        }
    }

    private CacheEntry leastFrequentEntry;
    private CacheEntry mostFrequentEntry;
    private final Map<K, CacheEntry> entriesByKey;
    private final int capacity;
    private static final int DEFAULT_CAPACITY = 100;

    public LFUCache() {
        this(DEFAULT_CAPACITY);
    }

    public LFUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.entriesByKey = new HashMap<>();
    }

    public V get(K key) {
        CacheEntry entry = entriesByKey.get(key);
        if (entry == null) {
            return null;
        }
        removeEntry(entry);
        entry.accessFrequency++;
        insertEntryByFrequency(entry);
        return entry.value;
    }

    public void put(K key, V value) {
        if (entriesByKey.containsKey(key)) {
            CacheEntry existingEntry = entriesByKey.get(key);
            existingEntry.value = value;
            existingEntry.accessFrequency++;
            removeEntry(existingEntry);
            insertEntryByFrequency(existingEntry);
        } else {
            if (entriesByKey.size() >= capacity) {
                entriesByKey.remove(leastFrequentEntry.key);
                removeEntry(leastFrequentEntry);
            }
            CacheEntry newEntry = new CacheEntry(key, value, 1);
            insertEntryByFrequency(newEntry);
            entriesByKey.put(key, newEntry);
        }
    }

    private void insertEntryByFrequency(CacheEntry entry) {
        if (mostFrequentEntry != null && leastFrequentEntry != null) {
            CacheEntry current = leastFrequentEntry;
            while (current != null) {
                if (current.accessFrequency > entry.accessFrequency) {
                    if (current == leastFrequentEntry) {
                        entry.next = current;
                        current.previous = entry;
                        leastFrequentEntry = entry;
                        break;
                    } else {
                        entry.next = current;
                        entry.previous = current.previous;
                        current.previous.next = entry;
                        current.previous = entry;
                        break;
                    }
                } else {
                    current = current.next;
                    if (current == null) {
                        mostFrequentEntry.next = entry;
                        entry.previous = mostFrequentEntry;
                        entry.next = null;
                        mostFrequentEntry = entry;
                        break;
                    }
                }
            }
        } else {
            mostFrequentEntry = entry;
            leastFrequentEntry = mostFrequentEntry;
        }
    }

    private void removeEntry(CacheEntry entry) {
        if (entry.previous != null) {
            entry.previous.next = entry.next;
        } else {
            leastFrequentEntry = entry.next;
        }

        if (entry.next != null) {
            entry.next.previous = entry.previous;
        } else {
            mostFrequentEntry = entry.previous;
        }
    }
}
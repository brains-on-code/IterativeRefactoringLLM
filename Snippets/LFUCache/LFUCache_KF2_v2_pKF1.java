package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, V> {

    private class CacheEntry {
        private final K key;
        private V value;
        private int accessFrequency;
        private CacheEntry previousEntry;
        private CacheEntry nextEntry;

        CacheEntry(K key, V value, int accessFrequency) {
            this.key = key;
            this.value = value;
            this.accessFrequency = accessFrequency;
        }
    }

    private CacheEntry leastFrequentEntry;
    private CacheEntry mostFrequentEntry;
    private final Map<K, CacheEntry> cacheEntriesByKey;
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
        this.cacheEntriesByKey = new HashMap<>();
    }

    public V get(K key) {
        CacheEntry cacheEntry = cacheEntriesByKey.get(key);
        if (cacheEntry == null) {
            return null;
        }
        removeEntry(cacheEntry);
        cacheEntry.accessFrequency++;
        insertEntryByFrequency(cacheEntry);
        return cacheEntry.value;
    }

    public void put(K key, V value) {
        if (cacheEntriesByKey.containsKey(key)) {
            CacheEntry existingEntry = cacheEntriesByKey.get(key);
            existingEntry.value = value;
            existingEntry.accessFrequency++;
            removeEntry(existingEntry);
            insertEntryByFrequency(existingEntry);
        } else {
            if (cacheEntriesByKey.size() >= capacity) {
                cacheEntriesByKey.remove(leastFrequentEntry.key);
                removeEntry(leastFrequentEntry);
            }
            CacheEntry newEntry = new CacheEntry(key, value, 1);
            insertEntryByFrequency(newEntry);
            cacheEntriesByKey.put(key, newEntry);
        }
    }

    private void insertEntryByFrequency(CacheEntry entryToInsert) {
        if (mostFrequentEntry != null && leastFrequentEntry != null) {
            CacheEntry currentEntry = leastFrequentEntry;
            while (currentEntry != null) {
                if (currentEntry.accessFrequency > entryToInsert.accessFrequency) {
                    if (currentEntry == leastFrequentEntry) {
                        entryToInsert.nextEntry = currentEntry;
                        currentEntry.previousEntry = entryToInsert;
                        leastFrequentEntry = entryToInsert;
                        break;
                    } else {
                        entryToInsert.nextEntry = currentEntry;
                        entryToInsert.previousEntry = currentEntry.previousEntry;
                        currentEntry.previousEntry.nextEntry = entryToInsert;
                        currentEntry.previousEntry = entryToInsert;
                        break;
                    }
                } else {
                    currentEntry = currentEntry.nextEntry;
                    if (currentEntry == null) {
                        mostFrequentEntry.nextEntry = entryToInsert;
                        entryToInsert.previousEntry = mostFrequentEntry;
                        entryToInsert.nextEntry = null;
                        mostFrequentEntry = entryToInsert;
                        break;
                    }
                }
            }
        } else {
            mostFrequentEntry = entryToInsert;
            leastFrequentEntry = mostFrequentEntry;
        }
    }

    private void removeEntry(CacheEntry entryToRemove) {
        if (entryToRemove.previousEntry != null) {
            entryToRemove.previousEntry.nextEntry = entryToRemove.nextEntry;
        } else {
            leastFrequentEntry = entryToRemove.nextEntry;
        }

        if (entryToRemove.nextEntry != null) {
            entryToRemove.nextEntry.previousEntry = entryToRemove.previousEntry;
        } else {
            mostFrequentEntry = entryToRemove.previousEntry;
        }
    }
}
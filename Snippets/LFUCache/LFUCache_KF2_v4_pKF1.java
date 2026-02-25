package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, V> {

    private class CacheEntry {
        private final K key;
        private V value;
        private int frequency;
        private CacheEntry previous;
        private CacheEntry next;

        CacheEntry(K key, V value, int initialFrequency) {
            this.key = key;
            this.value = value;
            this.frequency = initialFrequency;
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
        CacheEntry entry = cacheEntriesByKey.get(key);
        if (entry == null) {
            return null;
        }
        removeEntry(entry);
        entry.frequency++;
        insertEntryByFrequency(entry);
        return entry.value;
    }

    public void put(K key, V value) {
        if (cacheEntriesByKey.containsKey(key)) {
            CacheEntry existingEntry = cacheEntriesByKey.get(key);
            existingEntry.value = value;
            existingEntry.frequency++;
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
                if (currentEntry.frequency > entryToInsert.frequency) {
                    if (currentEntry == leastFrequentEntry) {
                        entryToInsert.next = currentEntry;
                        currentEntry.previous = entryToInsert;
                        leastFrequentEntry = entryToInsert;
                        break;
                    } else {
                        entryToInsert.next = currentEntry;
                        entryToInsert.previous = currentEntry.previous;
                        currentEntry.previous.next = entryToInsert;
                        currentEntry.previous = entryToInsert;
                        break;
                    }
                } else {
                    currentEntry = currentEntry.next;
                    if (currentEntry == null) {
                        mostFrequentEntry.next = entryToInsert;
                        entryToInsert.previous = mostFrequentEntry;
                        entryToInsert.next = null;
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
        if (entryToRemove.previous != null) {
            entryToRemove.previous.next = entryToRemove.next;
        } else {
            leastFrequentEntry = entryToRemove.next;
        }

        if (entryToRemove.next != null) {
            entryToRemove.next.previous = entryToRemove.previous;
        } else {
            mostFrequentEntry = entryToRemove.previous;
        }
    }
}
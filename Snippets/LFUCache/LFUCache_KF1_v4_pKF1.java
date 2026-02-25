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

    private CacheEntry leastFrequentlyUsedEntry;
    private CacheEntry mostFrequentlyUsedEntry;
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
        CacheEntry existingEntry = entriesByKey.get(key);

        if (existingEntry != null) {
            existingEntry.value = value;
            existingEntry.accessFrequency++;
            removeEntry(existingEntry);
            insertEntryByFrequency(existingEntry);
            return;
        }

        if (entriesByKey.size() >= capacity) {
            entriesByKey.remove(leastFrequentlyUsedEntry.key);
            removeEntry(leastFrequentlyUsedEntry);
        }

        CacheEntry newEntry = new CacheEntry(key, value, 1);
        insertEntryByFrequency(newEntry);
        entriesByKey.put(key, newEntry);
    }

    private void insertEntryByFrequency(CacheEntry entryToInsert) {
        if (leastFrequentlyUsedEntry != null && mostFrequentlyUsedEntry != null) {
            CacheEntry currentEntry = leastFrequentlyUsedEntry;
            while (currentEntry != null) {
                if (currentEntry.accessFrequency > entryToInsert.accessFrequency) {
                    if (currentEntry == leastFrequentlyUsedEntry) {
                        entryToInsert.next = currentEntry;
                        currentEntry.previous = entryToInsert;
                        leastFrequentlyUsedEntry = entryToInsert;
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
                        mostFrequentlyUsedEntry.next = entryToInsert;
                        entryToInsert.previous = mostFrequentlyUsedEntry;
                        entryToInsert.next = null;
                        mostFrequentlyUsedEntry = entryToInsert;
                        break;
                    }
                }
            }
        } else {
            leastFrequentlyUsedEntry = entryToInsert;
            mostFrequentlyUsedEntry = entryToInsert;
        }
    }

    private void removeEntry(CacheEntry entryToRemove) {
        if (entryToRemove.previous != null) {
            entryToRemove.previous.next = entryToRemove.next;
        } else {
            leastFrequentlyUsedEntry = entryToRemove.next;
        }

        if (entryToRemove.next != null) {
            entryToRemove.next.previous = entryToRemove.previous;
        } else {
            mostFrequentlyUsedEntry = entryToRemove.previous;
        }
    }
}
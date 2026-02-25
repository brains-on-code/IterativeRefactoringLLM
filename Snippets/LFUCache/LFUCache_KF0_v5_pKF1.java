package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code LFUCache} class implements a Least Frequently Used (LFU) cache.
 * An LFU cache evicts the least frequently used item when the cache reaches its capacity.
 * It maintains a mapping of keys to nodes, where each node contains the key, its associated value,
 * and a frequency count that tracks how many times the item has been accessed. A doubly linked list
 * is used to efficiently manage the ordering of items based on their usage frequency.
 *
 * <p>This implementation is designed to provide O(1) time complexity for both the {@code get} and
 * {@code put} operations, which is achieved through the use of a hashmap for quick access and a
 * doubly linked list for maintaining the order of item frequencies.</p>
 *
 * <p>
 * Reference: <a href="https://en.wikipedia.org/wiki/Least_frequently_used">LFU Cache - Wikipedia</a>
 * </p>
 *
 * @param <K> The type of keys maintained by this cache.
 * @param <V> The type of mapped values.
 *
 * author Akshay Dubey (https://github.com/itsAkshayDubey)
 */
public class LFUCache<K, V> {

    /**
     * The {@code CacheEntry} class represents an element in the LFU cache.
     * Each entry contains a key, a value, and a frequency count.
     * It also has pointers to the previous and next entries in the doubly linked list.
     */
    private class CacheEntry {
        private final K key;
        private V value;
        private int frequency;
        private CacheEntry previousEntry;
        private CacheEntry nextEntry;

        /**
         * Constructs a new {@code CacheEntry} with the specified key, value, and frequency.
         *
         * @param key The key associated with this entry.
         * @param value The value stored in this entry.
         * @param frequency The frequency of usage of this entry.
         */
        CacheEntry(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }

    private CacheEntry leastFrequentEntry;
    private CacheEntry mostFrequentEntry;
    private final Map<K, CacheEntry> keyToEntryMap;
    private final int capacity;
    private static final int DEFAULT_CAPACITY = 100;

    /**
     * Constructs an LFU cache with the default capacity.
     */
    public LFUCache() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an LFU cache with the specified capacity.
     *
     * @param capacity The maximum number of items that the cache can hold.
     * @throws IllegalArgumentException if the specified capacity is less than or equal to zero.
     */
    public LFUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.keyToEntryMap = new HashMap<>();
    }

    /**
     * Retrieves the value associated with the given key from the cache.
     * If the key exists, the entry's frequency is incremented, and the entry is repositioned
     * in the linked list based on its updated frequency.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the key, or {@code null} if the key is not present in the cache.
     */
    public V get(K key) {
        CacheEntry entry = keyToEntryMap.get(key);
        if (entry == null) {
            return null;
        }
        removeEntry(entry);
        entry.frequency++;
        insertEntryByFrequency(entry);
        return entry.value;
    }

    /**
     * Inserts or updates a key-value pair in the cache.
     * If the key already exists, the value is updated and its frequency is incremented.
     * If the cache is full, the least frequently used item is removed before inserting the new item.
     *
     * @param key The key associated with the value to be inserted or updated.
     * @param value The value to be inserted or updated.
     */
    public void put(K key, V value) {
        if (keyToEntryMap.containsKey(key)) {
            CacheEntry existingEntry = keyToEntryMap.get(key);
            existingEntry.value = value;
            existingEntry.frequency++;
            removeEntry(existingEntry);
            insertEntryByFrequency(existingEntry);
        } else {
            if (keyToEntryMap.size() >= capacity) {
                keyToEntryMap.remove(leastFrequentEntry.key); // Evict least frequently used item
                removeEntry(leastFrequentEntry);
            }
            CacheEntry newEntry = new CacheEntry(key, value, 1);
            insertEntryByFrequency(newEntry);
            keyToEntryMap.put(key, newEntry);
        }
    }

    /**
     * Adds an entry to the linked list in the correct position based on its frequency.
     * The linked list is ordered by frequency, with the least frequently used entry at the head.
     *
     * @param entry The entry to be inserted into the list.
     */
    private void insertEntryByFrequency(CacheEntry entry) {
        if (leastFrequentEntry != null && mostFrequentEntry != null) {
            CacheEntry currentEntry = leastFrequentEntry;
            while (currentEntry != null) {
                if (currentEntry.frequency > entry.frequency) {
                    if (currentEntry == leastFrequentEntry) {
                        entry.nextEntry = currentEntry;
                        currentEntry.previousEntry = entry;
                        leastFrequentEntry = entry;
                        break;
                    } else {
                        entry.nextEntry = currentEntry;
                        entry.previousEntry = currentEntry.previousEntry;
                        currentEntry.previousEntry.nextEntry = entry;
                        currentEntry.previousEntry = entry;
                        break;
                    }
                } else {
                    currentEntry = currentEntry.nextEntry;
                    if (currentEntry == null) {
                        mostFrequentEntry.nextEntry = entry;
                        entry.previousEntry = mostFrequentEntry;
                        entry.nextEntry = null;
                        mostFrequentEntry = entry;
                        break;
                    }
                }
            }
        } else {
            leastFrequentEntry = entry;
            mostFrequentEntry = entry;
        }
    }

    /**
     * Removes an entry from the doubly linked list.
     * This method ensures that the pointers of neighboring entries are properly updated.
     *
     * @param entry The entry to be removed from the list.
     */
    private void removeEntry(CacheEntry entry) {
        if (entry.previousEntry != null) {
            entry.previousEntry.nextEntry = entry.nextEntry;
        } else {
            leastFrequentEntry = entry.nextEntry;
        }

        if (entry.nextEntry != null) {
            entry.nextEntry.previousEntry = entry.previousEntry;
        } else {
            mostFrequentEntry = entry.previousEntry;
        }
    }
}
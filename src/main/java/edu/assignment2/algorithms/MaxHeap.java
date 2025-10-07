package edu.assignment2.algorithms;

import edu.assignment2.metrics.PerformanceTracker;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class MaxHeap {
    private final int[] data;
    private int size;
    private final PerformanceTracker tracker;

    public MaxHeap(int capacity, PerformanceTracker tracker) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must be non-negative");
        }
        this.data = new int[capacity];
        this.size = 0;
        this.tracker = tracker == null ? new PerformanceTracker() : tracker;
        this.tracker.incrementAllocations();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        Arrays.fill(data, 0);
        size = 0;
    }

    public void insert(int value) {
        ensureCapacityForInsert();
        data[size] = value;
        tracker.incrementArrayWrites(1);
        siftUp(size);
        size++;
    }

    public int max() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        tracker.incrementArrayReads(1);
        return data[0];
    }

    public int extractMax() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        int root = data[0];
        tracker.incrementArrayReads(1);
        size--;
        if (size > 0) {
            data[0] = data[size];
            tracker.incrementArrayReads(1);
            tracker.incrementArrayWrites(1);
            siftDown(0);
        }
        return root;
    }

    public void increaseKey(int index, int newValue) {
        validateIndex(index);
        tracker.incrementArrayReads(1);
        if (newValue < data[index]) {
            throw new IllegalArgumentException("newValue must be >= current value");
        }
        data[index] = newValue;
        tracker.incrementArrayWrites(1);
        siftUp(index);
    }

    private void siftUp(int index) {
        int child = index;
        while (child > 0) {
            int parent = (child - 1) >>> 1;
            tracker.incrementComparisons(1);
            tracker.incrementArrayReads(2);
            if (data[child] > data[parent]) {
                swap(child, parent);
                child = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        int parent = index;
        while (true) {
            int left = (parent << 1) + 1;
            int right = left + 1;
            if (left >= size) {
                break;
            }
            int largerChild = left;
            if (right < size) {
                tracker.incrementComparisons(1);
                tracker.incrementArrayReads(2);
                if (data[right] > data[left]) {
                    largerChild = right;
                }
            }
            tracker.incrementComparisons(1);
            tracker.incrementArrayReads(2);
            if (data[largerChild] > data[parent]) {
                swap(largerChild, parent);
                parent = largerChild;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
        tracker.incrementSwaps(1);
        tracker.incrementArrayReads(2);
        tracker.incrementArrayWrites(2);
    }

    private void ensureCapacityForInsert() {
        if (size == data.length) {
            throw new IllegalStateException("heap capacity exceeded");
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
    }
}



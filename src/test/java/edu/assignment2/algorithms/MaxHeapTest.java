package edu.assignment2.algorithms;

import edu.assignment2.metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MaxHeapTest {

    @Test
    void emptyHeapOperations() {
        MaxHeap heap = new MaxHeap(0, new PerformanceTracker());
        assertTrue(heap.isEmpty());
        assertThrows(NoSuchElementException.class, heap::max);
        assertThrows(NoSuchElementException.class, heap::extractMax);
    }

    @Test
    void singleElement() {
        MaxHeap heap = new MaxHeap(1, new PerformanceTracker());
        heap.insert(42);
        assertEquals(1, heap.size());
        assertEquals(42, heap.max());
        assertEquals(42, heap.extractMax());
        assertTrue(heap.isEmpty());
    }

    @Test
    void duplicates() {
        MaxHeap heap = new MaxHeap(5, new PerformanceTracker());
        heap.insert(5);
        heap.insert(5);
        heap.insert(5);
        assertEquals(5, heap.max());
        assertEquals(5, heap.extractMax());
        assertEquals(5, heap.extractMax());
        assertEquals(5, heap.extractMax());
        assertTrue(heap.isEmpty());
    }

    @Test
    void increaseKey() {
        MaxHeap heap = new MaxHeap(3, new PerformanceTracker());
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.increaseKey(2, 10);
        assertEquals(10, heap.max());
        assertEquals(10, heap.extractMax());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000})
    void randomSequenceExtractsInNonIncreasingOrder(int n) {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap heap = new MaxHeap(n, tracker);
        Random rnd = new Random(0);
        for (int i = 0; i < n; i++) heap.insert(rnd.nextInt());
        int prev = Integer.MAX_VALUE;
        while (!heap.isEmpty()) {
            int x = heap.extractMax();
            assertTrue(x <= prev);
            prev = x;
        }
    }
}



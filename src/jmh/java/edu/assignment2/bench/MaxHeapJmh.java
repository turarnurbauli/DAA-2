package edu.assignment2.bench;

import edu.assignment2.algorithms.MaxHeap;
import edu.assignment2.metrics.PerformanceTracker;
import org.openjdk.jmh.annotations.*;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@Fork(1)
public class MaxHeapJmh {
    @State(Scope.Thread)
    public static class BenchState {
        @Param({"1000", "10000", "100000"})
        public int n;
        int[] data;
        PerformanceTracker tracker;

        @Setup
        public void setup() {
            SecureRandom rnd = new SecureRandom();
            data = new int[n];
            for (int i = 0; i < n; i++) data[i] = rnd.nextInt();
            tracker = new PerformanceTracker();
        }
    }

    @Benchmark
    public void buildAndDrain(BenchState s) {
        MaxHeap heap = new MaxHeap(s.n, s.tracker);
        for (int v : s.data) heap.insert(v);
        while (!heap.isEmpty()) heap.extractMax();
    }
}



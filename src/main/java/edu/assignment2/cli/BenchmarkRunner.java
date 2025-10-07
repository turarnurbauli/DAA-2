package edu.assignment2.cli;

import edu.assignment2.algorithms.MaxHeap;
import edu.assignment2.metrics.PerformanceTracker;

import java.nio.file.Path;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        int[] sizes = {100, 1000, 10000, 100000};
        String scenarioArg = args.length > 0 ? args[0] : "random"; // random|sorted|reverse|all
        Path out = Path.of(args.length > 1 ? args[1] : "docs/performance-plots/maxheap_bench.csv");

        PerformanceTracker tracker = new PerformanceTracker();
        tracker.writeCsvHeader(out);

        String[] scenarios = scenarioArg.equalsIgnoreCase("all")
                ? new String[] {"random", "sorted", "reverse"}
                : new String[] {scenarioArg};

        for (String scenario : scenarios) {
            for (int n : sizes) {
                int[] input = generate(scenario, n);
                MaxHeap heap = new MaxHeap(n, tracker);
                tracker.reset();
                Instant start = Instant.now();
                for (int v : input) {
                    heap.insert(v);
                }
                while (!heap.isEmpty()) {
                    heap.extractMax();
                }
                Instant end = Instant.now();
                long nanos = Duration.between(start, end).toNanos();
                tracker.appendCsv(out, n, scenario, nanos);
            }
        }
        System.out.println("Benchmark complete -> " + out.toAbsolutePath());
    }

    private static int[] generate(String scenario, int n) {
        int[] a = new int[n];
        SecureRandom rnd = new SecureRandom();
        switch (scenario) {
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "reverse":
                for (int i = 0; i < n; i++) a[i] = n - i;
                break;
            default:
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
        }
        return Arrays.copyOf(a, a.length);
    }
}



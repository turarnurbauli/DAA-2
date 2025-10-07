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
        int[] sizes = parseSizes(args);
        String scenarioArg = args.length > 0 ? args[0] : "random"; // random|sorted|reverse|java|all
        Path out = Path.of(args.length > 1 ? args[1] : "docs/performance-plots/maxheap_bench.csv");

        PerformanceTracker tracker = new PerformanceTracker();
        tracker.writeCsvHeader(out);

        String[] scenarios = scenarioArg.equalsIgnoreCase("all")
                ? new String[] {"random", "sorted", "reverse", "java"}
                : new String[] {scenarioArg};

        for (String scenario : scenarios) {
            for (int n : sizes) {
                int[] input = generate(scenario.equals("java") ? "random" : scenario, n);
                tracker.reset();
                long nanos;
                if (scenario.equals("java")) {
                    java.util.PriorityQueue<Integer> pq = new java.util.PriorityQueue<>((a,b)->Integer.compare(b,a));
                    Instant start = Instant.now();
                    for (int v : input) pq.add(v);
                    while (!pq.isEmpty()) pq.poll();
                    Instant end = Instant.now();
                    nanos = Duration.between(start, end).toNanos();
                } else {
                    MaxHeap heap = new MaxHeap(n, tracker);
                    Instant start = Instant.now();
                    for (int v : input) heap.insert(v);
                    while (!heap.isEmpty()) heap.extractMax();
                    Instant end = Instant.now();
                    nanos = Duration.between(start, end).toNanos();
                }
                tracker.appendCsv(out, n, scenario, nanos);
            }
        }
        System.out.println("Benchmark complete -> " + out.toAbsolutePath());
    }

    private static int[] parseSizes(String[] args) {
        if (args.length > 2) {
            String[] parts = args[2].split(",");
            int[] arr = new int[parts.length];
            for (int i = 0; i < parts.length; i++) arr[i] = Integer.parseInt(parts[i].trim());
            return arr;
        }
        return new int[] {100, 1000, 10000, 100000};
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



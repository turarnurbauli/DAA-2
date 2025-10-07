package edu.assignment2.metrics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;

public class PerformanceTracker {
    private final AtomicLong comparisons = new AtomicLong();
    private final AtomicLong swaps = new AtomicLong();
    private final AtomicLong arrayReads = new AtomicLong();
    private final AtomicLong arrayWrites = new AtomicLong();
    private final AtomicLong allocations = new AtomicLong();

    public void incrementComparisons(long delta) { comparisons.addAndGet(delta); }
    public void incrementSwaps(long delta) { swaps.addAndGet(delta); }
    public void incrementArrayReads(long delta) { arrayReads.addAndGet(delta); }
    public void incrementArrayWrites(long delta) { arrayWrites.addAndGet(delta); }
    public void incrementAllocations() { allocations.incrementAndGet(); }

    public long getComparisons() { return comparisons.get(); }
    public long getSwaps() { return swaps.get(); }
    public long getArrayReads() { return arrayReads.get(); }
    public long getArrayWrites() { return arrayWrites.get(); }
    public long getAllocations() { return allocations.get(); }

    public void reset() {
        comparisons.set(0L);
        swaps.set(0L);
        arrayReads.set(0L);
        arrayWrites.set(0L);
        allocations.set(0L);
    }

    public void writeCsvHeader(Path path) throws IOException {
        ensureParent(path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), false))) {
            writer.write("n,case,comparisons,swaps,arrayReads,arrayWrites,allocations,ns\n");
        }
    }

    public void appendCsv(Path path, int n, String scenario, long nanos) throws IOException {
        ensureParent(path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(n + "," + scenario + "," + getComparisons() + "," + getSwaps() + "," + getArrayReads() + "," + getArrayWrites() + "," + getAllocations() + "," + nanos + "\n");
        }
    }

    private void ensureParent(Path path) throws IOException {
        Path parent = path.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }
}



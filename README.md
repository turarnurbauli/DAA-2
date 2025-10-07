# Assignment 2 - Max Heap (Pair 4, Student B)

This project implements a Max-Heap with `increaseKey` and `extractMax`, a performance tracker, unit tests, and a CLI benchmark runner. Maven-based for easy import into IntelliJ IDEA.

## Quick Start (IntelliJ IDEA)
- Open IntelliJ IDEA ➜ File ➜ Open ➜ select `assignment2-max-heap/pom.xml`
- Wait for Maven import to finish
- Run configuration: `edu.assignment2.cli.BenchmarkRunner`

CLI usage:
```
mvn -q -f assignment2-max-heap/pom.xml -DskipTests package
java -jar assignment2-max-heap/target/assignment2-max-heap-0.1.0.jar all assignment2-max-heap/docs/performance-plots/maxheap_bench.csv
```

## Structure
```
src/main/java/edu/assignment2/algorithms/MaxHeap.java
src/main/java/edu/assignment2/metrics/PerformanceTracker.java
src/main/java/edu/assignment2/cli/BenchmarkRunner.java
src/test/java/edu/assignment2/algorithms/MaxHeapTest.java
docs/analysis-report.pdf (placeholder)
docs/performance-plots/
```

## Complexity
- Insert: O(log n) average/worst, Ω(1) best when at root
- ExtractMax: O(log n)
- IncreaseKey: O(log n)
- Space: O(n) for in-place array-backed heap



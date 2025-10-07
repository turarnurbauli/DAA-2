# Assignment 2 - Max Heap (Pair 4, Student B)

![CI](https://github.com/turarnurbauli/DAA-2/actions/workflows/ci.yml/badge.svg)
![Bench](https://github.com/turarnurbauli/DAA-2/actions/workflows/benchmark.yml/badge.svg)
![Report](https://github.com/turarnurbauli/DAA-2/actions/workflows/report.yml/badge.svg)

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

Custom sizes (third arg):
```
java -jar assignment2-max-heap/target/assignment2-max-heap-0.1.0.jar all assignment2-max-heap/docs/performance-plots/maxheap_bench.csv 100,1000,10000,100000
```

Generate plot PNG from CSV:
```
java -cp assignment2-max-heap/target/assignment2-max-heap-0.1.0-all.jar edu.assignment2.cli.PlotGenerator assignment2-max-heap/docs/performance-plots/maxheap_bench.csv assignment2-max-heap/docs/performance-plots/maxheap_bench.png
```

Compare with Java PriorityQueue (add 'java' scenario or use 'all'):
```
java -jar assignment2-max-heap/target/assignment2-max-heap-0.1.0-all.jar java assignment2-max-heap/docs/performance-plots/maxheap_bench.csv 100,1000,10000,100000
```

Run JMH:
```
mvn -q -f assignment2-max-heap/pom.xml -DskipTests package
java -jar assignment2-max-heap/target/assignment2-max-heap-0.1.0-all.jar -jmh
```

## Structure
```
src/main/java/edu/assignment2/algorithms/MaxHeap.java
src/main/java/edu/assignment2/metrics/PerformanceTracker.java
src/main/java/edu/assignment2/cli/BenchmarkRunner.java
src/main/java/edu/assignment2/cli/PlotGenerator.java
src/test/java/edu/assignment2/algorithms/MaxHeapTest.java
src/jmh/java/edu/assignment2/bench/MaxHeapJmh.java
docs/analysis-report.pdf (placeholder)
docs/analysis-report-template.md
docs/performance-plots/
```

GitHub Actions
- CI: runs tests on push/PR
- Benchmark & Plots: builds jar, runs benchmark (scenarios incl. 'java'), generates PNG, uploads artifacts
- Build Analysis PDF: converts `docs/analysis-report-template.md` to `docs/analysis-report.pdf`

## Complexity
- Insert: O(log n) average/worst, Ω(1) best when at root
- ExtractMax: O(log n)
- IncreaseKey: O(log n)
- Space: O(n) for in-place array-backed heap



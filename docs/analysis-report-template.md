# Algorithm Analysis Report (Pair 4, Student B — Max-Heap)

Author: Student B (Pair 4)
Topic: Max-Heap (increaseKey and extractMax)
Date: 2025‑10‑07

## 1. Algorithm Overview
A max-heap is a complete binary tree stored in an array where each node's value is not smaller than its children. For index i, parent p = floor((i−1)/2), left child l = 2i+1, right child r = 2i+2. In this project we use bitwise arithmetic: p = (i−1) >>> 1, l = (i << 1) + 1, r = l + 1.

Supported operations:
- insert(x): insert element followed by upward sift (siftUp)
- max(): read maximum in O(1)
- extractMax(): remove maximum followed by downward sift (siftDown)
- increaseKey(i, newValue): increase key at index i and sift up

Use cases: priority queues, schedulers, streaming scenarios with frequent insert/extract of the highest priority element.

## 2. Asymptotic Complexity
Let n be the number of elements.

- insert:
  - Worst case: sift up across the height h ≈ floor(log2 n) ⇒ O(log n)
  - Best case: no sift (element ≤ parent) ⇒ Ω(1)
  - Average case: Θ(log n)

- extractMax:
  - Replace the root with the last element and sift down by height ⇒ Θ(log n)

- increaseKey(i, v):
  - For v ≥ a[i], siftUp by depth of i ⇒ O(log n) worst, Ω(1) best

- max(): Θ(1)

Space:
- Array of size n, no auxiliary data structures ⇒ auxiliary space Θ(1) beyond the array; total space Θ(n).

Instrumentation:
- We track comparisons, swaps, arrayReads, arrayWrites, allocations to validate constant factors and memory-access profile.

## 3. Code Review and Optimizations
Implementation file: `edu.assignment2.algorithms.MaxHeap`

Design choices:
- Iterative `siftUp`/`siftDown` (no recursion) to avoid stack overhead and enable precise counters.
- Indexing via bit operations: `(i-1)>>>1`, `(i<<1)+1` (often faster than integer division/multiplication).
- Explicit metrics counting for visibility into performance.

Potential improvements (constant factors):
- Cache `data[parent]` locally in `siftDown` to reduce repeated array reads.
- Use a "hole percolation" variant for `siftDown` to reduce swap/write count versus 3-assign swaps.
- Provide an optional `buildHeap(int[])` bottom-up heapify (Θ(n)) for bulk build scenarios; current PQ workflow uses per-element inserts by design.

Correctness:
- Input validation (empty heap extraction, index bounds, forbid decreasing key in increaseKey).
- Tests cover empty, single, duplicates, random, and non-increasing extraction order.

## 4. Empirical Validation
Methodology:
- JVM: Temurin/OpenJDK 17
- Sizes: default n ∈ {10^2, 10^3, 10^4, 10^5}; configurable via the 3rd CLI argument
- Distributions: `random`, `sorted`, `reverse`; plus `java` scenario comparing to JDK `PriorityQueue`

Tools:
- CLI `BenchmarkRunner`: outputs CSV `docs/performance-plots/maxheap_bench.csv` with columns `n,case,comparisons,swaps,arrayReads,arrayWrites,allocations,ns`
- `PlotGenerator`: generates `docs/performance-plots/maxheap_bench.png` (time in ms)
- JMH `MaxHeapJmh`: stable average-time measurements for build+drain

Expected observations:
- As n grows, time follows ≈ n log n for the end-to-end "insert all → extract all" cycle.
- `sorted` vs `reverse` make little difference for heaps; structure is driven by the complete binary tree layout.
- `PriorityQueue` is also a binary heap; differences are constant-factor due to JIT and internal optimizations. Often JDK is comparable or slightly faster, especially without metrics overhead.

Theory validation:
- The time-vs-n curve is consistent with Θ(log n) per operation and Θ(n log n) for build+drain.

## 5. Conclusions and Recommendations
- Implementation is correct; memory is Θ(n); key ops are Θ(log n).
- Suitable when frequent insert/extract-max with predictable memory usage is required.
- Consider adding `buildHeap(int[])` for Θ(n) bulk build when the full array is known ahead of time.
- If position-based updates are frequent, consider an auxiliary map (key→position) to accelerate updates at the cost of Θ(n) extra memory.

## 6. Appendix
- CLI: `java -jar target/assignment2-max-heap-0.1.0-all.jar all docs/performance-plots/maxheap_bench.csv 100,1000,10000,100000`
- Plot: `java -cp target/assignment2-max-heap-0.1.0-all.jar edu.assignment2.cli.PlotGenerator docs/performance-plots/maxheap_bench.csv docs/performance-plots/maxheap_bench.png`
- JMH: `java -jar target/assignment2-max-heap-0.1.0-all.jar -jmh`
- GitHub Actions:
  - CI: tests on each push/PR
  - Benchmark & Plots: CSV + PNG artifacts
  - Build Analysis PDF: converts this Markdown into `docs/analysis-report.pdf`



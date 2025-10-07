package edu.assignment2.cli;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PlotGenerator {
    public static void main(String[] args) throws Exception {
        Path csv = Path.of(args.length > 0 ? args[0] : "docs/performance-plots/maxheap_bench.csv");
        Path out = Path.of(args.length > 1 ? args[1] : "docs/performance-plots/maxheap_bench.png");
        Map<String, List<Long>> scenarioToTimes = new LinkedHashMap<>();
        List<Integer> ns = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(csv)) {
            String header = br.readLine(); // n,case,comparisons,swaps,arrayReads,arrayWrites,allocations,ns
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int n = Integer.parseInt(p[0]);
                String scenario = p[1];
                long nanos = Long.parseLong(p[7]);
                if (!ns.contains(n)) ns.add(n);
                scenarioToTimes.computeIfAbsent(scenario, k -> new ArrayList<>()).add(nanos);
            }
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(1000).height(600)
                .title("MaxHeap Build+Extract Benchmark")
                .xAxisTitle("n")
                .yAxisTitle("time (ms)")
                .build();
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setOverlapped(true);

        List<String> xLabels = ns.stream().map(String::valueOf).toList();
        for (Map.Entry<String, List<Long>> e : scenarioToTimes.entrySet()) {
            List<Double> ms = e.getValue().stream().map(v -> v / 1_000_000.0).toList();
            chart.addSeries(e.getKey(), xLabels, ms);
        }
        Files.createDirectories(out.toAbsolutePath().getParent());
        BitmapEncoder.saveBitmap(chart, out.toString(), BitmapEncoder.BitmapFormat.PNG);
        System.out.println("Plot saved -> " + out.toAbsolutePath());
    }
}



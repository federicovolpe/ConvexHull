package main;

import heuristics.Heuristic;

public record ReportData(Heuristic heuristic, int pointNumber, Double jaccardIndex, Long time, boolean exception) {
}

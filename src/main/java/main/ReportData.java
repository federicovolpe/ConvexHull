package main;

import heuristics.Heuristic;

public record ReportData(Heuristic heuristic, int pointNumber, Double jaccardIndex, Long time, boolean exception) {

  public String toCsv(){
    return heuristic.getClass().getSimpleName() + ","+
        pointNumber + "," +
        jaccardIndex + "," +
        time + "," +
        exception + "\n";
  }
}

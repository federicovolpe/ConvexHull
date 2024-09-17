package main;

import heuristics.Heuristic;

public record ReportData(Heuristic heuristic,
                         int pointNumber,
                         int hyperplaneBudget,
                         Double jaccardIndex,
                         Long time,
                         boolean exception,
                         int regularity) {

  public String toCsv(){
    return heuristic.getClass().getSimpleName() + ","+
        pointNumber + "," +
        hyperplaneBudget + "," +
        jaccardIndex + "," +
        time + "," +
        exception + "," +
        regularity +"\n";
  }
}

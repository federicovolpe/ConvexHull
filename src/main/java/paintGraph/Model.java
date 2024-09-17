package paintGraph;

import heuristics.Heuristic;
import paintGraph.presenter.Observer;
import shapes.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model implements Observable {
  final Map<Heuristic, Boolean> displayedH = new HashMap<>();
  final Polygon p ;
  final List<Observer> observers = new ArrayList<>();


  public Model (final Polygon p, final List<Heuristic> heuristics){
    this.p = p;
    for(Heuristic h : heuristics)
      displayedH.put(h, true);
  }

  public void heuristicSelection(Heuristic h) {
    displayedH.put(h, !displayedH.get(h));
    notifyObservers();
  }

  public boolean isActive(Heuristic h) {
    return displayedH.get(h);
  }

  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  @Override
  public void notifyObservers() {
    List<Heuristic> active = new ArrayList<>();

    for(Map.Entry e : displayedH.entrySet())
      if((Boolean) e.getValue()) {
        active.add((Heuristic) e.getKey());
      }

    for(Observer o : observers)
      o.update(p, active);
  }
}

package paintGraph;

import paintGraph.presenter.Observer;

public interface Observable {
  void addObserver (Observer o);
  void notifyObservers ();
}

package paintGraph.presenter;

import heuristics.Heuristic;
import paintGraph.Model;
import paintGraph.view.InputView;

import java.util.List;

public class HeuristicPresenter implements InputPresenter {
  private final Model m;
  private final List<Heuristic> heuristics;

  public HeuristicPresenter(InputView v, Model m, List<Heuristic> heuristics) {
    this.m = m;
    this.heuristics = heuristics;
    v.addHandlers(this);
  }
  @Override
  public void action(String text) {
    System.out.println("presenter is sending to model the requset");
    for(Heuristic h : heuristics){
      if(h.getClass().getSimpleName().equals(text))
        m.heuristicSelection(h);
    }
  }
}

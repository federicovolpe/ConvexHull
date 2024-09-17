package paintGraph.presenter;
import heuristics.Heuristic;
import paintGraph.Model;
import paintGraph.view.GraphPanel;
import paintGraph.view.OutputView;
import shapes.Polygon;
import java.util.List;

public class GraphPresenter implements Observer{
  final OutputView view ;

  public GraphPresenter(OutputView v, Model model) {
    model.addObserver(this);
    this.view = v;
  }

  @Override
  public void update(Polygon p, List<Heuristic> heuristics) {
    for(Heuristic h : heuristics)
      System.out.println(h.getClass().getSimpleName());
    view.set(new GraphPanel(p, heuristics));
  }
}

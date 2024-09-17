package paintGraph.view;

import heuristics.Heuristic;
import paintGraph.presenter.InputPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ButtonPanel extends JPanel implements InputView {
  final List<JButton> buttons = new ArrayList<>();
  public ButtonPanel (List<Heuristic> heuristics){
    setLayout(new GridLayout(0, 1));  // One column, multiple rows
    for (Heuristic h : heuristics) {
      JButton button = new JButton(h.getClass().getSimpleName());
      button.setOpaque(true);
      button.setBackground(Color.GREEN);

      buttons.add(button);
      add(button);
    }
  }

  @Override
  public void addHandlers(InputPresenter presenter) {
    for (JButton button : buttons) {
      button.addActionListener(e -> {
        presenter.action(button.getText());
        changeSelectedBackground(button);}
      );
    }
  }

  private void changeSelectedBackground(JButton button) {
    if (button.getBackground().equals(Color.GREEN)) {
      button.setBackground(Color.RED);
    } else {
      button.setBackground(Color.GREEN);
    }

    // Revalidate and repaint the button to ensure the changes are reflected
    button.revalidate();
    button.repaint();
  }

}

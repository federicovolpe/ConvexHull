package paintGraph.view;

import heuristics.Heuristic;
import paintGraph.presenter.InputPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ButtonPanel extends JPanel implements InputView {
  final List<JButton> buttons = new ArrayList<>();
  public ButtonPanel (List<Heuristic> heuristics){
    setLayout(new GridLayout(0, 1));  // One column, multiple rows
    for (Heuristic h : heuristics) {
      MyButton button = new MyButton(h.getClass().getSimpleName());
      button.setForeground(Color.BLACK);
      button.setHorizontalTextPosition(SwingConstants.CENTER);
      button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
      button.setBackground(new Color(132, 230, 110));
      button.setHoverBackgroundColor(Color.LIGHT_GRAY);
      button.setPressedBackgroundColor(Color.DARK_GRAY);

      buttons.add(button);
      add(button);
    }
  }

  @Override
  public void addHandlers(InputPresenter presenter) {
    for (JButton button : buttons) {
      button.addActionListener(e -> {presenter.action(button.getText());}
      );
    }
  }

  class MyButton extends JButton {

    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private final Color active = new Color(132, 230, 110);
    private final Color disabled = new Color(209, 65, 65);
    private Color prevColor = active;

    public MyButton() {
      this(null);
    }

    public MyButton(String text) {
      super(text);
      super.setContentAreaFilled(false);
      addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
          setBackground(hoverBackgroundColor);
        }

        @Override
        public void mouseExited(MouseEvent e) {
          setBackground(prevColor);  // Ritorna al colore originale quando il mouse esce
        }

        @Override
        public void mousePressed(MouseEvent e) {
          setBackground(pressedBackgroundColor);
          changeSelectedBackground();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          setBackground(hoverBackgroundColor);  // Torna al colore hover quando rilasci il bottone
        }
      });
    }

    public void addActionListener(ActionListener l) {
      super.addActionListener(l);
    }

    @Override
    protected void paintComponent(Graphics g) {
      if (getModel().isPressed()) {
        g.setColor(pressedBackgroundColor);
      } else if (getModel().isRollover()) {
        g.setColor(hoverBackgroundColor);
      } else {
        g.setColor(getBackground());
      }
      g.fillRect(0, 0, getWidth(), getHeight());
      super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
      this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
      this.pressedBackgroundColor = pressedBackgroundColor;
    }

    private void changeSelectedBackground() {
      if (prevColor.equals(active)){
        setBackground(disabled);
        prevColor = disabled;
      } else {
        setBackground(active);
        prevColor = active;
      }
    }
  }

}

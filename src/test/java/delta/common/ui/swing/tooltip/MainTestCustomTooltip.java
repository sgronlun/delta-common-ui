package delta.common.ui.swing.tooltip;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolTip;

/**
 * Test class for the custom tooltip.
 * @author DAM
 */
public class MainTestCustomTooltip
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    JButton button=new JButton("Coucou");
    button.setToolTipText("Coucou!");
    JButton button2=new JButton()
    {
      @Override
      public JToolTip createToolTip()
      {
        JPanel panel=new JPanel();
        panel.setPreferredSize(new Dimension(100,100));
        panel.setBackground(Color.RED);
        panel.add(new JButton("Coucou2"));
        panel.setOpaque(true);
        return new CustomTooltip(panel);
      }
    };
    button2.setText("Coucou");
    button2.setToolTipText("Blabla");
    JFrame f=new JFrame();
    f.getContentPane().add(button2);
    f.pack();
    f.setVisible(true);
  }
}

package delta.common.ui.swing.checkbox;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Test class for the three-state checkbox.
 * @author DAM
 */
public class MainTestThreeStateCheckbox
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    JPanel panel=new JPanel();
    ThreeStateCheckbox cb1=new ThreeStateCheckbox("Not selected");
    panel.add(cb1);
    ThreeStateCheckbox cb2=new ThreeStateCheckbox("Half-state");
    cb2.setState(ThreeState.HALF_SELECTED);
    panel.add(cb2);
    ThreeStateCheckbox cb3=new ThreeStateCheckbox("Selected");
    cb3.setState(ThreeState.SELECTED);
    panel.add(cb3);
    JFrame f=new JFrame();
    f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    f.getContentPane().add(panel);
    f.pack();
    f.setVisible(true);
  }
}

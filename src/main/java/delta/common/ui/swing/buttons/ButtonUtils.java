package delta.common.ui.swing.buttons;

import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Utility methods related to buttons.
 * @author DAM
 */
public class ButtonUtils
{
  /**
   * Release all resources used by the given button.
   * @param button Button to use.
   */
  public static void dispose(JButton button)
  {
    removeListeners(button);
  }

  /**
   * Remove all the action listeners of the given button.
   * @param button Button to use.
   */
  public static void removeListeners(JButton button)
  {
    if (button==null)
    {
      return;
    }
    ActionListener[] listeners=button.getActionListeners();
    if ((listeners!=null) && (listeners.length>0))
    {
      for(ActionListener listener : listeners)
      {
        button.removeActionListener(listener);
      }
    }
  }
}

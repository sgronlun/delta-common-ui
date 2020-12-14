package delta.common.ui.swing.checkbox;

import javax.swing.JCheckBox;

import delta.common.ui.swing.GuiFactory;

/**
 * Controller for a check-box.
 * @author DAM
 */
public class CheckboxController
{
  private JCheckBox _checkbox;

  /**
   * Constructor.
   * @param label Label to show.
   */
  public CheckboxController(String label)
  {
    _checkbox=GuiFactory.buildCheckbox(label);
  }

  /**
   * Set the graphical state of the managed gadget.
   * @param enabled Enabled or not.
   */
  public void setState(boolean enabled)
  {
    _checkbox.setEnabled(enabled);
  }

  /**
   * Get the managed check-box.
   * @return the managed check-box.
   */
  public JCheckBox getCheckbox()
  {
    return _checkbox;
  }

  /**
   * Indicates if the managed check-box is selected or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isSelected()
  {
    return _checkbox.isSelected();
  }

  /**
   * Set the selection state of the managed check-box.
   * @param selected <code>true</code> if selected, <code>false</code> otherwise.
   */
  public void setSelected(boolean selected)
  {
    _checkbox.setSelected(selected);
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _checkbox=null;
  }
}

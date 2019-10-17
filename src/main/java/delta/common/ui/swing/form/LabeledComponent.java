package delta.common.ui.swing.form;

import java.awt.Component;

import javax.swing.JLabel;

import delta.common.ui.swing.GuiFactory;

/**
 * Label/component pair.
 * @param <C> Component type.
 * @author DAM
 */
public class LabeledComponent<C extends Component>
{
  private JLabel _label;
  private C _component;

  /**
   * Constructor.
   * @param label Label to use.
   * @param component Associated component.
   */
  public LabeledComponent(String label, C component)
  {
    _label=GuiFactory.buildLabel(label);
    _component=component;
  }

  /**
   * Get the managed label.
   * @return the managed label.
   */
  public JLabel getLabel()
  {
    return _label;
  }

  /**
   * Get the associated component.
   * @return the associated component.
   */
  public C getComponent()
  {
    return _component;
  }

  /**
   * Set the pair visible or not.
   * @param visible <code>true</code> to show it, <code>false</code> to hide it.
   */
  public void setVisible(boolean visible)
  {
    _label.setVisible(visible);
    _component.setVisible(visible);
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _label=null;
    _component=null;
  }
}

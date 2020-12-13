package delta.common.ui.swing.tooltip;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JToolTip;

/**
 * Custom tooltip.
 * A tooltip that draws a custom component.
 * @author DAM
 */
public class CustomTooltip extends JToolTip
{
  private JComponent _component;

  /**
   * Constructor.
   * @param component to show.
   */
  public CustomTooltip(JComponent component)
  {
    _component=component;
    _component.setSize(component.getPreferredSize());
    add(component);
  }

  @Override
  public void paint(Graphics g)
  {
    _component.paint(g);
  }

  @Override
  public void setTipText(String tipText)
  {
    // Ignored
  }

  @Override
  public Dimension getPreferredSize()
  {
    return _component.getPreferredSize();
  }
}

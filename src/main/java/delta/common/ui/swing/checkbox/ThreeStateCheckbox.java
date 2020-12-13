package delta.common.ui.swing.checkbox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

/**
 * Three-state checkbox.
 * @author DAM
 */
public class ThreeStateCheckbox extends JCheckBox implements Icon, ActionListener
{
  private static final Logger LOGGER=Logger.getLogger(ThreeStateCheckbox.class);

  private static final Icon ICON=UIManager.getIcon("CheckBox.icon");

  private boolean _halfState;

  /**
   * Constructor.
   */
  public ThreeStateCheckbox()
  {
    this("");
  }

  /**
   * Constructor with text.
   * @param text Text.
   */
  public ThreeStateCheckbox(String text)
  {
    super(text);
    addActionListener(this);
    setIcon(this);
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y)
  {
    ICON.paintIcon(c,g,x,y);
    if (!_halfState)
    {
      return;
    }
    
    int w=getIconWidth();
    int h=getIconHeight();
    g.setColor(c.isEnabled()?new Color(51,51,51):new Color(122,138,153));
    g.fillRect(x+4,y+4,w-8,h-8);

    if (!c.isEnabled())
    {
      return;
    }
    g.setColor(new Color(81,81,81));
    g.drawRect(x+4,y+4,w-9,h-9);
  }

  @Override
  public int getIconWidth()
  {
    return ICON.getIconWidth();
  }

  @Override
  public int getIconHeight()
  {
    return ICON.getIconHeight();
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    ThreeStateCheckbox tcb=(ThreeStateCheckbox)e.getSource();
    if (_halfState)
    {
      _halfState=false;
      tcb.setSelected(true);
    }
    else
    {
      if (tcb.isSelected())
      {
        _halfState=true;
        tcb.setSelected(false);
      }
    }
    if (LOGGER.isDebugEnabled())
    {
      String state=(isSelected())?"Selected":(_halfState?"Half-selected":"Not selected");
      LOGGER.debug("State: "+state);
    }
  }

  /**
   * Indicates if it is half-selected or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isHalfSelected()
  {
    return _halfState;
  }

  /**
   * Set the half-selected state.
   * @param halfState State to set.
   */
  public void setHalfSelected(boolean halfState)
  {
    _halfState=halfState;
    if (halfState)
    {
      setSelected(false);
    }
    if (LOGGER.isDebugEnabled())
    {
      String state=(isSelected())?"Selected":(_halfState?"Half-selected":"Not selected");
      LOGGER.debug("State: "+state);
    }
    repaint();
  }
}

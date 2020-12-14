package delta.common.ui.swing.checkbox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

/**
 * Three-state checkbox.
 * @author DAM
 */
public class ThreeStateCheckbox extends JCheckBox implements Icon, ItemListener
{
  private static final Logger LOGGER=Logger.getLogger(ThreeStateCheckbox.class);

  private static final Icon ICON=UIManager.getIcon("CheckBox.icon");

  private ThreeState _state;
  private boolean _inListener;

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
    _state=ThreeState.NOT_SELECTED;
    addItemListener(this);
    setIcon(this);
  }

  @Override
  public void itemStateChanged(ItemEvent e)
  {
    if (_inListener) return;
    _inListener=true;

    // Prog OR nonSelected+click => setSelected(true)=>"Selected",state=SELECTED;
    // selected+click => setSelected(false)=>"Not selected", state=NOT_SELECTED;
    // hal selected+click=>setSelected(true)=>"Selected"(with halfSelectged=true),state=SELECTED;
    int state=e.getStateChange();
    if (state==ItemEvent.SELECTED)
    {
      if (_state==ThreeState.HALF_SELECTED)
      {
        // Transition half state->selected
        _state=ThreeState.SELECTED;
      }
      else
      {
        // Transition not-selected->half selected
        setSelected(false);
        _state=ThreeState.HALF_SELECTED;
      }
    }
    else
    {
      // Transition selected->not-selected
      _state=ThreeState.NOT_SELECTED;
    }
    _inListener=false;

    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("State: "+_state);
    }
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y)
  {
    ICON.paintIcon(c,g,x,y);
    if (_state!=ThreeState.HALF_SELECTED)
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

  /**
   * Get the current state.
   * @return the current state.
   */
  public ThreeState getState()
  {
    return _state;
  }

  /**
   * Set the state.
   * @param state State to set.
   */
  public void setState(ThreeState state)
  {
    _inListener=true;
    if (state==ThreeState.SELECTED)
    {
      setSelected(true);
    }
    else
    {
      setSelected(false);
    }
    _state=state;
    _inListener=false;
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("State: "+_state);
    }
    repaint();
  }
}

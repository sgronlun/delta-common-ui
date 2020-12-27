package delta.common.ui.swing.icons;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

/**
 * Transparent icon.
 * @author DAM
 */
public class TransparentIcon implements Icon
{
  private Icon _icon;
  private float _opacity;

  /**
   * Constructor.
   * @param icon Embedded icon.
   * @param opacity Opacity (0=transparent, 1=opaque).
   */
  public TransparentIcon(Icon icon, float opacity)
  {
    _icon=icon;
    _opacity=opacity;
  }

  public void paintIcon(Component c, Graphics g, int x, int y)
  {
    if (_icon!=null)
    {
      Graphics2D g2d = (Graphics2D) g.create();
      g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, _opacity));
      _icon.paintIcon(c,g2d,x,y);
    }
  }

  @Override
  public int getIconWidth()
  {
    return (_icon!=null)?_icon.getIconWidth():0;
  }

  @Override
  public int getIconHeight()
  {
    return (_icon!=null)?_icon.getIconHeight():0;
  }
}

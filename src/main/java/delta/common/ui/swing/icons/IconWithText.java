package delta.common.ui.swing.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

import delta.common.ui.swing.draw.HaloPainter;

/**
 * Icon with text display on bottom right.
 * @author DAM
 */
public class IconWithText implements Icon
{
  /**
   * Default font.
   */
  public static final Font DEFAULT_FONT=new Font(Font.DIALOG,Font.BOLD,12);

  /**
   * Position of text inside the icon.
   * @author DAM
   */
  public enum Position
  {
    /**
     * Bottom right.
     */
    BOTTOM_RIGHT,
    /**
     * Top left.
     */
    TOP_LEFT
  }

  private Icon _icon;
  private String _text;
  private Color _color;
  private Position _position;

  /**
   * Constructor.
   * @param icon Embedded icon.
   * @param text Text to display.
   * @param color Color to use for text.
   */
  public IconWithText(Icon icon, String text, Color color)
  {
    _icon=icon;
    _text=text;
    _color=color;
    _position=Position.BOTTOM_RIGHT;
  }

  /**
   * Set position to use.
   * @param position Position to use.
   */
  public void setPosition(Position position)
  {
    _position=position;
  }

  public void paintIcon(Component c, Graphics g, int x, int y)
  {
    if (_icon!=null)
    {
      _icon.paintIcon(c,g,x,y);
    }

    if (_text.length()>0)
    {
      Font font=DEFAULT_FONT;
      g.setFont(font);
      FontMetrics metrics=g.getFontMetrics(font);
      Rectangle2D r=metrics.getStringBounds(_text,g);

      int dx;
      int dy;
      if (_position==Position.BOTTOM_RIGHT)
      {
        dx = (int)(getIconWidth() - r.getWidth()) - 2;
        dy = getIconHeight() - metrics.getDescent();
      }
      else
      {
        dx = 5;
        dy = metrics.getAscent() + 1;
      }
      dx+=x;
      dy+=y;

      HaloPainter.drawStringWithHalo(g,dx,dy,_text,_color,Color.BLACK);
    }
  }

  /**
   * Set text to display.
   * @param text Text to display.
   */
  public void setText(String text)
  {
    if (!_text.equals(text))
    {
      _text=text;
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

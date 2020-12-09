package delta.common.ui.swing.draw;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Halo painting utilities.
 * @author DAM
 */
public class HaloPainter
{
  /**
   * Draw a string with a halo.
   * @param g Graphics to draw to.
   * @param x Horizontal position.
   * @param y Vertical position.
   * @param text Text to draw.
   * @param foreground Foreground color.
   * @param halo Halo color.
   */
  public static void drawStringWithHalo(Graphics g, int x, int y, String text, Color foreground, Color halo)
  {
    g.setColor(halo);
    for(int i=x-1;i<=x+1;i++)
    {
      for(int j=y-1;j<=y+1;j++)
      {
        if ((i!=x) || (j!=y))
        {
          g.drawString(text, i, j);
        }
      }
    }
    g.setColor(foreground);
    g.drawString(text, x, y);
  }

  /**
   * Draw a string with a halo.
   * @param g Graphics to draw to.
   * @param x Horizontal position.
   * @param y Vertical position.
   * @param textLines Text lines to draw.
   * @param foreground Foreground color.
   * @param halo Halo color.
   */
  public static void drawStringsWithHalo(Graphics g, int x, int y, String[] textLines, Color foreground, Color halo)
  {
    FontMetrics fm=g.getFontMetrics();
    int height=fm.getHeight();
    for(String textLine : textLines)
    {
      drawStringWithHalo(g,x,y,textLine,foreground,halo);
      y+=height;
    }
  }
}

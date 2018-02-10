package delta.common.ui.swing.labels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JLabel;

import delta.common.ui.swing.draw.HaloPainter;

/**
 * A JLabel with a halo.
 * @author DAM
 */
public class LabelWithHalo extends JLabel
{
  /**
   * Default font.
   */
  public static final Font DEFAULT_FONT=new Font(Font.DIALOG,Font.BOLD,12);

  /**
   * Constructor.
   */
  public LabelWithHalo()
  {
  }

  @Override
  public Dimension getPreferredSize()
  {
    Dimension ret=super.getPreferredSize();
    ret.height+=2;
    ret.width+=2;
    return ret;
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    if (isOpaque())
    {
      g.setColor(getBackground());
      g.fillRect(0,0,getWidth(),getHeight());
    }
    Font font=getFont();
    g.setFont(font);
    FontMetrics metrics=g.getFontMetrics(font);
    int dx=1;
    int dy=getHeight()-metrics.getDescent()-1;
    String text=getText();
    Color color=getForeground();
    HaloPainter.drawStringWithHalo(g,dx,dy,text,color,Color.BLACK);
  }
}

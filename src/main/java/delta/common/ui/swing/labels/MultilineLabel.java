package delta.common.ui.swing.labels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A label displayed on a fixed number of lines.
 * @author DAM
 */
public class MultilineLabel extends JPanel
{
  private String[] _text;
  private int[] _baselines;
  private Dimension _preferredSize;

  /**
   * Constructor.
   */
  public MultilineLabel()
  {
    setOpaque(false);
    _text=new String[0];
  }

  /**
   * Set the displayed text.
   * @param text Text
   * @param lineCount Number of lines to use.
   */
  public void setText(String text, int lineCount)
  {
    String[] lines=SplitIntoLines.split(text, lineCount);
    setText(lines);
  }

  /**
   * Set displayed text.
   * @param text Multilines text.
   */
  public void setText(String[] text)
  {
    _text=text;
    _preferredSize=null;
    revalidate();
    repaint();
  }

  private Dimension computeSize()
  {
    if (_preferredSize!=null)
    {
      return _preferredSize;
    }
    _baselines=new int[_text.length];
    Font font=getFont();
    Graphics2D graphics=(Graphics2D)getGraphics();
    Rectangle global = new Rectangle(0,0,0,0);
    int baseline=0;
    int index=0;
    for(String line : _text)
    {
      if (line.length()==0) continue;
      TextLayout layout=new TextLayout(line,font,graphics.getFontRenderContext());
      float ascent=layout.getAscent();
      float advance=layout.getAdvance();
      float descent=layout.getDescent();
      float leading=layout.getLeading();
      _baselines[index]=baseline+(int)ascent;
      index++;
      float height=ascent+descent+leading;
      Rectangle2D.Float lineR=new Rectangle2D.Float(0.0F,-ascent+baseline,advance,height);
      //System.out.println("Line: " + line + " => " + lineR);
      //System.out.println("Ascent="+ascent+", advance="+advance+", descent="+descent+",leading="+leading);
      global.add(lineR);
      baseline+=height;
    }
    Dimension ret=new Dimension(global.width,global.height);
    _preferredSize=ret;
    return ret;
  }

  @Override
  public Dimension getPreferredSize()
  {
    return computeSize();
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    computeSize();
    int index=0;
    for(String line : _text)
    {
      int baseline=_baselines[index];
      index++;
      g.drawString(line,0,baseline);
    }
  }

  /**
   * Main method for tests.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    MultilineLabel label=new MultilineLabel();
    String[] text=new String[]{"aaaadfdgfgrp^tyr p^toyp^toptôe", "pqrA"};
    label.setText(text);
    JFrame f=new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(label);
    f.pack();
    f.setVisible(true);
  }
}

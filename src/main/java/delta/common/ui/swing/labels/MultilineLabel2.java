package delta.common.ui.swing.labels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;

/**
 * A label displayed on a fixed number of lines.
 * @author DAM
 */
public class MultilineLabel2 extends JPanel
{
  private LabelLineStyle _defaultStyle;
  private Map<Integer,LabelLineStyle> _lineStyles;

  /**
   * Constructor.
   */
  public MultilineLabel2()
  {
    setOpaque(false);
    setLayout(new GridBagLayout());
    _defaultStyle=LabelLineStyle.DEFAULT_LINE_STYLE;
    _lineStyles=new HashMap<Integer,LabelLineStyle>();
  }

  /**
   * Constructor.
   * @param size Font size.
   */
  public MultilineLabel2(float size)
  {
    this();
    _defaultStyle=_defaultStyle.setFontSize(size);
  }

  /**
   * Get the default style.
   * @return A style.
   */
  public LabelLineStyle getDefaultStyle()
  {
    return _defaultStyle;
  }

  /**
   * Set the default style.
   * @param defaultStyle Default style.
   */
  public void setDefaultStyle(LabelLineStyle defaultStyle)
  {
    _defaultStyle=defaultStyle;
  }

  /**
   * Set the style for a single line.
   * @param lineIndex Index of the targeted line.
   * @param style Style to set.
   */
  public void setLineStyle(int lineIndex, LabelLineStyle style)
  {
    _lineStyles.put(Integer.valueOf(lineIndex),style);
  }

  /**
   * Set the foreground color.
   * @param foregroundColor Color to set.
   */
  public void setForegroundColor(Color foregroundColor)
  {
    _defaultStyle=_defaultStyle.setForegroundColor(foregroundColor);
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

  private LabelLineStyle getStyleForLine(int index)
  {
    LabelLineStyle ret=_lineStyles.get(Integer.valueOf(index));
    if (ret==null)
    {
      ret=_defaultStyle;
    }
    return ret;
  }

  /**
   * Set displayed text.
   * @param text Multilines text.
   */
  public void setText(String[] text)
  {
    removeAll();
    int nbLines=text.length;
    for(int i=0;i<nbLines;i++)
    {
      LabelLineStyle style=getStyleForLine(i);
      JLabel label=null;
      if (style.isHalo())
      {
        LabelWithHalo haloLabel=new LabelWithHalo();
        haloLabel.setHaloColor(style.getHaloColor());
        label=haloLabel;
      }
      else
      {
        label=GuiFactory.buildLabel("");
      }
      label.setFont(style.getFont());
      label.setForeground(style.getForegroundColor());
      label.setText(text[i]);
      GridBagConstraints c=new GridBagConstraints(0,i,1,1,1.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
      add(label,c);
    }
    revalidate();
    repaint();
  }

  /**
   * Main method for tests.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    MultilineLabel2 label=new MultilineLabel2();
    String[] text=new String[]{"aaaadfdgfgrp^tyr p^toyp^toptôe", "pqrA"};
    label.setText(text);
    JFrame f=new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(label);
    f.pack();
    f.setVisible(true);
  }
}

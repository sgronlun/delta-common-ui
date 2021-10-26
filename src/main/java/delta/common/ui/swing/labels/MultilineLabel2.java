package delta.common.ui.swing.labels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
  private Float _firstLineSize;
  private Float _size;
  private Color _foreground;

  /**
   * Constructor.
   */
  public MultilineLabel2()
  {
    setOpaque(false);
    setLayout(new GridBagLayout());
    _foreground=Color.BLACK;
  }

  /**
   * Constructor.
   * @param size Font size.
   */
  public MultilineLabel2(float size)
  {
    this();
    _firstLineSize=Float.valueOf(size);
    _size=Float.valueOf(size);
  }

  /**
   * Set the font size of the first line.
   * @param size Size to set (<code>null</code> to use default).
   */
  public void setFirstLineFontSize(Float size)
  {
    _firstLineSize=size;
  }

  /**
   * Set the font size of the next lines.
   * @param size Size to set (<code>null</code> to use default).
   */
  public void setNextLinesFontSize(Float size)
  {
    _size=size;
  }

  /**
   * Set the foreground color.
   * @param foreground Color to set.
   */
  public void setForegroundColor(Color foreground)
  {
    _foreground=foreground;
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
    removeAll();
    int nbLines=text.length;
    for(int i=0;i<nbLines;i++)
    {
      Float size=(i==0)?_firstLineSize:_size; 
      JLabel label=null;
      if (size!=null)
      {
        label=GuiFactory.buildLabel(text[i],size.floatValue());
      }
      else
      {
        label=GuiFactory.buildLabel(text[i]);
      }
      label.setForeground(_foreground);
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

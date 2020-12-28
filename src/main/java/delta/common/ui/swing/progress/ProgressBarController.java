package delta.common.ui.swing.progress;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import delta.common.ui.swing.GuiFactory;

/**
 * Controller for a progress bar.
 * @author DAM
 */
public class ProgressBarController
{
  private static final Color DEFAULT_COLOR=Color.BLUE;
  private JProgressBar _progressBar;

  /**
   * Constructor.
   */
  public ProgressBarController()
  {
    _progressBar=buildProgressBar();
    setSize(200,25);
  }

  /**
   * Get the managed progress bar.
   * @return the managed progress bar.
   */
  public JProgressBar getProgressBar()
  {
    return _progressBar;
  }

  private JProgressBar buildProgressBar()
  {
    JProgressBar bar=new JProgressBar(SwingConstants.HORIZONTAL,0,100);
    bar.setBackground(GuiFactory.getBackgroundColor());
    bar.setBorderPainted(true);
    bar.setStringPainted(true);
    bar.setForeground(DEFAULT_COLOR);
    return bar;
  }

  /**
   * Set color.
   * @param color Color to set.
   */
  public void setColor(Color color)
  {
    _progressBar.setForeground(color);
  }

  /**
   * Set the size of the progress bar.
   * @param width Width to set (pixels).
   * @param height Height to set (pixels).
   */
  public void setSize(int width, int height)
  {
    _progressBar.setPreferredSize(new Dimension(width,height));
    _progressBar.setMinimumSize(new Dimension(width,height));
  }

  /**
   * Set the value range.
   * @param min Minimul value.
   * @param max Maximul value.
   */
  public void setRange(int min, int max)
  {
    _progressBar.setMinimum(min);
    _progressBar.setMaximum(max);
  }

  /**
   * Set the currently displayed value.
   * @param value Value to set.
   */
  public void setValue(int value)
  {
    _progressBar.setString(value+" / "+_progressBar.getMaximum());
    _progressBar.setValue(value);
  }

  /**
   * Get the currently displayed value.
   * @return a value.
   */
  public int getValue()
  {
    return _progressBar.getValue();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _progressBar=null;
  }
}

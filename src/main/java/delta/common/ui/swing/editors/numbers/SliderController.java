package delta.common.ui.swing.editors.numbers;

import javax.swing.JSlider;

/**
 * Slider controller.
 * @author DAM
 */
public class SliderController
{
  private JSlider _slider;

  /**
   * Constructor.
   */
  public SliderController()
  {
    _slider=new JSlider();
  }

  /**
   * Get the managed slider.
   * @return the managed slider.
   */
  public JSlider getSlider()
  {
    return _slider;
  }

  /**
   * Set the range of the slider.
   * @param min Minimum value.
   * @param max Maximum value.
   */
  public void setRange(int min, int max)
  {
    _slider.setMinimum(min);
    _slider.setMaximum(max);
  }

  /**
   * Set the currently displayed value.
   * @param value Value to set.
   */
  public void setValue(int value)
  {
    _slider.setValue(value);
  }

  /**
   * Get the currently displayed value.
   * @return a value.
   */
  public int getValue()
  {
    return _slider.getValue();
  }
}

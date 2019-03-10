package delta.common.ui.swing.text;

import javax.swing.JTextField;

import delta.common.utils.NumericTools;

/**
 * Float edition controller using a text field.
 * @author DAM
 */
public class FloatEditionController extends NumberEditionController<Float>
{
  /**
   * Constructor.
   * @param textField Managed text field.
   */
  public FloatEditionController(JTextField textField)
  {
    super(textField,5);
  }

  /**
   * Set the given value into the managed text field.
   * @param value Value to set.
   */
  public void setValue(Float value)
  {
    if (value==null)
    {
      _textField.setText("");
    }
    else
    {
      _textField.setText(String.valueOf(value.floatValue()));
    }
  }

  @Override
  protected Float parseValue(String text)
  {
    Float ret=null;
    Float value=NumericTools.parseFloat(text);
    if (value!=null)
    {
      ret=value;
      // Check for min value
      if (_minValue!=null)
      {
        if (value.floatValue()<_minValue.floatValue())
        {
          ret=null;
        }
      }
      // Check for max value
      if (_maxValue!=null)
      {
        if (value.floatValue()>_maxValue.floatValue())
        {
          ret=null;
        }
      }
    }
    return ret;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    super.dispose();
  }
}

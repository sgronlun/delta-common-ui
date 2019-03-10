package delta.common.ui.swing.text;

import javax.swing.JTextField;

import delta.common.utils.NumericTools;

/**
 * Integer edition controller using a text field.
 * @author DAM
 */
public class IntegerEditionController extends NumberEditionController<Integer>
{
  /**
   * Constructor.
   * @param textField Managed text field.
   */
  public IntegerEditionController(JTextField textField)
  {
    this(textField,5);
  }

  /**
   * Constructor.
   * @param textField Managed text field.
   * @param columns Width indication.
   */
  public IntegerEditionController(JTextField textField, int columns)
  {
    super(textField,columns);
  }

  /**
   * Set the given value into the managed text field.
   * @param value Value to set.
   */
  public void setValue(Integer value)
  {
    if (value==null)
    {
      _textField.setText("");
    }
    else
    {
      _textField.setText(String.valueOf(value.intValue()));
    }
  }

  /**
   * Get the edited value.
   * @return A value or <code>null</code> if not valid.
   */
  public Integer getValue()
  {
    return _currentValue;
  }

  @Override
  protected Integer parseValue(String text)
  {
    Integer ret=null;
    Integer value=NumericTools.parseInteger(text);
    if (value!=null)
    {
      ret=value;
      // Check for min value
      if (_minValue!=null)
      {
        if (value.intValue()<_minValue.intValue())
        {
          ret=null;
        }
      }
      // Check for max value
      if (_maxValue!=null)
      {
        if (value.intValue()>_maxValue.intValue())
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

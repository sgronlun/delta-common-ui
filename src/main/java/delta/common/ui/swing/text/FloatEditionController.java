package delta.common.ui.swing.text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;

/**
 * Float edition controller using a text field.
 * @author DAM
 */
public class FloatEditionController extends NumberEditionController<Float>
{
  private static final Logger LOGGER=Logger.getLogger(FloatEditionController.class);

  private NumberFormat _format;

  /**
   * Constructor.
   * @param textField Managed text field.
   */
  public FloatEditionController(JTextField textField)
  {
    super(textField,5);
    _format=new DecimalFormat();
  }

  /**
   * Set the pattern to use to format the displayed value.
   * @param pattern Pattern to set.
   */
  public void setFormat(String pattern)
  {
    _format=new DecimalFormat(pattern);
  }

  /**
   * Set the format to use to format the displayed value.
   * @param format Format to set.
   */
  public void setFormat(NumberFormat format)
  {
    _format=format;
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
      String valueStr=_format.format(value.floatValue());
      _textField.setText(valueStr);
    }
  }

  @Override
  protected Float parseValue(String text)
  {
    Float value=null;
    try
    {
      Number parsedValue=_format.parse(text);
      if (parsedValue!=null)
      {
        value=Float.valueOf(parsedValue.floatValue());
      }
    }
    catch(Exception e)
    {
      LOGGER.warn("Parsing error!",e);
    }
    Float ret=null;
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
    _format=null;
  }
}

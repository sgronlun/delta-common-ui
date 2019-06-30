package delta.common.ui.swing.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextField;

/**
 * Base class for number edition controllers.
 * @param <T> Type of numbers.
 * @author DAM
 */
public abstract class NumberEditionController<T extends Number> extends AbstractTextEditionController
{
  protected T _currentValue;
  protected T _minValue;
  protected T _maxValue;
  private List<NumberListener<T>> _listeners;

  /**
   * Constructor.
   * @param textField Managed text field.
   * @param columns Width indication.
   */
  public NumberEditionController(JTextField textField, int columns)
  {
    super(textField);
    _minValue=null;
    _maxValue=null;
    _textField.setColumns(columns);
    _textField.setHorizontalAlignment(JTextField.TRAILING);
    _listeners=new ArrayList<NumberListener<T>>();
  }

  /**
   * Set the allowed value range.
   * @param minValue Minimum value, if any.
   * @param maxValue Maximum value, if any.
   */
  public void setValueRange(T minValue, T maxValue)
  {
    _minValue=minValue;
    _maxValue=maxValue;
    configureTextField();
  }

  /**
   * Configure the text field.
   */
  private void configureTextField()
  {
    // TODO add Document model to check for typed chars
  }

  /**
   * Set the given value into the managed text field.
   * @param value Value to set.
   */
  public abstract void setValue(T value);

  /**
   * Get the edited value.
   * @return A value or <code>null</code> if not valid.
   */
  public T getValue()
  {
    return _currentValue;
  }

  protected abstract T parseValue(String text);

  /**
   * Add a value listener.
   * @param listener Listener to add.
   */
  public void addValueListener(NumberListener<T> listener)
  {
    _listeners.add(listener);
  }

  /**
   * Remove a value listener.
   * @param listener Listener to remove.
   */
  public void removeValueListener(NumberListener<T> listener)
  {
    _listeners.remove(listener);
  }

  protected void handleTextChanged(String newText)
  {
    T newValue=parseValue(newText);
    boolean same=Objects.equals(_currentValue,newValue);
    _currentValue=newValue;
    if (!same)
    {
      // Call listeners if any
      for(NumberListener<T> listener : _listeners)
      {
        listener.valueChanged(this,newValue);
      }
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    super.dispose();
    _currentValue=null;
    _minValue=null;
    _maxValue=null;
    _listeners.clear();
  }
}

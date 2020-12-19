package delta.common.ui.swing.editors.numbers;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.text.IntegerEditionController;

/**
 * A textfield and label to edit a integer number with a maximum value.
 * @author DAM
 */
public class TextFieldAndMaxLabel
{
  private static final String SLASH_SEPARATOR = " / ";
  // Data
  private int _max;
  // Controllers
  private IntegerEditionController _integerEditor;
  // UI
  private JPanel _panel;
  private JTextField _textField;
  private JLabel _maxLabel;

  /**
   * Constructor.
   */
  public TextFieldAndMaxLabel()
  {
    _textField=GuiFactory.buildTextField("");
    _integerEditor=new IntegerEditionController(_textField);
    _maxLabel=GuiFactory.buildLabel(SLASH_SEPARATOR+_max);
    _panel=build();
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  /**
   * Get the current value.
   * @return a value or <code>null</code> if none/invalid value.
   */
  public Integer getValue()
  {
    return _integerEditor.getValue();
  }

  /**
   * Set the current value.
   * @param value Value to set.
   */
  public void setValue(Integer value)
  {
    _integerEditor.setValue(value);
  }

  /**
   * Get the managed text field.
   * @return the managed text field.
   */
  public JTextField getTextField()
  {
    return _textField;
  }

  /**
   * Get the managed label.
   * @return the managed label.
   */
  public JLabel getLabel()
  {
    return _maxLabel;
  }

  private JPanel build()
  {
    JPanel panel=GuiFactory.buildPanel(new FlowLayout());
    panel.add(_textField);
    panel.add(_maxLabel);
    return panel;
  }

  /**
   * Set the maximum value.
   * @param max Maximum value to use.
   */
  public void setMax(int max)
  {
    _integerEditor.setValueRange(_integerEditor.getMinValue(),Integer.valueOf(max));
    _maxLabel.setText(SLASH_SEPARATOR+max);
  }
}

package delta.common.ui.swing.text;

import javax.swing.JTextField;

/**
 * Base class for text edition controllers.
 * @author DAM
 */
public abstract class AbstractTextEditionController
{
  protected JTextField _textField;
  private DynamicTextEditionController _dynamicEditionController;

  /**
   * Constructor.
   * @param textField Managed text field.
   */
  public AbstractTextEditionController(JTextField textField)
  {
    _textField=textField;
    TextListener listener=new TextListener()
    {
      @Override
      public void textChanged(String newText)
      {
        handleTextChanged(newText);
      }
    };
    _dynamicEditionController=new DynamicTextEditionController(_textField,listener);
  }

  /**
   * Set the graphical state of the managed gadget.
   * @param enabled Enabled or not.
   * @param editable Editable or not.
   */
  public void setState(boolean enabled, boolean editable)
  {
    _textField.setEnabled(enabled);
    _textField.setEditable(editable);
  }

  /**
   * Get the managed text field.
   * @return the managed text field.
   */
  public JTextField getTextField()
  {
    return _textField;
  }

  protected abstract void handleTextChanged(String newText);

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _textField=null;
    if (_dynamicEditionController!=null)
    {
      _dynamicEditionController.dispose();
      _dynamicEditionController=null;
    }
  }
}

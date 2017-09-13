package delta.common.ui.swing.text.dates;

import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JTextField;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.dnd.DNDTools;
import delta.common.ui.swing.dnd.DropListener;

/**
 * Controller for a date edition gadget.
 * @author DAM
 */
public class DateEditionController implements DropListener<List<File>>
{
  private DateCodec _codec;
  private JTextField _textField;

  /**
   * Constructor.
   * @param codec 
   */
  public DateEditionController(DateCodec codec)
  {
    _codec=codec;
    _textField=GuiFactory.buildTextField("");
    _textField.setColumns(10);
    DNDTools.installFilesDropListener(_textField,this);
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
  public JTextField getTextField() {
    return _textField;
  }

  public void drop(List<File> data, DropTargetDropEvent sourceEvent)
  {
    if ((data!=null) && (data.size()>0))
    {
      File file=data.get(0);
      long date=file.lastModified();
      setDate(Long.valueOf(date));
    }
  }

  /**
   * Get the current raw date as string (may be invalid).
   * @return A string.
   */
  public String getRawDate()
  {
    return _textField.getText();
  }

  /**
   * Get the current date value.
   * @return A date value or <code>null</code> if invalid.
   */
  public Long getDate()
  {
    String dateStr=getRawDate();
    Long date=_codec.parseDate(dateStr);
    return date;
  }

  /**
   * Set the current date.
   * @param date Date to set.
   */
  public void setDate(Long date)
  {
    String dateStr=_codec.formatDate(date);
    _textField.setText(dateStr);
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _textField=null;
  }
}

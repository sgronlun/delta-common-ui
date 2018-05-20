package delta.common.ui.swing.text.dates;

import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextField;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.dnd.DNDTools;
import delta.common.ui.swing.dnd.DropListener;
import delta.common.ui.swing.menus.CommandExecutor;
import delta.common.ui.swing.menus.PopupMenuController;
import delta.common.ui.swing.text.DynamicTextEditionController;
import delta.common.ui.swing.text.TextListener;

/**
 * Controller for a date edition gadget.
 * @author DAM
 */
public class DateEditionController implements DropListener<List<File>>, TextListener, CommandExecutor
{
  private static final String NOW_COMMAND="NOW";

  private Long _date;
  private DateCodec _codec;
  private DynamicTextEditionController _textController;
  private JTextField _textField;
  private List<DateListener> _listeners;
  private PopupMenuController _menu;

  /**
   * Constructor.
   * @param codec Date codec.
   */
  public DateEditionController(DateCodec codec)
  {
    _date=null;
    _codec=codec;
    _textField=GuiFactory.buildTextField("");
    _textField.setColumns(10);
    String tooltip=buildTooltip();
    _textField.setToolTipText(tooltip);
    installMenu();
    DNDTools.installFilesDropListener(_textField,this);
  }

  private String buildTooltip()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("<html>");
    sb.append("Use context menu to fill with the current date.");
    sb.append("<br>");
    sb.append("Drag&drop a file into this text field to fill it with the file date");
    sb.append("</html>");
    return sb.toString();
  }

  private void installMenu()
  {
    _menu=new PopupMenuController(this);
    _menu.addMenuItem("Now...",NOW_COMMAND);
    _menu.install(_textField);
  }

  @Override
  public void invoke(String command)
  {
    if (NOW_COMMAND.equals(command))
    {
      doNow();
    }
  }

  private void doNow()
  {
    long now=System.currentTimeMillis();
    setDate(Long.valueOf(now));
  }

  /**
   * Add a date listener.
   * @param listener Listener to add.
   */
  public void addListener(DateListener listener)
  {
    if (_listeners==null)
    {
      _textController=new DynamicTextEditionController(_textField,this);
      _listeners=new ArrayList<DateListener>();
    }
    _listeners.add(listener);
  }

  /**
   * Remove a date listener.
   * @param listener Listener to remove.
   */
  public void removeListener(DateListener listener)
  {
    if (_listeners!=null)
    {
      _listeners.remove(listener);
      if (_listeners.size()==0)
      {
        _textController.dispose();
        _textController=null;
        _listeners=null;
      }
    }
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

  public void textChanged(String newText)
  {
    Long newDate=getDate();
    boolean dateChanged=!Objects.equals(_date,newDate);
    if (dateChanged)
    {
      _date=newDate;
      //System.out.println("New date: "+_date+" = "+((_date!=null)?new Date(_date.longValue()):null));
      for(DateListener listener : _listeners)
      {
        listener.dateChanged(this,_date);
      }
    }
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
    if (!Objects.equals(dateStr,_textField.getText()))
    {
      _textField.setText(dateStr);
      // Rely on the text listener to update the date
      //_date=date;
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _date=null;
    _codec=null;
    if (_textController!=null)
    {
      _textController.dispose();
      _textController=null;
    }
    if (_menu!=null)
    {
      _menu.dispose();
      _menu=null;
    }
    _textField=null;
    _listeners=null;
  }
}

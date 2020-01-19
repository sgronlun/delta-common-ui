package delta.common.ui.swing.tables.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.misc.Disposable;

/**
 * The ButtonColumn class provides a renderer and an editor that looks like a
 * JButton.
 * <p>The renderer and editor will then be used for a specified column in
 * the table.
 * <p>The TableModel will contain the String to be displayed on the
 * button.
 * <p>The button can be invoked by a mouse click or by pressing the space
 * bar when the cell has focus.
 * <p>Optionally a mnemonic can be set to invoke the button.
 * <p>When the button is invoked the provided Action is invoked. The source
 * of the Action will be the table. The action command will contain the model
 * row number of the button that was clicked.
 */
public class ButtonRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener, Disposable
{
  private int _mnemonic;
  private Border _originalBorder;
  private Border _focusBorder;
  private int _editedRow;

  private JButton _renderButton;
  private JButton _editButton;
  private Object _editorValue;
  private ActionListener _actionListener;

  /**
   * Create the ButtonColumn to be used as a renderer and editor. The renderer
   * and editor will automatically be installed on the TableColumn of the
   * specified column.
   */
  public ButtonRenderer()
  {
    _renderButton=GuiFactory.buildButton("");
    _editButton=GuiFactory.buildButton("");
    _editButton.setFocusPainted(false);
    _editButton.addActionListener(this);
    _originalBorder=_editButton.getBorder();
    setFocusBorder(new LineBorder(Color.BLUE));
    _actionListener=null;
  }

  /**
   * Get foreground color of the button when the cell has focus
   * @return the foreground color
   */
  public Border getFocusBorder()
  {
    return _focusBorder;
  }

  /**
   * The foreground color of the button when the cell has focus
   * @param focusBorder the foreground color
   */
  public void setFocusBorder(Border focusBorder)
  {
    this._focusBorder=focusBorder;
    _editButton.setBorder(focusBorder);
  }

  /**
   * Get the button's mnemonic.
   * @return a mnemonic.
   */
  public int getMnemonic()
  {
    return _mnemonic;
  }

  /**
   * The mnemonic to activate the button when the cell has focus
   * @param mnemonic the mnemonic
   */
  public void setMnemonic(int mnemonic)
  {
    _mnemonic=mnemonic;
    _renderButton.setMnemonic(mnemonic);
    _editButton.setMnemonic(mnemonic);
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
  {
    if (value==null)
    {
      _editButton.setText("");
      _editButton.setIcon(null);
    }
    else if (value instanceof Icon)
    {
      _editButton.setText("");
      _editButton.setIcon((Icon)value);
    }
    else
    {
      _editButton.setText(value.toString());
      _editButton.setIcon(null);
    }

    _editorValue=value;
    _editedRow=row;
    return _editButton;
  }

  /**
   * Get the edited row.
   * @return a row index.
   */
  public int getEditedRow()
  {
    return _editedRow;
  }

  @Override
  public Object getCellEditorValue()
  {
    return _editorValue;
  }

  //
  // Implement TableCellRenderer interface
  //
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    if (hasFocus)
    {
      _renderButton.setBorder(_focusBorder);
    }
    else
    {
      _renderButton.setBorder(_originalBorder);
    }

    if (value==null)
    {
      _renderButton.setText("");
      _renderButton.setIcon(null);
    }
    else if (value instanceof Icon)
    {
      _renderButton.setText("");
      _renderButton.setIcon((Icon)value);
    }
    else
    {
      _renderButton.setText(value.toString());
      _renderButton.setIcon(null);
    }

    return _renderButton;
  }

  /**
   * Set action listener.
   * @param actionListener Action listener to use.
   */
  public void setActionListener(ActionListener actionListener)
  {
    _actionListener=actionListener;
  }

  //
  // Implement ActionListener interface
  //
  /**
   * The button has been pressed. Stop editing and invoke the custom Action.
   * @param e Source event.
   */
  public void actionPerformed(ActionEvent e)
  {
    int row=_editedRow;
    fireEditingStopped();

    if (_actionListener!=null)
    {
      ActionEvent event=new ActionEvent("",ActionEvent.ACTION_PERFORMED,""+row,e.getModifiers());
      _actionListener.actionPerformed(event);
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _originalBorder=null;
    _focusBorder=null;
    _renderButton=null;
    _editButton=null;
    _editorValue=null;
    _actionListener=null;
  }
}

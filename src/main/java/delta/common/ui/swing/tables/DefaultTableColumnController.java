package delta.common.ui.swing.tables;

import java.awt.event.ActionListener;
import java.util.Comparator;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import delta.common.ui.swing.misc.Disposable;

/**
 * Controller for a column of a generic table.
 * @param <POJO> Type of data items.
 * @param <VALUE> Value type of the managed column.
 * @author DAM
 */
public class DefaultTableColumnController<POJO,VALUE> implements TableColumnController<POJO,VALUE>
{
  private String _id;
  private int _minWidth;
  private int _maxWidth;
  private int _preferredWidth;
  private Class<? extends VALUE> _dataType;
  private String _header;
  private boolean _sortable;
  private CellDataProvider<POJO,VALUE> _valueProvider;
  private CellDataUpdater<POJO> _valueUpdater;
  private TableCellRenderer _renderer;
  private TableCellEditor _editor;
  private Boolean _useToString;
  private boolean _editable;
  private Comparator<VALUE> _comparator;
  private TableCellRenderer _headerCellRenderer;
  private ActionListener _actionListener;

  /**
   * Constructor.
   * @param header Header label.
   * @param dataType Type of data in the column.
   * @param valueProvider Provider for cell values.
   */
  public DefaultTableColumnController(String header, Class<? extends VALUE> dataType, CellDataProvider<POJO,VALUE> valueProvider)
  {
    this(header,header,dataType,valueProvider);
  }

  /**
   * Constructor.
   * @param id Identifier.
   * @param header Header label.
   * @param dataType Type of data in the column.
   * @param valueProvider Provider for cell values.
   */
  public DefaultTableColumnController(String id, String header, Class<? extends VALUE> dataType, CellDataProvider<POJO,VALUE> valueProvider)
  {
    _id=id;
    _header=header;
    _dataType=dataType;
    _sortable=true;
    _valueProvider=valueProvider;
    _renderer=null;
    _editor=null;
    _useToString=null;
  }

  /**
   * Get the identifier for this column.
   * @return the identifier for this column.
   */
  public String getId()
  {
    return _id;
  }

  /**
   * Get the minimum width for this column.
   * @return a value in pixels.
   */
  public int getMinWidth()
  {
    return _minWidth;
  }

  /**
   * Set the minimum width for this column.
   * @param minWidth a width in pixels (-1 for no minimum).
   */
  public void setMinWidth(int minWidth)
  {
    _minWidth=minWidth;
  }

  /**
   * Get the maximum width for this column.
   * @return a value in pixels (-1 means no limit).
   */
  public int getMaxWidth()
  {
    return _maxWidth;
  }

  /**
   * Set the maximum width for this column.
   * @param maxWidth a width in pixels (-1 for no limit).
   */
  public void setMaxWidth(int maxWidth)
  {
    _maxWidth=maxWidth;
  }

  /**
   * Get the preferred width for this column.
   * @return a value in pixels.
   */
  public int getPreferredWidth()
  {
    return _preferredWidth;
  }

  /**
   * Set the preferred width for this column.
   * @param preferredWidth a width in pixels.
   */
  public void setPreferredWidth(int preferredWidth)
  {
    _preferredWidth=preferredWidth;
  }

  /**
   * Set width specifications.
   * @param minWidth Minimum width in pixels.
   * @param maxWidth Maximum width in pixels.
   * @param preferredWidth Preferred width in pixels.
   */
  public void setWidthSpecs(int minWidth, int maxWidth, int preferredWidth)
  {
    _minWidth=minWidth;
    _maxWidth=maxWidth;
    _preferredWidth=preferredWidth;
  }

  /**
   * Indicates if this column is sortable or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isSortable()
  {
    return _sortable;
  }

  /**
   * Set the sortable flag.
   * @param sortable Flag value to set.
   */
  public void setSortable(boolean sortable)
  {
    _sortable=sortable;
  }

  @Override
  public TableCellRenderer getCellRenderer()
  {
    return _renderer;
  }

  /**
   * Set a specific cell renderer.
   * @param renderer Renderer to set.
   */
  public void setCellRenderer(TableCellRenderer renderer)
  {
    _renderer=renderer;
  }

  @Override
  public TableCellEditor getCellEditor()
  {
    return _editor;
  }

  /**
   * Set a specific cell editor.
   * @param editor Editor to set.
   */
  public void setCellEditor(TableCellEditor editor)
  {
    _editor=editor;
  }

  /**
   * Get the associated cell renderer, if any.
   * @return A renderer or <code>null</code> to use defaults.
   */
  public TableCellRenderer getHeaderCellRenderer()
  {
    return _headerCellRenderer;
  }

  /**
   * Set a specific header cell renderer.
   * @param headerCellRenderer Renderer to set.
   */
  public void setHeaderCellRenderer(TableCellRenderer headerCellRenderer)
  {
    _headerCellRenderer=headerCellRenderer;
  }

  /**
   * Indicates if this column is editable or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEditable()
  {
    return _editable;
  }

  /**
   * Set the 'editable' flag.
   * @param editable <code>true</code> if it is, <code>false</code> otherwise.
   */
  public void setEditable(boolean editable)
  {
    _editable=editable;
  }

  /**
   * Indicates if the 'use to string' has been forced by the user.
   * @return A Boolean value or <code>null</code> to use defaults.
   */
  public Boolean isUseToString()
  {
    return _useToString;
  }

  /**
   * Set a specific value for 'use to string'.
   * @param useToString Value to set.
   */
  public void setUseToString(Boolean useToString)
  {
    _useToString=useToString;
  }

  /**
   * Get the comparator to use for this column.
   * @return A comparator or <code>null</code> if none.
   */
  public Comparator<VALUE> getComparator()
  {
    return _comparator;
  }

  /**
   * Set the comparator to use for this column.
   * @param comparator Comparator to set (may be <code>null</code>).
   */
  public void setComparator(Comparator<VALUE> comparator)
  {
    _comparator=comparator;
  }

  /**
   * Get the type of data in the managed column.
   * @return a data type.
   */
  public Class<? extends VALUE> getDataType()
  {
    return _dataType;
  }

  /**
   * Get the header label for this column.
   * @return a label.
   */
  public String getHeader()
  {
    return _header;
  }

  /**
   * Get the cell value provider for this column.
   * @return a cell value provider.
   */
  public CellDataProvider<POJO,VALUE> getValueProvider()
  {
    return _valueProvider;
  }

  /**
   * Get the cell value updater for this column.
   * @return a cell value updater.
   */
  public CellDataUpdater<POJO> getValueUpdater()
  {
    return _valueUpdater;
  }

  /**
   * Set the value updater.
   * @param valueUpdater updater to set.
   */
  public void setValueUpdater(CellDataUpdater<POJO> valueUpdater)
  {
    _valueUpdater=valueUpdater;
  }

  @Override
  public ActionListener getActionListener()
  {
    return _actionListener;
  }

  /**
   * Set the action listener for this column.
   * @param actionListener
   */
  public void setActionListener(ActionListener actionListener)
  {
    _actionListener=actionListener;
  }

  @Override
  public void dispose()
  {
    _valueProvider=null;
    _valueUpdater=null;
    if (_renderer instanceof Disposable)
    {
      ((Disposable)_renderer).dispose();
      _renderer=null;
    }
    _editor=null;
    _comparator=null;
    _headerCellRenderer=null;
    _actionListener=null;
  }

  @Override
  public String toString()
  {
    return _header;
  }
}

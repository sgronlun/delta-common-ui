package delta.common.ui.swing.tables;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.tables.renderers.ButtonRenderer;
import delta.common.utils.NumericTools;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.misc.TypedProperties;

/**
 * Generic controller for a table.
 * @param <POJO> Type of tabled data.
 * @author DAM
 */
public class GenericTableController<POJO>
{
  /**
   * Double-click action command.
   */
  public static final String DOUBLE_CLICK="double click";
  /**
   * Button action command.
   */
  public static final String BUTTON="button";
  // Data
  private DataProvider<POJO> _dataProvider;
  private Filter<POJO> _filter;
  private Sort _sort;
  // Columns
  private TableColumnsManager<POJO> _columns;
  // GUI
  private JTable _table;
  private GenericTableModel<POJO> _model;
  private TableRowSorter<GenericTableModel<POJO>> _sorter;
  // Control
  private List<ActionListener> _actionListeners;

  /**
   * Constructor.
   * @param dataProvider Data provider.
   */
  public GenericTableController(DataProvider<POJO> dataProvider)
  {
    _dataProvider=dataProvider;
    _filter=null;
    _columns=new TableColumnsManager<POJO>();
    _actionListeners=new ArrayList<ActionListener>();
  }

  /**
   * Get the columns manager.
   * @return the columns manager.
   */
  public TableColumnsManager<POJO> getColumnsManager()
  {
    return _columns;
  }

  /**
   * Refresh table.
   */
  public void refresh()
  {
    if (_table!=null)
    {
      _model.fireTableDataChanged();
    }
  }

  /**
   * Refresh an item in this table.
   * @param dataItem Data item to refresh.
   */
  public void refresh(POJO dataItem)
  {
    if (_table!=null)
    {
      int row=getRowForItem(dataItem);
      if (row!=-1)
      {
        _model.fireTableRowsUpdated(row,row);
      }
    }
  }

  /**
   * Get the managed table.
   * @return the managed table.
   */
  public JTable getTable()
  {
    if (_table==null)
    {
      _table=build();
      applySort(_sort);
    }
    return _table;
  }

  private JTable build()
  {
    _model=new GenericTableModel<POJO>(this,_dataProvider);
    final JTable table=GuiFactory.buildTable();
    table.setModel(_model);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    _sorter=new TableRowSorter<GenericTableModel<POJO>>(_model)
    {
      @Override
      protected boolean useToString(int column)
      {
        TableColumnController<POJO,?> columnController=_columns.getAt(column);
        Boolean useToString=columnController.isUseToString();
        if (useToString!=null)
        {
          return useToString.booleanValue();
        }
        return super.useToString(column);
      }
    };
    _sorter.setSortsOnUpdates(true);
    initColumns(table);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    _sorter.setMaxSortKeys(3);
    table.setRowSorter(_sorter);

    // Filter
    RowFilter<GenericTableModel<POJO>,Integer> guiFilter=new RowFilter<GenericTableModel<POJO>,Integer>()
    {
      @Override
      public boolean include(RowFilter.Entry<? extends GenericTableModel<POJO>,? extends Integer> entry)
      {
        boolean ret=true;
        if (_filter!=null)
        {
          Integer id=entry.getIdentifier();
          POJO item=_dataProvider.getAt(id.intValue());
          ret=_filter.accept(item);
        }
        return ret;
      }
    };
    _sorter.setRowFilter(guiFilter);

    // Double click management
    MouseAdapter doucleClickAdapter=new MouseAdapter()
    {
      @Override
      public void mouseClicked(MouseEvent e)
      {
        Point p = e.getPoint();
        int row = table.convertRowIndexToModel(table.rowAtPoint(p));
        int column = table.convertColumnIndexToModel(table.columnAtPoint(p));
        if (row >= 0 && column >= 0)
        {
          int nbClicks=e.getClickCount();
          if (nbClicks==2)
          {
            invokeDoubleClickAction(row,e);
          }
        }
      }
    };
    table.addMouseListener(doucleClickAdapter);
    return table;
  }

  private void initColumns(JTable table)
  {
    int i=0;
    List<TableColumnController<POJO,?>> controllers=_columns.getSelectedColumns();
    for(TableColumnController<POJO,?> controller : controllers)
    {
      TableColumn column=table.getColumnModel().getColumn(i);
      // Size
      int preferredWidth=controller.getPreferredWidth();
      if (preferredWidth>=0)
      {
        column.setPreferredWidth(preferredWidth);
      }
      int minWidth=controller.getMinWidth();
      if (minWidth>=0)
      {
        column.setMinWidth(minWidth);
      }
      int maxWidth=controller.getMaxWidth();
      if (maxWidth>=0)
      {
        column.setMaxWidth(maxWidth);
      }
      column.setResizable(true);
      // Cell renderer
      TableCellRenderer renderer=controller.getCellRenderer();
      if (renderer!=null)
      {
        column.setCellRenderer(renderer);
      }
      // Cell editor
      TableCellEditor editor=controller.getCellEditor();
      if (editor!=null)
      {
        column.setCellEditor(editor);
      }
      // Header cell renderer
      TableCellRenderer headerRenderer=controller.getHeaderCellRenderer();
      if (headerRenderer!=null)
      {
        column.setHeaderRenderer(headerRenderer);
      }
      // Sort
      boolean sortable=controller.isSortable();
      _sorter.setSortable(i,sortable);
      if (sortable)
      {
        Comparator<?> comparator=controller.getComparator();
        if (comparator!=null)
        {
          _sorter.setComparator(i,comparator);
        }
      }
      i++;
    }
  }

  /**
   * Indicates that columns were updated.
   */
  public void updateColumns()
  {
    _model.fireTableStructureChanged();
    initColumns(_table);
  }

  private void invokeDoubleClickAction(int row, MouseEvent sourceEvent)
  {
    POJO dataItem=_dataProvider.getAt(row);
    ActionEvent e=new ActionEvent(dataItem,ActionEvent.ACTION_PERFORMED,DOUBLE_CLICK,sourceEvent.getModifiers());
    ActionListener[] als=_actionListeners.toArray(new ActionListener[_actionListeners.size()]);
    for(ActionListener al : als)
    {
      al.actionPerformed(e);
    }
  }

  /**
   * Add a column controller.
   * @param controller Controller to add.
   */
  public void addColumnController(TableColumnController<POJO,?> controller)
  {
    _columns.addColumnController(controller,true);
  }

  /**
   * Get a column controller.
   * @param index Index of the targeted column, starting at 0.
   * @return A column controller.
   */
  public TableColumnController<POJO,?> getColumnController(int index)
  {
    return _columns.getAt(index);
  }

  /**
   * Get the number of columns.
   * @return the number of columns.
   */
  public int getColumnCount()
  {
    return _columns.getSelectedColumnsCount();
  }

  /**
   * Add an action listener.
   * @param al Action listener to add.
   */
  public void addActionListener(ActionListener al)
  {
    _actionListeners.add(al);
  }

  /**
   * Remove an action listener.
   * @param al Action listener to remove.
   */
  public void removeActionListener(ActionListener al)
  {
    _actionListeners.remove(al);
  }

  /**
   * Get the row for the given item.
   * @param item Item to search.
   * @return A row index or <code>-1</code> if not found.
   */
  public int getRowForItem(POJO item)
  {
    int ret=-1;
    int nb=_dataProvider.getCount();
    for(int i=0;i<nb;i++)
    {
      if (_dataProvider.getAt(i)==item)
      {
        ret=i;
        break;
      }
    }
    return ret;
  }

  /**
   * Set data filter.
   * @param filter Filter to set.
   */
  public void setFilter(Filter<POJO> filter)
  {
    _filter=filter;
  }

  /**
   * To be called when the filter was updated and the GUI needs a refresh.
   */
  public void filterUpdated()
  {
    _sorter.setRowFilter(_sorter.getRowFilter());
  }

  /**
   * Get the number of filtered items in the managed table.
   * @return A number of items.
   */
  public int getNbFilteredItems()
  {
    int ret=_table.getRowSorter().getViewRowCount();
    return ret;
  }

  /**
   * Get the number of items in the managed table.
   * @return A number of items.
   */
  public int getNbItems()
  {
    return _dataProvider.getCount();
  }

  /**
   * Get the object at the given row.
   * @param row Row to use, starting at 0.
   * @return An object.
   */
  public POJO getAt(int row)
  {
    return _dataProvider.getAt(row);
  }

  /**
   * Get the currently selected item.
   * @return An item or <code>null</code> if not found.
   */
  public POJO getSelectedItem()
  {
    POJO ret=null;
    int selectedItemIndex=_table.getSelectedRow();
    if (selectedItemIndex!=-1)
    {
      int modelIndex=_table.convertRowIndexToModel(selectedItemIndex);
      ret=_dataProvider.getAt(modelIndex);
    }
    return ret;
  }

  /**
   * Select an item.
   * @param item Item to select.
   */
  public void selectItem(POJO item)
  {
    int index=-1;
    int modelIndex=getItemIndex(item);
    if (modelIndex!=-1)
    {
      index=_table.convertRowIndexToView(modelIndex);
    }
    ListSelectionModel selectionModel=_table.getSelectionModel();
    if (index!=-1)
    {
      selectionModel.setSelectionInterval(index,index);
    }
    else
    {
      selectionModel.clearSelection();
    }
  }

  private int getItemIndex(POJO item)
  {
    int index=-1;
    if (item!=null)
    {
      int nb=_dataProvider.getCount();
      for(int i=0;i<nb;i++)
      {
        POJO currentItem=_dataProvider.getAt(i);
        if (currentItem==item)
        {
          index=i;
          break;
        }
      }
    }
    return index;
  }

  /**
   * Set sort.
   * @param sort Sort specification.
   */
  public void setSort(Sort sort)
  {
    _sort=sort;
    if (_table!=null)
    {
      applySort(_sort);
    }
  }

  private void applySort(Sort sort)
  {
    if (sort!=null)
    {
      List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
      int nbItems=sort.getNumberOfItems();
      for(int i=0;i<nbItems;i++)
      {
        String id=sort.getColumnId(i);
        boolean ascending=sort.isAscending(i);
        Integer columnIndex=_columns.getColumnIndex(id);
        if (columnIndex!=null)
        {
          SortOrder order=ascending?SortOrder.ASCENDING:SortOrder.DESCENDING;
          sortKeys.add(new RowSorter.SortKey(columnIndex.intValue(), order));
        }
      }
      _sorter.setSortKeys(sortKeys);
    }
  }

  /**
   * Get the current sort.
   * @return the current sort.
   */
  public Sort getCurrentSort()
  {
    Sort sort=new Sort();
    List<? extends SortKey> sortKeys=_sorter.getSortKeys();
    for(SortKey sortKey : sortKeys)
    {
      int columnIndex=sortKey.getColumn();
      SortOrder order=sortKey.getSortOrder();
      if (order!=SortOrder.UNSORTED)
      {
        TableColumnController<POJO,?> columnManager=_columns.getAt(columnIndex);
        String id=columnManager.getId();
        boolean ascending=(order==SortOrder.ASCENDING);
        sort.addSortItem(id,ascending);
      }
    }
    return sort;
  }

  /**
   * Build a button column.
   * @param id Column identifier.
   * @param label Button label.
   * @param width Column width.
   * @return a column.
   */
  public DefaultTableColumnController<POJO,String> buildButtonColumn(String id, final String label, int width)
  {
    CellDataProvider<POJO,String> cell=new CellDataProvider<POJO,String>()
    {
      @Override
      public String getData(POJO item)
      {
        return label;
      }
    };
    final DefaultTableColumnController<POJO,String> column=new DefaultTableColumnController<POJO,String>(id,"",String.class,cell);
    column.setWidthSpecs(width,width,width);
    column.setEditable(true);

    ButtonRenderer renderer=new ButtonRenderer();
    column.setCellRenderer(renderer);
    column.setCellEditor(renderer);
    ActionListener al=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent event)
      {
        int row=NumericTools.parseInt(event.getActionCommand(),-1);
        if (row>=0)
        {
          row=_table.convertRowIndexToModel(row);
          POJO item=getAt(row);
          ActionListener columnAl=column.getActionListener();
          if (columnAl!=null)
          {
            ActionEvent e=new ActionEvent(item,ActionEvent.ACTION_PERFORMED,BUTTON,event.getModifiers());
            columnAl.actionPerformed(e);
          }
        }
      }
    };
    renderer.setActionListener(al);
    return column;
  }

  /**
   * Save current table state in preferences.
   * @param prefs Storage for preferences.
   */
  public void savePreferences(TypedProperties prefs)
  {
    // Selected columns
    List<String> columnIds=_columns.getSelectedColumnsIds();
    prefs.setStringList(TablePreferencesProperties.COLUMNS_PROPERTY,columnIds);
    // Sort
    Sort sort=getCurrentSort();
    prefs.setStringProperty(TablePreferencesProperties.SORT_PROPERTY,sort.asString());
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // GUI
    if (_table!=null)
    {
      _table.setModel(new DefaultTableModel());
      _table=null;
    }
    _model=null;
    _sorter=null;
    _actionListeners.clear();
    // Data
    _dataProvider=null;
    _filter=null;
    _sort=null;
    // Columns
    if (_columns!=null)
    {
      _columns.dispose();
      _columns=null;
    }
  }

  /**
   * Date renderer.
   * @author DAM
   */
  public static class DateRenderer extends DefaultTableCellRenderer
  {
    private DateFormat _formatter;

    /**
     * Constructor.
     * @param format Date format.
     */
    public DateRenderer(String format)
    {
      _formatter=new SimpleDateFormat(format);
    }

    /**
     * Constructor.
     * @param format Date format.
     */
    public DateRenderer(DateFormat format)
    {
      _formatter=format;
    }

    @Override
    public void setValue(Object value)
    {
      setText((value == null) ? "" : _formatter.format(value));
    }
  }

  /**
   * Number renderer.
   * @author DAM
   */
  public static class NumberRenderer extends DefaultTableCellRenderer
  {
    private NumberFormat _formatter;
    /**
     * Constructor.
     * @param formatter Number formatter.
     */
    public NumberRenderer(NumberFormat formatter)
    {
      setHorizontalAlignment(SwingConstants.RIGHT);
      _formatter=formatter;
    }

    @Override
    public void setValue(Object value)
    {
      setText((value == null) ? "" : _formatter.format(value));
    }
  }
}

package delta.common.ui.swing.tables.context;

import java.util.HashMap;
import java.util.Map;

import delta.common.utils.context.Context;
import delta.common.utils.context.SimpleContextImpl;

/**
 * Table contexts manager.
 * @author DAM
 */
public class TableContextManager
{
  private Map<Integer,Context> _rowContexts;
  private Map<Integer,Context> _columnContexts;
  private Map<Integer,Map<Integer,Context>> _cellContexts;

  /**
   * Constructor.
   */
  public TableContextManager()
  {
    _rowContexts=new HashMap<Integer,Context>();
    _columnContexts=new HashMap<Integer,Context>();
    _cellContexts=new HashMap<Integer,Map<Integer,Context>>();
  }

  /**
   * Get the context for a single cell.
   * @param row Row index (starting at 0).
   * @param column Column index (starting at 0).
   * @return A context or <code>null</code> if none.
   */
  public Context getContext(int row, int column)
  {
    // Cell?
    Context ret=getCellContext(row,column);
    if (ret!=null)
    {
      return ret;
    }
    // Row?
    ret=getRowContext(row);
    if (ret!=null)
    {
      return ret;
    }
    // Column?
    ret=getColumnContext(column);
    if (ret!=null)
    {
      return ret;
    }
    // Global
    return null;
  }

  private Context getCellContext(int row, int column)
  {
    Integer rowKey=Integer.valueOf(row);
    Map<Integer,Context> columnsContext=_cellContexts.get(rowKey);
    if (columnsContext==null)
    {
      return null;
    }
    Integer columnKey=Integer.valueOf(column);
    Context ret=columnsContext.get(columnKey);
    return ret;
  }

  private Context getRowContext(int row)
  {
    Integer rowKey=Integer.valueOf(row);
    return _rowContexts.get(rowKey);
  }

  private Context getColumnContext(int column)
  {
    Integer columnKey=Integer.valueOf(column);
    return _columnContexts.get(columnKey);
  }

  /**
   * Set a cell context value.
   * @param row Row index (starting at 0).
   * @param column Column index (starting at 0).
   * @param key Key to use.
   * @param value Value to set.
   */
  public void setCellContext(int row, int column, String key, Object value)
  {
    Integer rowKey=Integer.valueOf(row);
    Map<Integer,Context> columnContexts=_cellContexts.get(rowKey);
    if (columnContexts==null)
    {
      columnContexts=new HashMap<Integer,Context>();
      _cellContexts.put(rowKey,columnContexts);
    }
    Integer columnKey=Integer.valueOf(column);
    Context context=columnContexts.get(columnKey);
    if (context==null)
    {
      context=new SimpleContextImpl();
      columnContexts.put(columnKey,context);
    }
    context.setValue(key,value);
  }

  /**
   * Set a row context value.
   * @param row Row index (starting at 0).
   * @param key Key to use.
   * @param value Value to set.
   */
  public void setRowContext(int row, String key, Object value)
  {
    Integer contextKey=Integer.valueOf(row);
    Context context=_rowContexts.get(contextKey);
    if (context==null)
    {
      context=new SimpleContextImpl();
      _rowContexts.put(contextKey,context);
    }
    context.setValue(key,value);
  }

  /**
   * Set a column context value.
   * @param column Column index (starting at 0).
   * @param key Key to use.
   * @param value Value to set.
   */
  public void setColumnContext(int column, String key, Object value)
  {
    Integer contextKey=Integer.valueOf(column);
    Context context=_columnContexts.get(contextKey);
    if (context==null)
    {
      context=new SimpleContextImpl();
      _columnContexts.put(contextKey,context);
    }
    context.setValue(key,value);
  }
}

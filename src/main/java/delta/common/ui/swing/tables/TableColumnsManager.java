package delta.common.ui.swing.tables;

import java.util.ArrayList;
import java.util.List;

/**
 * Columns manager for a table.
 * @param <POJO> Type of tabled data.
 * @author DAM
 */
public class TableColumnsManager<POJO>
{
  // Available columns
  private List<TableColumnController<POJO,?>> _availableColumns;
  // Selected columns
  private List<TableColumnController<POJO,?>> _selectedColumns;

  /**
   * Constructor.
   */
  public TableColumnsManager()
  {
    _availableColumns=new ArrayList<TableColumnController<POJO,?>>();
    _selectedColumns=new ArrayList<TableColumnController<POJO,?>>();
  }

  /**
   * Get the available columns.
   * @return the available columns.
   */
  public List<TableColumnController<POJO,?>> getAvailableColumns()
  {
    return _availableColumns;
  }

  /**
   * Get the current column at the given index.
   * @param index An index, starting at 0.
   * @return A table column controller.
   */
  public TableColumnController<POJO,?> getAt(int index)
  {
    return _selectedColumns.get(index);
  }

  /**
   * Get the selected columns.
   * @return the selected columns.
   */
  public List<TableColumnController<POJO,?>> getSelectedColumns()
  {
    return _selectedColumns;
  }

  /**
   * Set the selected columns.
   * @param selectedColumns Selected columns.
   */
  public void setSelectedColumns(List<TableColumnController<POJO,?>> selectedColumns)
  {
    _selectedColumns.clear();
    _selectedColumns.addAll(selectedColumns);
  }

  /**
   * Get the number of selected columns.
   * @return A columns count.
   */
  public int getSelectedColumnsCount()
  {
    return _selectedColumns.size();
  }

  /**
   * Add a column controller.
   * @param controller Controller to add.
   */
  public void addColumnController(TableColumnController<POJO,?> controller)
  {
    if (!_availableColumns.contains(controller))
    {
      _availableColumns.add(controller);
    }
    if (!_selectedColumns.contains(controller))
    {
      _selectedColumns.add(controller);
    }
  }

  /**
   * Remove a column from the selected ones.
   * @param controller Controller to remove.
   */
  public void removeSelectedColumn(TableColumnController<POJO,?> controller)
  {
    _selectedColumns.remove(controller);
  }
}

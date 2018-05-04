package delta.common.ui.swing.tables;

import java.util.Comparator;

import javax.swing.table.TableCellRenderer;

/**
 * Controller for a column of a generic table.
 * @param <POJO> Type of data items.
 * @param <VALUE> Value type of the managed column.
 * @author DAM
 */
public interface TableColumnController<POJO,VALUE>
{
  /**
   * Get the identifier for this column.
   * @return the identifier for this column.
   */
  String getId();

  /**
   * Get the minimum width for this column.
   * @return a value in pixels.
   */
  int getMinWidth();

  /**
   * Get the maximum width for this column.
   * @return a value in pixels (-1 means no limit).
   */
  int getMaxWidth();

  /**
   * Get the preferred width for this column.
   * @return a value in pixels.
   */
  int getPreferredWidth();

  /**
   * Indicates if this column is sortable or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  boolean isSortable();

  /**
   * Get the associated cell renderer, if any.
   * @return A renderer or <code>null</code> to use defaults.
   */
  TableCellRenderer getCellRenderer();

  /**
   * Get the associated cell renderer, if any.
   * @return A renderer or <code>null</code> to use defaults.
   */
  TableCellRenderer getHeaderCellRenderer();

  /**
   * Indicates if this column is editable or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  boolean isEditable();

  /**
   * Indicates if the 'use to string' has been forced by the user.
   * @return A Boolean value or <code>null</code> to use defaults.
   */
  Boolean isUseToString();

  /**
   * Get the comparator to use for this column.
   * @return A comparator or <code>null</code> if none.
   */
  Comparator<VALUE> getComparator();

  /**
   * Get the type of data in the managed column.
   * @return a data type.
   */
  Class<VALUE> getDataType();

  /**
   * Get the header label for this column.
   * @return a label.
   */
  String getHeader();

  /**
   * Get the cell value provider for this column.
   * @return a cell value provider.
   */
  CellDataProvider<POJO,VALUE> getValueProvider();

  /**
   * Get the cell value updater for this column.
   * @return a cell value updater.
   */
  CellDataUpdater<POJO> getValueUpdater();
}

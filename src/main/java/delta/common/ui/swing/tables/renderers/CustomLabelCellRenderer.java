package delta.common.ui.swing.tables.renderers;

import javax.swing.JLabel;

import delta.common.ui.swing.tables.GenericTableController;

/**
 * Interface of a custom label cell renderer.
 * @param <POJO> Type of POJOs managed by the table.
 * @param <VALUE> Type of the values in the managed column.
 * @author DAM
 */
public interface CustomLabelCellRenderer<POJO,VALUE>
{
  /**
   * Configure a label.
   * @param table Parent table.
   * @param value Value to show.
   * @param isSelected Selection state.
   * @param hasFocus Focus state.
   * @param row Row index (starting at 0).
   * @param column Column index (starting at 0).
   * @param label Label to configure.
   */
  void configure(GenericTableController<POJO> table, VALUE value, boolean isSelected, boolean hasFocus, int row, int column, JLabel label);
}

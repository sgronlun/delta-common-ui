package delta.common.ui.swing.tables.renderers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import delta.common.ui.swing.tables.GenericTableController;

/**
 * Renderer for a table cell that uses a JLabel.
 * @param <POJO> Type of POJOs managed by the table.
 * @param <VALUE> Type of the values in the managed column.
 * @author DAM
 */
public class LabelTableCellRenderer<POJO,VALUE> implements TableCellRenderer
{
  private GenericTableController<POJO> _table;
  private DefaultTableCellRenderer _renderer;
  private CustomLabelCellRenderer<POJO,VALUE> _customRenderer;

  /**
   * Constructor.
   * @param table Managed table.
   * @param customRenderer Custom label cell renderer.
   */
  public LabelTableCellRenderer(GenericTableController<POJO> table, CustomLabelCellRenderer<POJO,VALUE> customRenderer)
  {
    _table=table;
    _renderer=new DefaultTableCellRenderer();
    _customRenderer=customRenderer;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    JLabel label=(JLabel)_renderer.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
    int rowIndex=table.convertRowIndexToModel(row);
    int columnIndex=table.convertColumnIndexToModel(column);
    _customRenderer.configure(_table,(VALUE)value,isSelected,hasFocus,rowIndex,columnIndex,label);
    return label;
  }

}

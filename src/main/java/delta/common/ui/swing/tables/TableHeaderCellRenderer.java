package delta.common.ui.swing.tables;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 * A table header cell renderer that uses a tooltip.
 * @author DAM
 */
public class TableHeaderCellRenderer extends DefaultTableCellRenderer
{
  /**
   * Constructor.
   */
  public TableHeaderCellRenderer()
  {
    super();
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(SwingConstants.CENTER);
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    if (table!=null)
    {
      JTableHeader header=table.getTableHeader();
      if (header!=null)
      {
        setForeground(header.getForeground());
        setBackground(header.getBackground());
        setFont(header.getFont());
      }
    }
    String text=(value==null)?"":value.toString();
    setText(text);
    setToolTipText(text);
    return this;
  }
}

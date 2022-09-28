package delta.common.ui.swing.tables.export;

import java.util.List;

import javax.swing.Icon;
import javax.swing.JTable;

import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.tables.TableColumnController;
import delta.common.ui.swing.tables.TableColumnsManager;

/**
 * Table data exporter.
 * @author DAM
 * @param <POJO> Type of managed data.
 */
public class DataExport<POJO>
{
  private ExportDataOutput _output;

  /**
   * Constructor.
   * @param output Output.
   */
  public DataExport(ExportDataOutput output)
  {
    _output=output;
  }

  /**
   * Export table data.
   * @param table Table to export.
   */
  public void export(GenericTableController<POJO> table)
  {
    JTable jtable=table.getTable();
    int nbRows=table.getNbFilteredItems();
    TableColumnsManager<POJO> columnsMgr=table.getColumnsManager();
    List<TableColumnController<POJO,?>> columns=columnsMgr.getSelectedColumns();
    int nbColumns=jtable.getColumnCount();
    // Headers
    String[] headers=new String[nbColumns];
    for(int j=0;j<nbColumns;j++)
    {
      int columnIndex=jtable.convertColumnIndexToModel(j);
      TableColumnController<POJO,?> column=columns.get(columnIndex);
      headers[j]=column.getHeader();
    }
    _output.writeData(headers);
    // Values
    String[] values=new String[nbColumns];
    for(int i=0;i<nbRows;i++)
    {
      POJO pojo=table.getAtViewIndex(i);
      for(int j=0;j<nbColumns;j++)
      {
        int columnIndex=jtable.convertColumnIndexToModel(j);
        TableColumnController<POJO,?> column=columns.get(columnIndex);
        Object data=column.getValueProvider().getData(pojo);
        values[j]=toString(data);
      }
      _output.writeData(values);
    }
  }

  private String toString(Object data)
  {
    if (data==null)
    {
      return "";
    }
    if (data instanceof Icon)
    {
      return "icon";
    }
    return data.toString();
  }
}

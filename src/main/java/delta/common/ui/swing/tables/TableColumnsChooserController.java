package delta.common.ui.swing.tables;

import java.util.Comparator;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import delta.common.ui.swing.lists.OrderedItemsSelectionController;
import delta.common.ui.swing.windows.DefaultFormDialogController;
import delta.common.ui.swing.windows.WindowController;

/**
 * Controller for a table columns chooser.
 * @param <POJO> Type of data items.
 * @author DAM
 */
public class TableColumnsChooserController<POJO> extends DefaultFormDialogController<String>
{
  private OrderedItemsSelectionController<TableColumnController<POJO,?>> _selectionController;
  private GenericTableController<POJO> _table;

  private static class TableColumnsComparator<POJO> implements Comparator<TableColumnController<POJO,?>>
  {
    public int compare(TableColumnController<POJO,?> o1, TableColumnController<POJO,?> o2)
    {
      return o1.getHeader().compareTo(o2.getHeader());
    }
  }

  /**
   * Constructor.
   * @param parent Parent controller.
   * @param table  Associated table.
   */
  public TableColumnsChooserController(WindowController parent, GenericTableController<POJO> table)
  {
    super(parent,null);
    _table=table;
    TableColumnsComparator<POJO> comparator=new TableColumnsComparator<POJO>();
    _selectionController=new OrderedItemsSelectionController<TableColumnController<POJO,?>>(comparator);
    TableColumnsManager<POJO> columnsManager=_table.getColumnsManager();
    _selectionController.setItems(columnsManager.getAvailableColumns());
    _selectionController.selectItems(columnsManager.getSelectedColumns());
  }

  @Override
  protected JDialog build()
  {
    JDialog dialog=super.build();
    dialog.setTitle("Choose columns...");
    return dialog;
  }

  @Override
  protected JPanel buildFormPanel()
  {
    return _selectionController.getPanel();
  }

  @Override
  protected void okImpl()
  {
    List<TableColumnController<POJO,?>> selectedColumns=_selectionController.getSelectedItems();
    TableColumnsManager<POJO> columnsManager=_table.getColumnsManager();
    columnsManager.setSelectedColumns(selectedColumns);
    _table.updateColumns();
  }
}

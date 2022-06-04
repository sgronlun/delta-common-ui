package delta.common.ui.swing.tables.selection;

import javax.swing.ListSelectionModel;

import delta.common.ui.swing.selection.SelectionManager;
import delta.common.ui.swing.tables.GenericTableController;

/**
 * Selection manager.
 * @author DAM
 * @param <POJO> Type of managed data.
 */
public class TableSelectionManager<POJO> extends SelectionManager<POJO>
{
  // Parent controller
  private GenericTableController<POJO> _tableController;

  /**
   * Constructor.
   * @param tableController Parent table controller.
   */
  public TableSelectionManager(GenericTableController<POJO> tableController)
  {
    super();
    _tableController=tableController;
  }

  @Override
  protected ListSelectionModel getSelectionModel()
  {
    return _tableController.getTable().getSelectionModel();
  }

  @Override
  protected POJO resolveElement(int index)
  {
    return _tableController.getAtViewIndex(index);
  }

  @Override
  public void dispose()
  {
    super.dispose();
    _tableController=null;
  }
}

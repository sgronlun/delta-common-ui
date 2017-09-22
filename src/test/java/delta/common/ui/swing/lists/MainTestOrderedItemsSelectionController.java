package delta.common.ui.swing.lists;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import delta.common.ui.swing.windows.DefaultFormDialogController;

/**
 * Simple test class for the ordered items selection controller.
 * @author dm
 */
public class MainTestOrderedItemsSelectionController
{
  /**
   * Controller for a table columns chooser.
   * @author DAM
   */
  public static class ChooserDialog extends DefaultFormDialogController<String>
  {
    private OrderedItemsSelectionController<String> _selectionController;

    /**
     * Constructor.
     */
    public ChooserDialog()
    {
      super(null,null);
      _selectionController=new OrderedItemsSelectionController<String>(null);
      List<String> all=new ArrayList<String>();
      all.add("Orange");
      all.add("Red");
      all.add("Green");
      _selectionController.setItems(all);
    }

    @Override
    protected JPanel buildFormPanel()
    {
      return _selectionController.getPanel();
    }

    @Override
    protected void okImpl()
    {
      System.out.println("Selected:" + _selectionController.getSelectedItems());
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new ChooserDialog().editModal();
  }
}

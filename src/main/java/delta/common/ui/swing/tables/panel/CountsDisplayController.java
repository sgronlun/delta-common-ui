package delta.common.ui.swing.tables.panel;

import javax.swing.JLabel;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.misc.Disposable;
import delta.common.ui.swing.tables.GenericTableController;

/**
 * Controller for a label to display the elements count in a table.
 * @param <T> Type of managed table entries.
 * @author DAM
 */
public class CountsDisplayController<T> implements Disposable
{
  private GenericTableController<T> _tableController;
  private JLabel _label;
  private String _text;

  /**
   * Constructor.
   * @param tableController Source table controller.
   */
  public CountsDisplayController(GenericTableController<T> tableController)
  {
    _tableController=tableController;
    _label=GuiFactory.buildLabel("-");
    _text="Elements";
  }

  /**
   * Set the text to use.
   * @param text Text to use.
   */
  public void setText(String text)
  {
    _text=text;
  }

  /**
   * Get the managed label.
   * @return the managed label.
   */
  public JLabel getLabel()
  {
    return _label;
  }

  /**
   * Update the counters.
   */
  public void update()
  {
    int nbFiltered=_tableController.getNbFilteredItems();
    int nbItems=_tableController.getNbItems();
    String label="";
    if (nbFiltered==nbItems)
    {
      label=_text+": "+nbItems;
    }
    else
    {
      label=_text+": "+nbFiltered+"/"+nbItems;
    }
    _label.setText(label);
  }

  @Override
  public void dispose()
  {
    _tableController=null;
    _label=null;
  }
}

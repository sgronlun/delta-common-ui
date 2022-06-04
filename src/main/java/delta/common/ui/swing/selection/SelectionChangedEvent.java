package delta.common.ui.swing.selection;

import java.util.ArrayList;
import java.util.List;

/**
 * Selection changed event.
 * @param <POJO> Type of managed elements.
 * @author DAM
 */
public class SelectionChangedEvent<POJO>
{
  private List<POJO> _selectedElements;

  /**
   * Constructor.
   * @param selectedElements Selected elements.
   */
  public SelectionChangedEvent(List<POJO> selectedElements)
  {
    _selectedElements=new ArrayList<POJO>(selectedElements);
  }

  /**
   * Get the selected elements.
   * @return a list of elements.
   */
  public List<POJO> getSelectedElements()
  {
    return _selectedElements;
  }

  @Override
  public String toString()
  {
    return "Selection Changed Event: new selection="+_selectedElements;
  }
}

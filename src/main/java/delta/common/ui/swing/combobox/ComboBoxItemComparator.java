package delta.common.ui.swing.combobox;

import java.util.Comparator;

/**
 * Comparator for ComboBoxItems using their label.
 * @author DAM
 * @param <T> Type of items.
 */
public class ComboBoxItemComparator<T> implements Comparator<ComboBoxItem<T>>
{
  @Override
  public int compare(ComboBoxItem<T> o1, ComboBoxItem<T> o2)
  {
    if (o1==null)
    {
      return (o2!=null)?-1:0;
    }
    if (o2!=null)
    {
      return o1.getLabel().compareTo(o2.getLabel());
    }
    return 1;
  }
}

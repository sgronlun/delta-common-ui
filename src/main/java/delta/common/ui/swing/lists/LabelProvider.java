package delta.common.ui.swing.lists;

/**
 * Interface of a label provider.
 * @author DAM
 * @param <T> Type of managed items.
 */
public interface LabelProvider<T>
{
  /**
   * Get a label for the given item.
   * @param item Item to use.
   * @return A label.
   */
  String getLabel(T item);
}

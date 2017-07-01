package delta.common.ui.swing.combobox;

/**
 * Interface of an item selection callback.
 * @param <T> Type of managed data.
 * @author DAM
 */
public interface ItemSelectionListener<T>
{
  /**
   * Called when an item was selected.
   * @param item Selected item.
   */
  void itemSelected(T item);
}

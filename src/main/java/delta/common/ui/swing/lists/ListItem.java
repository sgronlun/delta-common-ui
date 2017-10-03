package delta.common.ui.swing.lists;

/**
 * A list item that uses a custom label provider.
 * @param <T> Type of managed data.
 * @author DAM
 */
public class ListItem<T>
{
  private T _item;
  private LabelProvider<T> _labelProvider;

  /**
   * Constructor.
   * @param item Managed item.
   * @param labelProvider Associated label provider.
   */
  public ListItem(T item, LabelProvider<T> labelProvider)
  {
    _item=item;
    _labelProvider=labelProvider;
  }

  /**
   * Get the managed item.
   * @return the managed item.
   */
  public T getItem()
  {
    return _item;
  }

  @Override
  public String toString()
  {
    return (_labelProvider!=null)?_labelProvider.getLabel(_item):_item.toString();
  }
}

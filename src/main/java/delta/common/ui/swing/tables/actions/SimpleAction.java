package delta.common.ui.swing.tables.actions;

/**
 * Simple action on a data item.
 * @author DAM
 * @param <T> Type of managed data.
 */
public interface SimpleAction<T>
{
  /**
   * Invoke an action on the given data item.
   * @param data Targeted data item.
   */
  void doIt(T data);
}

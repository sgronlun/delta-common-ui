package delta.common.ui.swing.selection;

/**
 * Listener for selection changed events.
 * @param <POJO> Type of managed elements.
 * @author DAM
 */
public interface SelectionChangedListener<POJO>
{
  /**
   * Invoked when the current selection changed inside a component.
   * @param event Event.
   */
  void selectionChanged(SelectionChangedEvent<POJO> event);
}

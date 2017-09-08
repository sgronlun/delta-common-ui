package delta.common.ui.swing.dnd;

import java.awt.dnd.DropTargetDropEvent;

/**
 * Drag&drop listener for files.
 * @param <T> Type of dropped data.
 * @author DAM
 */
public interface DropListener<T>
{
  /**
   * Handle the 'drop' event.
   * @param data Dropped data.
   * @param sourceEvent Source DnD event.
   */
  void drop(T data, DropTargetDropEvent sourceEvent);
}

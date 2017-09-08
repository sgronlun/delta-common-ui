package delta.common.ui.swing.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTargetDropEvent;

/**
 * Interface of an object that can extract some data from a DnD event.
 * @author DAM
 * @param <T> Type of data to extract.
 */
public interface DataExtractor<T>
{
  /**
   * Extract data from a DnD event.
   * @param e Source event.
   * @param flavor Data flavor to use.
   * @return Some data or <code>null</code> if it is not possible.
   */
  T extractData(DropTargetDropEvent e, DataFlavor flavor);
}

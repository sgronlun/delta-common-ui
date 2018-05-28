package delta.common.ui.swing.text.range;

/**
 * Listener for range updates.
 * @author DAM
 */
public interface RangeListener
{
  /**
   * Called when the <code>source</code> range editor had a r
   * @param source
   * @param minValue
   * @param maxValue
   */
  void rangeUpdated(RangeEditorController source, Integer minValue, Integer maxValue);
}

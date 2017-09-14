package delta.common.ui.swing.text.dates;

/**
 * Date value listener.
 * @author DAM
 */
public interface DateListener
{
  /**
   * Called when the date of a gadget has changed.
   * @param source Source controller.
   * @param newDate New value.
   */
  void dateChanged(DateEditionController source, Long newDate);
}

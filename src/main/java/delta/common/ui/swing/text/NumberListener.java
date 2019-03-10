package delta.common.ui.swing.text;

/**
 * Number value listener.
 * @param <T> Type of numbers.
 * @author DAM
 */
public interface NumberListener<T extends Number>
{
  /**
   * Called when the value of a gadget has changed.
   * @param source Source controller.
   * @param newValue New value.
   */
  void valueChanged(NumberEditionController<T> source, T newValue);
}

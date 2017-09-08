package delta.common.ui.swing.text.dates;

/**
 * Date coder/decoder.
 * @author DAM
 */
public interface DateCodec
{
  /**
   * Parse a date from a string.
   * @param dateStr Input date as a string.
   * @return A date or <code>null</code> if invalid.
   */
  Long parseDate(String dateStr);

  /**
   * Format a date as a string.
   * @param date Input date (may be <code>null</code>).
   * @return A date string.
   */
  String formatDate(Long date);
}

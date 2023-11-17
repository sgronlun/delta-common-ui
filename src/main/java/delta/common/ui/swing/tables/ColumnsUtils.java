package delta.common.ui.swing.tables;

import java.text.DateFormat;
import java.text.NumberFormat;

import delta.common.ui.swing.tables.GenericTableController.DateRenderer;
import delta.common.utils.l10n.LocalizedFormats;
import delta.common.utils.l10n.dates.DateFormatSpecification;

/**
 * Columns configuration utilities.
 * @author DAM
 */
public class ColumnsUtils
{
  /**
   * Configure a column to show an integer value.
   * @param column Column to use.
   */
  public static final void configureIntegerColumn(DefaultTableColumnController<?,Integer> column)
  {
    configureIntegerColumn(column,60);
  }

  /**
   * Configure a column to show an integer value.
   * @param column Column to use.
   * @param width Width to use.
   */
  public static final void configureIntegerColumn(DefaultTableColumnController<?,Integer> column, int width)
  {
    column.setWidthSpecs(width,width,width);
    NumberFormat format=LocalizedFormats.getIntegerNumberFormat();
    column.setCellRenderer(new GenericTableController.NumberRenderer(format));
  }

  /**
   * Configure a column to show a long value.
   * @param column Column to use.
   */
  public static final void configureLongColumn(DefaultTableColumnController<?,Long> column)
  {
    configureLongColumn(column,80);
  }

  /**
   * Configure a column to show a long value.
   * @param column Column to use.
   * @param width Width to use.
   */
  public static final void configureLongColumn(DefaultTableColumnController<?,Long> column, int width)
  {
    column.setWidthSpecs(width,width,width);
    NumberFormat format=LocalizedFormats.getIntegerNumberFormat();
    column.setCellRenderer(new GenericTableController.NumberRenderer(format));
  }

  /**
   * Configure a column to show a float value.
   * @param column Column to use.
   * @param minDigits Minimum number of digits.
   * @param maxDigits Maximum number of digits.
   * @param width Width to use.
   */
  public static final void configureFloatColumn(DefaultTableColumnController<?,Float> column, int minDigits, int maxDigits, int width)
  {
    column.setWidthSpecs(width,width,width);
    NumberFormat format=LocalizedFormats.getRealNumberFormat(minDigits,maxDigits);
    column.setCellRenderer(new GenericTableController.NumberRenderer(format));
  }

  /**
   * Configure a column to show a date/time value.
   * @param column Column to use.
   */
  public static final void configureDateTimeColumn(DefaultTableColumnController<?,?> column)
  {
    DateFormatSpecification spec=LocalizedFormats.getDateTimeFormatSpecification();
    int columnSize=spec.getColumnSize();
    configureDateTimeColumn(column,columnSize);
  }

  /**
   * Configure a column to show a date/time value.
   * @param column Column to use.
   * @param width Width to use.
   */
  private static final void configureDateTimeColumn(DefaultTableColumnController<?,?> column, int width)
  {
    column.setWidthSpecs(width,width,width);
    DateFormat format=LocalizedFormats.getDateTimeFormat();
    column.setCellRenderer(new DateRenderer(format));
  }

  /**
   * Configure a column to show a date value.
   * @param column Column to use.
   */
  public static final void configureDateColumn(DefaultTableColumnController<?,?> column)
  {
    DateFormatSpecification spec=LocalizedFormats.getDateFormatSpecification();
    int columnSize=spec.getColumnSize();
    configureDateColumn(column,columnSize);
  }

  /**
   * Configure a column to show a date value.
   * @param column Column to use.
   * @param width Width to use.
   */
  private static final void configureDateColumn(DefaultTableColumnController<?,?> column, int width)
  {
    column.setWidthSpecs(width,width,width);
    DateFormat format=LocalizedFormats.getDateFormat();
    column.setCellRenderer(new DateRenderer(format));
  }
}

package delta.common.ui.swing.tables.export;

/**
 * Interface for export data output.
 * @author DAM
 */
public interface ExportDataOutput
{
  /**
   * Output a single row of data.
   * @param data Data to push.
   */
  void writeData(String[] data);
}

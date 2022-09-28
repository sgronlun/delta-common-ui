package delta.common.ui.swing.tables.export;

import delta.common.utils.text.EndOfLine;

/**
 * Export data output that produces a string.
 * @author DAM
 */
public class StringExportDataOutput implements ExportDataOutput
{
  private StringBuilder _sb=new StringBuilder();

  @Override
  public void writeData(String[] data)
  {
    int index=0;
    for(String cell : data)
    {
      if (index>0)
      {
        _sb.append('\t');
      }
      if (cell!=null)
      {
        _sb.append(cell);
      }
      index++;
    }
    _sb.append(EndOfLine.NATIVE_EOL);
  }

  /**
   * Get the result as a string.
   * @return a string.
   */
  public String getResult()
  {
    return _sb.toString();
  }
}

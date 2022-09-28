package delta.common.ui.swing.tables.export;

/**
 * Simple implementation of the export data output.
 * @author DAM
 */
public class SimpleExportDataOutput implements ExportDataOutput
{
  @Override
  public void writeData(String[] data)
  {
    int index=0;
    for(String cell : data)
    {
      if (index>0)
      {
        System.out.print('\t');
      }
      if (cell!=null)
      {
        System.out.print(cell);
      }
      index++;
    }
    System.out.println();
  }
}

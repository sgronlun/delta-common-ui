package delta.common.ui.swing.tables;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import delta.common.ui.swing.GuiFactory;
import delta.common.utils.tables.DataTable;

/**
 * Controller for a panel that displays a table.
 * @author DAM
 */
public class RawTablePanelController
{
  private JPanel _panel;

  /**
   * Constructor.
   * @param headers Header labels.
   * @param data Cell data.
   */
  public RawTablePanelController(List<String> headers, List<Object[]> data)
  {
    _panel=buildPanel(headers,data);
  }

  /**
   * Constructor.
   * @param table Table.
   */
  public RawTablePanelController(DataTable table)
  {
    List<String> headers=new ArrayList<String>();
    int nbColumns=table.getNbColumns();
    for(int i=0;i<nbColumns;i++)
    {
      String columnName=table.getColumn(i).getName();
      headers.add(columnName);
    }
    List<Object[]> data=new ArrayList<Object[]>();
    int nbRows=table.getNbRows();
    for(int i=0;i<nbRows;i++)
    {
      Object[] row=new Object[nbColumns];
      for(int j=0;j<nbColumns;j++)
      {
        row[j]=table.getData(i,j);
      }
      data.add(row);
    }
    _panel=buildPanel(headers,data);
  }

  /**
   * Get the managed label.
   * @return the managed label.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  /**
   * Set a custom header cell.
   * @param x Start cell.
   * @param width Header width (cells count).
   * @param value Header text.
   * @param color Background color.
   */
  public void setHeaderCell(int x, int width, String value, Color color)
  {
    GridBagConstraints c=new GridBagConstraints(x,0,width,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(2,2,2,2),5,0);
    JLabel headerLabel=GuiFactory.buildLabel(value);
    headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    headerLabel.setText(value);
    headerLabel.setBackground(color);
    headerLabel.setOpaque(true);
    headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD,14));
    _panel.add(headerLabel,c);
  }

  private JPanel buildPanel(List<String> headers, List<Object[]> data)
  {
    GridBagConstraints c=new GridBagConstraints(0,1,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(2,2,2,2),0,0);
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
    // Headers
    for(String header : headers)
    {
      JLabel headerLabel=GuiFactory.buildLabel(header);
      headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD,14));
      panel.add(headerLabel,c);
      c.gridx++;
    }
    // Rows
    for(Object[] row : data)
    {
      c.gridy++;
      c.gridx=0;
      for(Object cell : row)
      {
        if (cell!=null)
        {
          JLabel cellLabel=GuiFactory.buildLabel(cell.toString());
          panel.add(cellLabel,c);
        }
        c.gridx++;
      }
    }
    return panel;
  }
}

package delta.common.ui.swing.tables;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;

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
   * Get the managed label.
   * @return the managed label.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  private JPanel buildPanel(List<String> headers, List<Object[]> data)
  {
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(2,2,2,2),0,0);
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

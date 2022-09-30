package delta.common.ui.swing.tables.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.tables.TableColumnsChooserController;
import delta.common.ui.swing.windows.WindowController;

/**
 * Panel controller for a generic table.
 * @param <T> Type of managed table entries.
 * @author DAM
 */
public class GenericTablePanelController<T> implements FilterUpdateListener
{
  // Data
  private GenericTableController<T> _tableController;
  // GUI
  private JPanel _panel;
  private CountsDisplayController<T> _countsController;
  // Controllers
  private WindowController _parent;
  // Configuration
  private GenericTablePanelConfiguration _configuration;

  /**
   * Constructor.
   * @param parent Parent window.
   * @param tableController Associated table controller.
   */
  public GenericTablePanelController(WindowController parent, GenericTableController<T> tableController)
  {
    _parent=parent;
    _tableController=tableController;
    _countsController=new CountsDisplayController<T>(tableController);
    _configuration=new GenericTablePanelConfiguration();
  }

  /**
   * Get the configuration for this panel.
   * @return the configuration for this panel.
   */
  public GenericTablePanelConfiguration getConfiguration()
  {
    return _configuration;
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    if (_panel==null)
    {
      _panel=build();
    }
    return _panel;
  }

  private JPanel build()
  {
    JPanel panel=GuiFactory.buildPanel(new BorderLayout());

    // Border
    String borderTitle=_configuration.getBorderTitle();
    if (borderTitle!=null)
    {
      TitledBorder border=GuiFactory.buildTitledBorder(borderTitle);
      panel.setBorder(border);
    }
    // Table
    JTable table=_tableController.getTable();
    JScrollPane scroll=GuiFactory.buildScrollPane(table);
    panel.add(scroll,BorderLayout.CENTER);
    // Stats
    JPanel statsPanel=GuiFactory.buildPanel(new FlowLayout(FlowLayout.LEFT));
    statsPanel.add(_countsController.getLabel());
    // Button to choose columns
    JButton choose=GuiFactory.buildButton("Choose columns...");
    ActionListener al=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        TableColumnsChooserController<T> chooser=new TableColumnsChooserController<T>(_parent,_tableController);
        chooser.editModal();
      }
    };
    choose.addActionListener(al);
    statsPanel.add(choose);
    // Button to export
    /*
    JButton export=GuiFactory.buildButton("Export...");
    ActionListener alExport=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        DataExport<T> exporter=new DataExport<T>(new SimpleExportDataOutput());
        exporter.export(_tableController);
      }
    };
    export.addActionListener(alExport);
    statsPanel.add(export);
    */
    panel.add(statsPanel,BorderLayout.NORTH);
    // Force counters init
    filterUpdated();
    return panel;
  }

  /**
   * Update filter.
   */
  public void filterUpdated()
  {
    _tableController.filterUpdated();
    _countsController.update();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _tableController=null;
    // GUI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    // Controllers
    if (_countsController!=null)
    {
      _countsController.dispose();
      _countsController=null;
    }
    _parent=null;
    _configuration=null;
  }
}

package delta.common.ui.swing.tables;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.selection.SelectionChangedEvent;
import delta.common.ui.swing.selection.SelectionChangedListener;
import delta.common.ui.swing.tables.DataItem.SEX;
import delta.common.ui.swing.tables.actions.SimpleAction;
import delta.common.ui.swing.tables.export.DataExport;
import delta.common.ui.swing.tables.export.SimpleExportDataOutput;

/**
 * Test for the generic tables.
 * @author DAM
 */
public class MainTestGenericTables
{
  private GenericTableController<DataItem> _table;

  private boolean _doAdd=false;

  private void doIt()
  {
    _table=buildTable();
    final JFrame f=new JFrame();
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JPanel panel=new JPanel(new BorderLayout());
    JScrollPane scroll=new JScrollPane(_table.getTable());
    panel.add(scroll,BorderLayout.CENTER);
    // Button: update columns
    JButton updateColumns=new JButton("Update columns");
    ActionListener alUpdateColumns=new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        updateColumns();
      }
    };
    updateColumns.addActionListener(alUpdateColumns);
    // Button: close
    JButton close=new JButton("Close");
    ActionListener alClose=new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        _table.dispose();
        f.dispose();
      }
    };
    close.addActionListener(alClose);
    // Button: export
    JButton export=new JButton("Export");
    ActionListener alExport=new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        SimpleExportDataOutput output=new SimpleExportDataOutput();
        DataExport<DataItem> exporter=new DataExport<DataItem>(output);
        exporter.export(_table);
      }
    };
    export.addActionListener(alExport);
    // Buttons panel
    JPanel buttonsPanel=GuiFactory.buildPanel(new FlowLayout());
    buttonsPanel.add(updateColumns);
    buttonsPanel.add(close);
    buttonsPanel.add(export);
    panel.add(buttonsPanel,BorderLayout.SOUTH);
    f.getContentPane().add(panel);
    f.pack();
    f.setVisible(true);
  }

  private void updateColumns()
  {
    TableColumnsManager<DataItem> mgr=_table.getColumnsManager();
    if (_doAdd)
    {
      TableColumnController<DataItem,?> controller=mgr.getAvailableColumns().get(0);
      mgr.addColumnController(controller,true);
    }
    else
    {
      TableColumnController<DataItem,?> controller=mgr.getSelectedColumns().get(0);
      mgr.removeSelectedColumn(controller);
    }
    _table.updateColumns();
    _doAdd=!_doAdd;
  }

  private GenericTableController<DataItem> buildTable()
  {
    List<DataItem> items=new ArrayList<DataItem>();
    ListDataProvider<DataItem> provider=new ListDataProvider<DataItem>(items);
    DataItem item1=new DataItem(1,"MORCELLET",SEX.MALE);
    items.add(item1);
    DataItem item2=new DataItem(2,"SOURICE",SEX.FEMALE);
    items.add(item2);
    GenericTableController<DataItem> table=new GenericTableController<DataItem>(provider);

    // ID column
    CellDataProvider<DataItem,Long> idCell=new CellDataProvider<DataItem,Long>()
    {
      public Long getData(DataItem item)
      {
        return Long.valueOf(item.getId());
      }
    };
    DefaultTableColumnController<DataItem,Long> idColumn=new DefaultTableColumnController<DataItem,Long>("ID",Long.class,idCell);
    idColumn.setWidthSpecs(100,100,100);
    table.addColumnController(idColumn);
    // Name column
    for(int i=0;i<3;i++)
    {
      CellDataProvider<DataItem,String> nameCell=new CellDataProvider<DataItem,String>()
      {
        public String getData(DataItem item)
        {
          return item.getName();
        }
      };
      DefaultTableColumnController<DataItem,String> nameColumn=new DefaultTableColumnController<DataItem,String>("NOM",String.class,nameCell);
      nameColumn.setWidthSpecs(100,200,150);
      table.addColumnController(nameColumn);
    }
    // Sex column
    CellDataProvider<DataItem,SEX> sexCell=new CellDataProvider<DataItem,SEX>()
    {
      public SEX getData(DataItem item)
      {
        return item.getSex();
      }
    };
    DefaultTableColumnController<DataItem,SEX> sexColumn=new DefaultTableColumnController<DataItem,SEX>("SEX",SEX.class,sexCell);
    sexColumn.setWidthSpecs(100,200,150);
    table.addColumnController(sexColumn);
    // Selection listener
    SelectionChangedListener<DataItem> listener=new SelectionChangedListener<DataItem>()
    {
      @Override
      public void selectionChanged(SelectionChangedEvent<DataItem> event)
      {
        System.out.println("Selection changed: "+event);
      }
    };
    table.getSelectionManager().getListeners().addListener(listener);
    // Action listener
    SimpleAction<DataItem> doubleClickAction=new SimpleAction<DataItem>()
    {
      @Override
      public void doIt(DataItem data)
      {
        System.out.println("Double click on: "+data);
      }
    };
    table.getActionsManager().addDoubleClickAction(doubleClickAction);
    return table;
  }

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    new MainTestGenericTables().doIt();
  }
}

package delta.common.ui.swing.lists;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import delta.common.ui.swing.GuiFactory;

/**
 * Controller to edit an ordered selection of items.
 * @param <T> Type of managed items.
 */
public class OrderedItemsSelectionController<T> implements ActionListener
{
  // Controllers
  private ListController<T> _sourceList;
  private ListController<T> _selectedList;
  // UI
  private JPanel _panel;

  // Select/de-select buttons
  private JButton _add;
  private JButton _addAll;
  private JButton _remove;
  private JButton _removeAll;

  // Ordering buttons
  private JButton _top;
  private JButton _up;
  private JButton _down;
  private JButton _bottom;

  /**
   * Constructor.
   * @param comparator Comparator to use (may be <code>null</code>).
   */
  public OrderedItemsSelectionController(Comparator<T> comparator)
  {
    this(comparator,null);
  }

  /**
   * Constructor.
   * @param comparator Comparator to use (may be <code>null</code>).
   * @param labelProvider Label provider, may be <code>null</code>.
   */
  public OrderedItemsSelectionController(Comparator<T> comparator,LabelProvider<T> labelProvider)
  {
    _sourceList=new ListController<T>(comparator,labelProvider);
    _selectedList=new ListController<T>(null,labelProvider);
  }

  /**
   * Set the items to use.
   * @param items The items to use.
   */
  public void setItems(List<T> items)
  {
    _sourceList.setItems(items);
    _selectedList.removeAll();
    updateButtonsStatus();
  }

  /**
   * Select all items.
   */
  public void selectAll()
  {
    List<T> items=_sourceList.getItems();
    _selectedList.setItems(items);
    _sourceList.removeAll();
    updateButtonsStatus();
  }

  /**
   * Select some items.
   * @param items Items to select.
   */
  public void selectItems(List<T> items)
  {
    _selectedList.setItems(items);
    _sourceList.removeItems(items);
  }

  /**
   * Get the selected items.
   * @return a possibly empty but not <code>null</code> list of items.
   */
  public List<T> getSelectedItems()
  {
    return _selectedList.getItems();
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
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());

    JList<?> sourceList=_sourceList.getList();
    JList<?> selectedList=_selectedList.getList();
    ListSelectionListener selectionListener=new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        if (e.getValueIsAdjusting()) return;
        updateButtonsStatus();
      }
    };
    sourceList.addListSelectionListener(selectionListener);
    JScrollPane sourceSP=GuiFactory.buildScrollPane(sourceList);
    Dimension d=new Dimension(300,200);
    sourceSP.setPreferredSize(d);
    TitledBorder selectableBorder=GuiFactory.buildTitledBorder("Selectable");
    sourceSP.setBorder(selectableBorder);
    selectedList.addListSelectionListener(selectionListener);
    JScrollPane selectedSP=GuiFactory.buildScrollPane(selectedList);
    selectedSP.setPreferredSize(d);
    TitledBorder selectedBorder=GuiFactory.buildTitledBorder("Selected");
    selectedSP.setBorder(selectedBorder);

    // Selectable items
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(5,5,5,5),0,0);
    panel.add(sourceSP,c);
    // Selection buttons
    c=new GridBagConstraints(1,0,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
    JPanel buttonsPanel=buildMoveButtonsPanel();
    panel.add(buttonsPanel,c);
    // Selected items
    c=new GridBagConstraints(2,0,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(5,5,5,5),0,0);
    panel.add(selectedSP,c);
    // Ordering buttons
    c=new GridBagConstraints(3,0,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,5),0,0);
    JPanel orderingButtonsPanel=buildOrderingButtonsPanel();
    panel.add(orderingButtonsPanel,c);
    updateButtonsStatus();
    return panel;
  }

  private JPanel buildMoveButtonsPanel()
  {
    JPanel buttonsPanel=GuiFactory.buildPanel(new GridBagLayout());
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0);
    _add=GuiFactory.buildButton(">");
    buttonsPanel.add(_add,c);
    c.gridy++;
    _add.addActionListener(this);
    _addAll=GuiFactory.buildButton(">>");
    buttonsPanel.add(_addAll,c);
    c.gridy++;
    _addAll.addActionListener(this);
    _remove=GuiFactory.buildButton("<");
    buttonsPanel.add(_remove,c);
    c.gridy++;
    _remove.addActionListener(this);
    _removeAll=GuiFactory.buildButton("<<");
    buttonsPanel.add(_removeAll,c);
    c.gridy++;
    _removeAll.addActionListener(this);
    return buttonsPanel;
  }

  private JPanel buildOrderingButtonsPanel()
  {
    JPanel buttonsPanel=GuiFactory.buildPanel(new GridBagLayout());
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0);
    // Top
    _top=GuiFactory.buildButton("Top");
    buttonsPanel.add(_top,c);
    c.gridy++;
    _top.addActionListener(this);
    // Up
    _up=GuiFactory.buildButton("Up");
    buttonsPanel.add(_up,c);
    c.gridy++;
    _up.addActionListener(this);
    // Down
    _down=GuiFactory.buildButton("Down");
    buttonsPanel.add(_down,c);
    c.gridy++;
    _down.addActionListener(this);
    // Bottom
    _bottom=GuiFactory.buildButton("Bottom");
    buttonsPanel.add(_bottom,c);
    c.gridy++;
    _bottom.addActionListener(this);
    return buttonsPanel;
  }

  public void actionPerformed(ActionEvent e)
  {
    Object component=e.getSource();
    if (component==_add)
    {
      List<T> selectedItems=_sourceList.getSelectedItems();
      for(T selectedItem:selectedItems)
      {
        _selectedList.addItem(selectedItem);
        _sourceList.removeItem(selectedItem);
      }
    }
    else if (component==_addAll)
    {
      List<T> selectedItems=_sourceList.getItems();
      for(T selectedItem:selectedItems)
      {
        _selectedList.addItem(selectedItem);
      }
      _sourceList.removeAll();
    }
    else if (component==_remove)
    {
      List<T> selectedItems=_selectedList.getSelectedItems();
      for(T selectedItem:selectedItems)
      {
        _sourceList.addItem(selectedItem);
        _selectedList.removeItem(selectedItem);
      }
    }
    else if (component==_removeAll)
    {
      List<T> selectedItems=_selectedList.getItems();
      for(T selectedItem:selectedItems)
      {
        _sourceList.addItem(selectedItem);
      }
      _selectedList.removeAll();
    }
    else if (component==_top)
    {
      List<T> selectedItems=_selectedList.getSelectedItems();
      int nbItems=selectedItems.size();
      for(int i=nbItems-1;i>=0;i--)
      {
        T selectedItem=selectedItems.get(i);
        _selectedList.moveItemTop(selectedItem);
      }
      _selectedList.selectItems(selectedItems);
      _selectedList.getList().requestFocus();
    }
    else if (component==_up)
    {
      T selectedItem=_selectedList.getSelectedItems().get(0);
      _selectedList.moveItemUp(selectedItem);
      _selectedList.selectItem(selectedItem);
      _selectedList.getList().requestFocus();
    }
    else if (component==_down)
    {
      T selectedItem=_selectedList.getSelectedItems().get(0);
      _selectedList.moveItemDown(selectedItem);
      _selectedList.selectItem(selectedItem);
      _selectedList.getList().requestFocus();
    }
    else if (component==_bottom)
    {
      List<T> selectedItems=_selectedList.getSelectedItems();
      for(T selectedItem : selectedItems)
      {
        _selectedList.moveItemBottom(selectedItem);
      }
      _selectedList.selectItems(selectedItems);
      _selectedList.getList().requestFocus();
    }
    updateButtonsStatus();
  }

  private void updateButtonsStatus()
  {
    if (_add!=null)
    {
      List<T> sourceSelection=_sourceList.getSelectedItems();
      _add.setEnabled(sourceSelection.size()>0);
    }
    if (_addAll!=null)
    {
      List<T> items=_sourceList.getItems();
      _addAll.setEnabled(items.size()>0);
    }
    if (_remove!=null)
    {
      List<T> selectedSelection=_selectedList.getSelectedItems();
      _remove.setEnabled(selectedSelection.size()>0);
    }
    if (_removeAll!=null)
    {
      List<T> selected=_selectedList.getItems();
      _removeAll.setEnabled(selected.size()>0);
    }
    if (_top!=null)
    {
      List<T> selectedSelection=_selectedList.getSelectedItems();
      _top.setEnabled(selectedSelection.size()>0);
      _bottom.setEnabled(selectedSelection.size()>0);
      _up.setEnabled(selectedSelection.size()==1);
      _down.setEnabled(selectedSelection.size()==1);
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_sourceList!=null)
    {
      _sourceList.dispose();
      _sourceList=null;
    }
    if (_selectedList!=null)
    {
      _selectedList.dispose();
      _selectedList=null;
    }
    _add=null;
    _addAll=null;
    _remove=null;
    _removeAll=null;
    _top=null;
    _up=null;
    _down=null;
    _bottom=null;
  }
}

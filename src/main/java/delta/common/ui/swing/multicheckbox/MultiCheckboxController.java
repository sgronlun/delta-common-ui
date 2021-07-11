package delta.common.ui.swing.multicheckbox;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.checkbox.CheckboxController;
import delta.common.ui.swing.combobox.ItemSelectionListener;

/**
 * Controller for a group of check boxes.
 * @param <T> Type of managed data.
 * @author DAM
 */
public class MultiCheckboxController<T>
{
  // Data
  private List<MultiCheckboxItem<T>> _items;
  private List<ItemSelectionListener<T>> _listeners;
  private boolean _listenersEnabled;
  // Controllers
  private List<CheckboxController> _checkboxes;
  // UI
  private JPanel _panel;
  private ActionListener _al;

  /**
   * Constructor.
   */
  public MultiCheckboxController()
  {
    this(false);
  }

  /**
   * Constructor.
   * @param editable Editable or not.
   */
  public MultiCheckboxController(boolean editable)
  {
    _items=new ArrayList<MultiCheckboxItem<T>>();
    _listeners=new ArrayList<ItemSelectionListener<T>>();
    _al=new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        callListeners(e.getSource());
      }
    };
    _listenersEnabled=true;
    _checkboxes=new ArrayList<CheckboxController>();
    _panel=GuiFactory.buildPanel(new GridBagLayout());
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  /**
   * Add empty item.
   * @param label Label for the empty item.
   */
  public void addEmptyItem(String label)
  {
    MultiCheckboxItem<T> item=new MultiCheckboxItem<T>(null,label);
    _items.add(item);
    CheckboxController checkbox=new CheckboxController(label);
    _checkboxes.add(checkbox);
    checkbox.getCheckbox().addActionListener(_al);
    fillPanel();
  }

  /**
   * Add a new item.
   * @param data Data item.
   * @param label Label to use to display this item.
   */
  public void addItem(T data, String label)
  {
    MultiCheckboxItem<T> item=new MultiCheckboxItem<T>(data,label);
    _items.add(item);
    CheckboxController checkbox=new CheckboxController(label);
    _checkboxes.add(checkbox);
    checkbox.getCheckbox().addActionListener(_al);
    fillPanel();
  }

  private void fillPanel()
  {
    _panel.removeAll();
    int index=0;
    for(CheckboxController checkbox : _checkboxes)
    {
      GridBagConstraints c=new GridBagConstraints(index,0,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0,2,0,2),0,0); 
      _panel.add(checkbox.getCheckbox(),c);
      index++;
    }
  }

  /**
   * Get the list of items in this gadget.
   * @return A possibly empty but not <code>null</code> list of items.
   */
  public List<T> getItems()
  {
    List<T> ret=new ArrayList<T>();
    for(MultiCheckboxItem<T> item : _items)
    {
      ret.add(item.getItem());
    }
    return ret;
  }

  /**
   * Get the currently selected items.
   * @return A possibly empty but never <code>null</code> list of data items.
   */
  public List<T> getSelectedItems()
  {
    List<T> ret=new ArrayList<T>();
    int nbItems=_checkboxes.size();
    for(int i=0;i<nbItems;i++)
    {
      CheckboxController checkbox=_checkboxes.get(i);
      boolean selected=checkbox.isSelected();
      if (selected)
      {
        MultiCheckboxItem<T> item=_items.get(i);
        ret.add(item.getItem());
      }
    }
    return ret;
  }

  private int getItemIndexForData(T data)
  {
    int index=0;
    for(MultiCheckboxItem<T> item : _items)
    {
      if (equal(item.getItem(),data))
      {
        return index;
      }
      index++;
    }
    return -1;
  }

  private boolean equal(T t1, T t2)
  {
    if (t1==null)
    {
      return t2==null;
    }
    return t1.equals(t2);
  }

  /**
   * Set the state of an item.
   * @param data Data item to select.
   * @param selected State to set.
   */
  public void setItemState(T data, boolean selected)
  {
    int index=getItemIndexForData(data);
    if (index!=-1)
    {
      _checkboxes.get(index).setSelected(selected);
    }
  }

  /**
   * Select all items.
   */
  public void selectAll()
  {
    for(CheckboxController checkbox : _checkboxes)
    {
      checkbox.setSelected(true);
    }
  }

  /**
   * Set the selected items.
   * @param items Selected items.
   */
  public void setSelectedItems(Set<T> items)
  {
    int nbItems=_items.size();
    for(int i=0;i<nbItems;i++)
    {
      boolean selected=items.contains(_items.get(i).getItem());
      _checkboxes.get(i).setSelected(selected);
    }
  }

  /**
   * Add a listener for item selection.
   * @param listener Listener to add.
   */
  public void addListener(ItemSelectionListener<T> listener)
  {
    _listeners.add(listener);
  }

  /**
   * Remove a listener for item selection.
   * @param listener Listener to remove.
   */
  public void removeListener(ItemSelectionListener<T> listener)
  {
    _listeners.remove(listener);
  }

  private void callListeners(Object source)
  {
    if (!_listenersEnabled)
    {
      return;
    }
    if (_listeners!=null)
    {
      T item=null;
      int index=0;
      for(CheckboxController checkbox : _checkboxes)
      {
        if (checkbox.getCheckbox()==source)
        {
          item=_items.get(index).getItem();
          break;
        }
        index++;
      }
      for(ItemSelectionListener<T> listener : _listeners)
      {
        listener.itemSelected(item);
      }
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _items=null;
    _listeners=null;
    // Controllers
    if (_checkboxes!=null)
    {
      for(CheckboxController checkbox : _checkboxes)
      {
        checkbox.getCheckbox().removeActionListener(_al);
        checkbox.dispose();
      }
      _checkboxes.clear();
      _checkboxes=null;
    }
    // UI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    _al=null;
  }
}
